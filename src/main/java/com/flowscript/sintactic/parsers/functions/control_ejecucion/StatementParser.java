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
    private final TryStatementParser tryParser;
    private final ThrowStatementParser throwParser;
    private final ReturnStatementParser returnParser;
    private final GotoStatementParser gotoParser;
    private final ForStatementParser forParser;
    private final VariableDeclarationStatementParser varParser;
    private final BlockParser blockParser;
    private final ExpressionStatementParser exprParser;

    public StatementParser() {
        this.ifParser = new IfStatementParser();
        this.tryParser = new TryStatementParser();
        this.throwParser = new ThrowStatementParser();
        this.returnParser = new ReturnStatementParser();
        this.gotoParser = new GotoStatementParser();
        this.forParser = new ForStatementParser();
        this.varParser = new VariableDeclarationStatementParser();
        this.blockParser = new BlockParser();
        this.exprParser = new ExpressionStatementParser();
    }

    @Override
    public StatementNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing statement");
        }

        String value = current.getValue();

        // IfStatement
        if (value.equals("si") || value.equals("if")) {
            return ifParser.parse(context);
        }

        // TryStatement
        if (value.equals("intentar") || value.equals("try")) {
            return tryParser.parse(context);
        }

        // ThrowStatement
        if (value.equals("lanzar") || value.equals("throw")) {
            return throwParser.parse(context);
        }

        // ReturnStatement
        if (value.equals("retornar") || value.equals("return")) {
            return returnParser.parse(context);
        }

        // GotoStatement
        if (value.equals("go_to")) {
            return gotoParser.parse(context);
        }

        // ForStatement
        if (value.equals("para") || value.equals("for")) {
            return forParser.parse(context);
        }

        // Block
        if (current.getType() == TokenType.LEFT_BRACE) {
            return blockParser.parse(context);
        }

        // VariableDeclaration (IDENTIFIER '=')
        if (current.getType() == TokenType.IDENTIFIER &&
                context.peek(1) != null &&
                context.peek(1).getValue().equals("=")) {
            return varParser.parse(context);
        }

        // Default: ExpressionStatement
        return exprParser.parse(context);
    }
}
