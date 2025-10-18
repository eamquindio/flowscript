package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Parser coordinador para expresiones (punto de entrada del sistema de expresiones).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Expression ::= TernaryExpression
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Expresiones simples
 * 42
 * "Hola"
 * verdadero
 *
 * // Expresiones aritméticas
 * 10 + 20
 * x * (y + z)
 *
 * // Expresiones lógicas
 * x > 10 and y < 20
 * not activo or modo == "prueba"
 *
 * // Expresiones ternarias
 * x > 0 ? "positivo" : "negativo"
 *
 * // Llamadas a función
 * calcular(10, 20)
 * usuario.obtener_nombre()
 *
 * // Acceso a propiedades
 * usuario.nombre
 * lista[0]
 * matriz[i][j]
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ExpressionParser parser = new ExpressionParser();
 * ExpressionNode expr = parser.parse(context);
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} delegando a TernaryExpressionParser.
 *
 * @see ExpressionNode
 */
public class ExpressionParser implements IParser<ExpressionNode> {

    private final TernaryExpressionParser ternaryParser = new TernaryExpressionParser();

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException("fin de entrada inesperado en la expresion");
        }

        Token first = context.getCurrentToken();

        if (first.getType() == TokenType.ASSIGN) {
            throw new Parser.ParseException(
                "Se esperaba una expresión válida antes del '=' en línea " + first.getLine()
            );
        }

        ExpressionNode expr = ternaryParser.parse(context);

        while (context.hasMoreTokens()) {
            Token current = context.getCurrentToken();
            if (current == null) break;

            TokenType type = current.getType();

            if (type == TokenType.ASSIGN) {
                Token assignToken = context.consume(TokenType.ASSIGN);

                if (expr == null || expr.getToken() == null || expr.getToken().getType() != TokenType.IDENTIFIER) {
                    throw new Parser.ParseException(
                        "Se esperaba una expresión válida antes del '=' en línea " + assignToken.getLine()
                    );
                }

                if (!context.hasMoreTokens()) {
                    throw new Parser.ParseException(
                        "Se esperaba una expresión válida después de '=' en línea " + assignToken.getLine()
                    );
                }

                ExpressionNode right = ternaryParser.parse(context);
                if (right == null) {
                    throw new Parser.ParseException(
                        "Se esperaba una expresión válida después de '=' en línea " + assignToken.getLine()
                    );
                }

                ExpressionNode leftRef = expr;
                expr = new ExpressionNode(assignToken) {
                    private final ExpressionNode left = leftRef;
                    private final ExpressionNode rightExpr = right;

                    @Override
                    public String getExpressionType() {
                        return "assignment";
                    }

                    @Override
                    public String toString() {
                        return left.toString() + " = " + rightExpr.toString();
                    }
                };
                continue;
            }

            if (type == TokenType.LEFT_PAREN || type == TokenType.LEFT_BRACKET) {
                int balance = 0;
                TokenType open = type;
                TokenType close = (type == TokenType.LEFT_PAREN)
                        ? TokenType.RIGHT_PAREN : TokenType.RIGHT_BRACKET;

                do {
                    if (context.getCurrentToken().getType() == open) balance++;
                    if (context.getCurrentToken().getType() == close) balance--;
                    context.advance();
                    if (!context.hasMoreTokens()) break;
                } while (balance > 0);
                continue;
            }

            if (type == TokenType.DOT) {
                context.advance();
                if (context.getCurrentToken() != null &&
                    context.getCurrentToken().getType() == TokenType.IDENTIFIER) {
                    context.advance();
                }
                continue;
            }

            if (type == TokenType.SEMICOLON || type == TokenType.RIGHT_BRACE) {
                break;
            }

            break;
        }

        return expr;
    }
}