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

    private final IfStatementParser ifParser;
    private final WhileStatementParser whileParser;
    private final TryStatementParser tryParser;
    private final ThrowStatementParser throwParser;
    private final ReturnStatementParser returnParser;
    private final GotoStatementParser gotoParser;
    private final ForStatementParser forParser;
    private final BreakStatementParser breakParser;
    private final ContinueStatementParser continueParser;
    private final VariableDeclarationStatementParser varParser;
    private final BlockParser blockParser;
    private final ExpressionStatementParser exprParser;

    public StatementParser() {
        this.ifParser = new IfStatementParser();
        this.whileParser = new WhileStatementParser();
        this.tryParser = new TryStatementParser();
        this.throwParser = new ThrowStatementParser();
        this.returnParser = new ReturnStatementParser();
        this.gotoParser = new GotoStatementParser();
        this.forParser = new ForStatementParser();
        this.breakParser = new BreakStatementParser();
        this.continueParser = new ContinueStatementParser();
        this.varParser = new VariableDeclarationStatementParser();
        this.blockParser = new BlockParser();
        this.exprParser = new ExpressionStatementParser();
    }

    @Override
    public StatementNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Expected statement but reached end of input");
        }

        TokenType type = current.getType();

        // Check for each statement type based on leading keyword/token
        switch (type) {
            // Control flow statements
            case IF:
                return ifParser.parse(context);

            case WHILE:
                return whileParser.parse(context);

            case TRY:
                return tryParser.parse(context);

            case THROW:
                return throwParser.parse(context);

            case RETURN:
                return returnParser.parse(context);

            case GOTO:
                return gotoParser.parse(context);

            case FOR:
                return forParser.parse(context);

            case BREAK:
                return breakParser.parse(context);

            case CONTINUE:
                return continueParser.parse(context);

            // Block statement
            case LEFT_BRACE:
                return blockParser.parse(context);

            // Variable declaration (IDENTIFIER '=')
            case IDENTIFIER:
                // Need to look ahead to distinguish between:
                // - VariableDeclaration: IDENTIFIER '=' Expression
                // - ExpressionStatement: Expression (which starts with IDENTIFIER)
                Token next = context.peek(1);
                if (next != null && next.getType() == TokenType.ASSIGN) {
                    return varParser.parse(context);
                }
                // Otherwise, it's an expression statement
                return exprParser.parse(context);

            // Expression statement (fallback for any expression)
            default:
                return exprParser.parse(context);
        }
    }
}
