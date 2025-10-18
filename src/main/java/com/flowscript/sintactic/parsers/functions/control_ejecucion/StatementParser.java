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

    @Override
    public StatementNode parse(ParserContext context) throws Parser.ParseException {
        IfStatementParser ifParser = new IfStatementParser();
        TryStatementParser tryParser = new TryStatementParser();
        ThrowStatementParser throwParser = new ThrowStatementParser();
        ReturnStatementParser returnParser = new ReturnStatementParser();
        GotoStatementParser gotoParser = new GotoStatementParser();
        ForStatementParser forParser = new ForStatementParser();
        VariableDeclarationStatementParser varParser = new VariableDeclarationStatementParser();
        BlockParser blockParser = new BlockParser();
        ExpressionStatementParser exprParser = new ExpressionStatementParser();

        if (!context.hasMoreTokens()) return null;

        Token current = context.getCurrentToken();
        if (current.getType() == TokenType.RIGHT_BRACE) return null;

        switch (current.getType()) {
            case IF:
                return ifParser.parse(context);
            case FOR:
                return forParser.parse(context);
            case TRY:
                return tryParser.parse(context);
            case RETURN:
                return returnParser.parse(context);
            case THROW:
                return throwParser.parse(context);
            case LEFT_BRACE:
                return blockParser.parse(context);
            default:
                if (current.getType() == TokenType.IDENTIFIER ||
                    current.getType().isLiteral() ||
                    current.getType() == TokenType.LEFT_PAREN ||
                    current.getType().isOperator()) {

                    if (context.peek(1) != null && context.peek(1).getType() == TokenType.ASSIGN) {
                        return varParser.parse(context);
                    }

                    return exprParser.parse(context);
                }
                
                throw new Parser.ParseException(
                    "token inesperado '" + current.getValue() + "' en la línea " + current.getLine()
                );
        }
    }
}