package com.flowscript.sintactic.parsers.process.elementos_trabajo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.process.estructura_principal.TaskElementNode;

// Parsers de statements (inyectaremos todos para evitar ciclos)
import com.flowscript.sintactic.parsers.functions.control_ejecucion.BlockParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ForStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.IfStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ReturnStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ThrowStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.TryStatementParser;
import com.flowscript.sintactic.parsers.functions.statements_basicos.ExpressionStatementParser;
import com.flowscript.sintactic.parsers.functions.statements_basicos.VariableDeclarationStatementParser;
import com.flowscript.sintactic.parsers.process.navegacion.GotoStatementParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para elementos de tarea dentro de un proceso.
 *
 * Gramática (BNF):
 * TaskElement ::= 'task' IDENTIFIER '{' TaskBody '}'
 * TaskBody    ::= 'action' ':' Statement*
 *
 * Ejemplo:
 * task ValidarDatos {
 *   action:
 *     resultado = validar(entrada)
 *     go_to Siguiente
 * }
 */
public class TaskElementParser implements IParser<TaskElementNode> {

    private final StatementParser statementParser;

    public TaskElementParser() {
        // Construimos StatementParser y le inyectamos sus dependencias para evitar ciclos.
        this.statementParser = new StatementParser();
        this.statementParser.wire(
                new IfStatementParser(),
                new TryStatementParser(),
                new ThrowStatementParser(),
                new ReturnStatementParser(),
                new GotoStatementParser(),
                new ForStatementParser(),
                new VariableDeclarationStatementParser(),
                new BlockParser(),
                new ExpressionStatementParser()
        );
    }

    @Override
    public TaskElementNode parse(ParserContext context) throws Parser.ParseException {
        // task
        Token taskTok = consumeKeyword(context, TokenType.TASK, "task");

        // nombre de la tarea (ID)
        Token nameTok = consumeIdentifier(context, "nombre de la tarea (ID)");
        String taskName = nameTok.getValue();

        // '{'
        consumeSymbol(context, TokenType.LEFT_BRACE, "{");

        // 'action' ':'
        consumeKeyword(context, TokenType.ACTION, "action");
        consumeSymbol(context, TokenType.COLON, ":");

        // lista de statements hasta '}'
        List<StatementNode> stmts = parseStatementList(context);

        // '}'
        consumeSymbol(context, TokenType.RIGHT_BRACE, "}");

        return new TaskElementNode(taskTok, taskName, stmts);
    }

    /**
     * Lee una secuencia de statements hasta encontrar '}' (sin consumirla aquí).
     */
    private List<StatementNode> parseStatementList(ParserContext context) throws Parser.ParseException {
        List<StatementNode> list = new ArrayList<>();

        // Consumimos statements mientras el token actual no sea '}'.
        while (true) {
            Token t = context.getCurrentToken();
            if (t == null) {
                throw new Parser.ParseException("Se esperaba '}' para cerrar la tarea, pero no hay más tokens.");
            }
            if (isRBrace(t)) {
                // no consumimos aquí; lo hará el que llama
                break;
            }

            // Parsear un statement
            StatementNode stmt = statementParser.parse(context);
            list.add(stmt);
        }

        return list;
    }

    // ----------------------
    // Utilidades locales
    // ----------------------

    private static boolean isRBrace(Token t) {
        return t.getType() == TokenType.RIGHT_BRACE || "}".equals(t.getValue());
    }

    private static Token consumeKeyword(ParserContext ctx, TokenType type, String lexeme) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba '" + lexeme + "', pero no hay más tokens.");
        }
        if (t.getType() != type && !lexeme.equals(t.getValue())) {
            throw error(t, "Se esperaba '" + lexeme + "'");
        }
        ctx.advance();
        return t;
    }

    private static void consumeSymbol(ParserContext ctx, TokenType type, String lexeme) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba '" + lexeme + "', pero no hay más tokens.");
        }
        if (t.getType() != type && !lexeme.equals(t.getValue())) {
            throw error(t, "Se esperaba '" + lexeme + "'");
        }
        ctx.advance();
    }

    private static Token consumeIdentifier(ParserContext ctx, String what) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba " + what + ", pero no hay más tokens.");
        }
        if (t.getType() != TokenType.IDENTIFIER) {
            throw error(t, "Se esperaba un identificador para " + what);
        }
        ctx.advance();
        return t;
    }

    private static Parser.ParseException error(Token t, String msg) {
        return new Parser.ParseException(
                msg + " pero se encontró '" + t.getValue() +
                        "' en línea " + t.getLine() + ", columna " + t.getColumn()
        );
    }
}
