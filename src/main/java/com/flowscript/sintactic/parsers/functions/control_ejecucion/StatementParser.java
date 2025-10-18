package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.parsers.functions.control_flujo.ForStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.IfStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ReturnStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ThrowStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.TryStatementParser;
import com.flowscript.sintactic.parsers.functions.statements_basicos.ExpressionStatementParser;
import com.flowscript.sintactic.parsers.functions.statements_basicos.VariableDeclarationStatementParser;
import com.flowscript.sintactic.parsers.process.navegacion.GotoStatementParser;

/**
 * Parser coordinador para statements en FlowScript.
 *
 * <pre>
 * Statement ::=
 *       ExpressionStatement
 *     | IfStatement
 *     | TryStatement
 *     | ThrowStatement
 *     | ReturnStatement
 *     | GotoStatement
 *     | ForStatement
 *     | VariableDeclaration/Assignment (ID '=' expression)
 *     | Block
 * </pre>
 *
 * Este parser no instancia dependencias “grandes” en el constructor por defecto
 * para evitar ciclos. Usa inyección con wire(...) o el constructor completo.
 */
public class StatementParser implements IParser<StatementNode> {

    private IfStatementParser ifParser;
    private TryStatementParser tryParser;
    private ThrowStatementParser throwParser;
    private ReturnStatementParser returnParser;
    private GotoStatementParser gotoParser;
    private ForStatementParser forParser;
    private VariableDeclarationStatementParser varParser;
    private BlockParser blockParser;
    private ExpressionStatementParser exprParser;

    /** Constructor vacío: recuerda llamar a wire(...) antes de parsear. */
    public StatementParser() { }

    /** Constructor alterno con inyección por parámetros. */
    public StatementParser(IfStatementParser ifParser,
                           TryStatementParser tryParser,
                           ThrowStatementParser throwParser,
                           ReturnStatementParser returnParser,
                           GotoStatementParser gotoParser,
                           ForStatementParser forParser,
                           VariableDeclarationStatementParser varParser,
                           BlockParser blockParser,
                           ExpressionStatementParser exprParser) {
        this.ifParser = ifParser;
        this.tryParser = tryParser;
        this.throwParser = throwParser;
        this.returnParser = returnParser;
        this.gotoParser = gotoParser;
        this.forParser = forParser;
        this.varParser = varParser;
        this.blockParser = blockParser;
        this.exprParser = exprParser;
    }

    /**
     * Inyección de dependencias para evitar ciclos de construcción entre parsers.
     */
    public void wire(IfStatementParser ifParser,
                     TryStatementParser tryParser,
                     ThrowStatementParser throwParser,
                     ReturnStatementParser returnParser,
                     GotoStatementParser gotoParser,
                     ForStatementParser forParser,
                     VariableDeclarationStatementParser varParser,
                     BlockParser blockParser,
                     ExpressionStatementParser exprParser) {
        this.ifParser = ifParser;
        this.tryParser = tryParser;
        this.throwParser = throwParser;
        this.returnParser = returnParser;
        this.gotoParser = gotoParser;
        this.forParser = forParser;
        this.varParser = varParser;
        this.blockParser = blockParser;
        this.exprParser = exprParser;
    }

    @Override
    public StatementNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("Se esperaba un statement, pero no hay más tokens.");
        }

        // Si vemos '}', no lo consumimos aquí: que lo gestione el BlockParser que nos llamó.
        if (isRBrace(current)) {
            return null;
        }

        // Bloque: delega completamente al BlockParser (él debe consumir '{' y luego '}')
        if (isLBrace(current)) {
            require(blockParser, "BlockParser");
            return blockParser.parse(context);
        }

        // Palabras clave que despachan a parsers específicos
        if (isIf(current)) {
            require(ifParser, "IfStatementParser");
            return ifParser.parse(context);
        }
        if (isTry(current)) {
            require(tryParser, "TryStatementParser");
            return tryParser.parse(context);
        }
        if (isThrow(current)) {
            require(throwParser, "ThrowStatementParser");
            return throwParser.parse(context);
        }
        if (isReturn(current)) {
            require(returnParser, "ReturnStatementParser");
            return returnParser.parse(context);
        }
        if (isGoto(current)) {
            require(gotoParser, "GotoStatementParser");
            return gotoParser.parse(context);
        }
        if (isFor(current)) {
            require(forParser, "ForStatementParser");
            return forParser.parse(context);
        }

        // Heurística mínima para asignaciones/declaraciones: ID seguido de '='
        if (isAssignmentLookahead(context)) {
            require(varParser, "VariableDeclarationStatementParser");
            return varParser.parse(context);
        }

        // Por defecto, parsear como expresión-Statement
        require(exprParser, "ExpressionStatementParser");
        return exprParser.parse(context);
    }

    // -------------------------------------------------
    // Utilidades privadas (tolerantes a enum/lexema)
    // -------------------------------------------------

    private static boolean isIf(Token t) {
        return isType(t, TokenType.IF) || eqLex(t, "if");
    }

    private static boolean isTry(Token t) {
        return isType(t, TokenType.TRY) || eqLex(t, "try");
    }

    private static boolean isThrow(Token t) {
        return isType(t, TokenType.THROW) || eqLex(t, "throw");
    }

    private static boolean isReturn(Token t) {
        return isType(t, TokenType.RETURN) || eqLex(t, "return");
    }

    private static boolean isGoto(Token t) {
        // En el enum puede estar como GOTO con lexema "goto" o puedes usar "go_to" en el lenguaje.
        return isType(t, TokenType.GOTO) || eqLex(t, "goto") || eqLex(t, "go_to");
    }

    private static boolean isFor(Token t) {
        return isType(t, TokenType.FOR) || eqLex(t, "for");
    }

    private static boolean isLBrace(Token t) {
        // Acepta por tipo o por lexema
        return isType(t, TokenType.LEFT_BRACE) || eqLex(t, "{");
    }

    private static boolean isRBrace(Token t) {
        return isType(t, TokenType.RIGHT_BRACE) || eqLex(t, "}");
    }

    private static boolean isType(Token t, TokenType type) {
        return t != null && t.getType() == type;
    }

    private static boolean eqLex(Token t, String s) {
        return t != null && s.equals(t.getValue());
    }

    /**
     * ID '='  → lo tratamos como declaración/asignación.
     */
    private static boolean isAssignmentLookahead(ParserContext ctx) {
        Token t0 = ctx.getCurrentToken();
        if (t0 == null || t0.getType() != TokenType.IDENTIFIER) return false;

        Token t1 = ctx.peek(1);
        if (t1 == null) return false;

        // Acepta por tipo o por lexema
        return t1.getType() == TokenType.ASSIGN || "=".equals(t1.getValue());
    }

    private static void require(Object dep, String name) throws Parser.ParseException {
        if (dep == null) {
            throw new Parser.ParseException("Dependencia no inyectada: " + name +
                    ". Usa StatementParser.wire(...) o el constructor con dependencias.");
        }
    }
}
