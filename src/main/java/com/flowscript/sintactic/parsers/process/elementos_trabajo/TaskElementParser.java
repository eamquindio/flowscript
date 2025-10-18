package com.flowscript.sintactic.parsers.process.elementos_trabajo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.TaskElementNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para elementos de tarea dentro de un proceso.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * TaskElement ::= 'task' IDENTIFIER '{' TaskBody '}'
 * TaskBody ::= 'action:' StatementList
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 3: Elementos de Trabajo
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Tarea simple
 * task CargarCliente {
 *     action:
 *         cliente = db.get("clientes", entrada.id)
 *         go_to ValidarCliente
 * }
 *
 * // Tarea con lógica condicional
 * task ValidarMonto {
 *     action:
 *         if entrada.monto > 10000 {
 *             go_to RequiereAprobacion
 *         } else {
 *             go_to AprobacionAutomatica
 *         }
 * }
 * </pre>
 *
 * @see TaskElementNode
 */
public class TaskElementParser implements IParser<TaskElementNode> {

    //private final StatementParser statementParser;

    public TaskElementParser() {
        //this.statementParser = new StatementParser();
    }

    @Override
    public TaskElementNode parse(ParserContext context) throws Parser.ParseException {
        Token taskToken = parseTaskKeyword(context);
        String taskName = parseTaskName(context);
        parseOpenBrace(context);
        parseActionKeyword(context);
        parseColon(context);
        List<StatementNode> statements = parseStatementListEmpty(context);
        //List<StatementNode> statements = parseStatementList(context);
        parseCloseBrace(context);

        return new TaskElementNode(taskToken, taskName, statements);
    }

    private Token parseTaskKeyword(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (!context.check(TokenType.TASK)) {
            throw new Parser.ParseException("Se esperaba 'task' o 'tarea'");
        }
        context.advance();

        return token;
    }

    private String parseTaskName(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.IDENTIFIER)) {
            throw new Parser.ParseException("Se esperaba un identificador después de 'task'");
        }

        String name = context.getCurrentToken().getValue();
        context.advance();

        return name;
    }

    private void parseOpenBrace(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.LEFT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '{' después del nombre de la tarea");
        }
        context.advance();
    }

    private void parseActionKeyword(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.ACTION)) {
            throw new Parser.ParseException("Se esperaba 'action' o 'accion'");
        }
        context.advance();
    }

    private void parseColon(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.COLON)) {
            throw new Parser.ParseException("Se esperaba ':' después de 'action'");
        }
        context.advance();
    }

    private List<StatementNode> parseStatementListEmpty(ParserContext context) throws Parser.ParseException {
        int braceCount = 0;

        while (context.hasMoreTokens()) {
            if (context.check(TokenType.LEFT_BRACE)) {
                braceCount++;
                context.advance();
            } else if (context.check(TokenType.RIGHT_BRACE)) {
                if (braceCount == 0) {
                    break;
                }
                braceCount--;
                context.advance();
            } else {
                context.advance();
            }
        }

        return new ArrayList<>();
    }

    /*
    private List<StatementNode> parseStatementList(ParserContext context) throws Parser.ParseException {
        List<StatementNode> statements = new ArrayList<>();

        while (!context.check(TokenType.RIGHT_BRACE) && context.hasMoreTokens()) {
            StatementNode statement = statementParser.parse(context);
            statements.add(statement);
        }

        return statements;
    }


     */
    private void parseCloseBrace(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.RIGHT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '}' al final de la tarea");
        }
        context.advance();
    }
}
