package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.parsers.functions.control_flujo.*;
import com.flowscript.sintactic.parsers.functions.statements_basicos.*;
import com.flowscript.sintactic.parsers.process.navegacion.GotoStatementParser;

/**
 * Parser coordinador para statements en FlowScript.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Statement ::= ExpressionStatement
 *             | IfStatement
 *             | TryStatement
 *             | ThrowStatement
 *             | ReturnStatement
 *             | GotoStatement
 *             | ForStatement
 *             | VariableDeclaration
 *             | Block
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // ExpressionStatement
 * imprimir("Hola")
 * x + y
 *
 * // IfStatement
 * if x > 10 {
 *     imprimir("Grande")
 * } else {
 *     imprimir("Pequeño")
 * }
 *
 * // TryStatement
 * try {
 *     resultado = operacion_riesgosa()
 * } catch (e) {
 *     imprimir("Error: " + e.mensaje)
 * }
 *
 * // ThrowStatement
 * throw { tipo: "Error", mensaje: "Algo salió mal" }
 *
 * // ReturnStatement
 * return x * 2
 *
 * // GotoStatement
 * go_to SiguienteTarea
 *
 * // ForStatement
 * for each item in lista {
 *     imprimir(item)
 * }
 *
 * // VariableDeclaration
 * nombre = "Juan"
 * edad = 25
 *
 * // Block
 * {
 *     x = 10
 *     y = 20
 *     imprimir(x + y)
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * StatementParser parser = new StatementParser();
 * StatementNode statement = parser.parse(context);
 * </pre>
 *
 * @see StatementNode
 */
public class StatementParser implements IParser<StatementNode> {

    // Parsers especializados
    private IfStatementParser ifStmt;
    private TryStatementParser tryStmt;
    private ThrowStatementParser throwStmt;
    private ReturnStatementParser returnStmt;
    private GotoStatementParser gotoStmt;
    private ForStatementParser forStmt;
    private VariableDeclarationStatementParser varStmt;
    private BlockParser blockStmt;
    private ExpressionStatementParser exprStmt;

    /** Constructor vacío: usar setup(...) antes de invocar parse(). */
    public StatementParser() { }

    /** Constructor alternativo con inyección completa. */
    public StatementParser(
            IfStatementParser ifStmt,
            TryStatementParser tryStmt,
            ThrowStatementParser throwStmt,
            ReturnStatementParser returnStmt,
            GotoStatementParser gotoStmt,
            ForStatementParser forStmt,
            VariableDeclarationStatementParser varStmt,
            BlockParser blockStmt,
            ExpressionStatementParser exprStmt
    ) {
        this.ifStmt = ifStmt;
        this.tryStmt = tryStmt;
        this.throwStmt = throwStmt;
        this.returnStmt = returnStmt;
        this.gotoStmt = gotoStmt;
        this.forStmt = forStmt;
        this.varStmt = varStmt;
        this.blockStmt = blockStmt;
        this.exprStmt = exprStmt;
    }

    /**
     * Método auxiliar para inyectar dependencias después de la construcción.
     */
    public void setup(
            IfStatementParser ifStmt,
            TryStatementParser tryStmt,
            ThrowStatementParser throwStmt,
            ReturnStatementParser returnStmt,
            GotoStatementParser gotoStmt,
            ForStatementParser forStmt,
            VariableDeclarationStatementParser varStmt,
            BlockParser blockStmt,
            ExpressionStatementParser exprStmt
    ) {
        this.ifStmt = ifStmt;
        this.tryStmt = tryStmt;
        this.throwStmt = throwStmt;
        this.returnStmt = returnStmt;
        this.gotoStmt = gotoStmt;
        this.forStmt = forStmt;
        this.varStmt = varStmt;
        this.blockStmt = blockStmt;
        this.exprStmt = exprStmt;
    }

    @Override
    public StatementNode parse(ParserContext ctx) throws Parser.ParseException {
        Token token = ctx.getCurrentToken();
        if (token == null) {
            throw new Parser.ParseException("No se encontró una sentencia válida (EOF inesperado).");
        }

        if (isRBrace(token)) return null;


        if (isKeyword(token, TokenType.IF, "if")) {
            require(ifStmt, "IfStatementParser");
            return ifStmt.parse(ctx);
        }
        if (isKeyword(token, TokenType.TRY, "try")) {
            require(tryStmt, "TryStatementParser");
            return tryStmt.parse(ctx);
        }
        if (isKeyword(token, TokenType.THROW, "throw")) {
            require(throwStmt, "ThrowStatementParser");
            return throwStmt.parse(ctx);
        }


        if (isKeyword(token, TokenType.RETURN, "return")) {
            require(returnStmt, "ReturnStatementParser");
            return returnStmt.parse(ctx);
        }
        if (isGotoKeyword(token)) {
            require(gotoStmt, "GotoStatementParser");
            return gotoStmt.parse(ctx);
        }
        if (isKeyword(token, TokenType.FOR, "for")) {
            require(forStmt, "ForStatementParser");
            return forStmt.parse(ctx);
        }
        if (isAssignmentCandidate(ctx)) {
            require(varStmt, "VariableDeclarationStatementParser");
            return varStmt.parse(ctx);
        }

        // Por defecto: expresión
        require(exprStmt, "ExpressionStatementParser");
        return exprStmt.parse(ctx);
    }


    private static boolean isKeyword(Token t, TokenType type, String lexeme) {
        return (t != null) && (t.getType() == type || lexeme.equals(t.getValue()));
    }

    private static boolean isGotoKeyword(Token t) {
        return isKeyword(t, TokenType.GOTO, "goto") || isKeyword(t, TokenType.GOTO, "go_to");
    }

    private static boolean isLBrace(Token t) {
        return isKeyword(t, TokenType.LEFT_BRACE, "{");
    }

    private static boolean isRBrace(Token t) {
        return isKeyword(t, TokenType.RIGHT_BRACE, "}");
    }

    private static boolean isAssignmentCandidate(ParserContext ctx) {
        Token current = ctx.getCurrentToken();
        if (current == null || current.getType() != TokenType.IDENTIFIER) return false;

        Token next = ctx.peek(1);
        if (next == null) return false;

        return next.getType() == TokenType.ASSIGN || "=".equals(next.getValue());
    }

    private static void require(Object dependency, String name) throws Parser.ParseException {
        if (dependency == null) {
            throw new Parser.ParseException(
                    "Dependencia faltante: " + name +
                            ". Usa StatementParser.setup(...) o el constructor con parámetros."
            );
        }
    }
}