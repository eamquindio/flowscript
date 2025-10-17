package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;

/**
 * Parser para operadores postfijos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * PostfixOperator ::= '.' IDENTIFIER
 *                   | '[' Expression ']'
 *                   | '(' ArgumentList? ')'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 10: Expresiones - Operadores Postfijos
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Los operadores postfijos incluyen:
 * - Acceso a miembro: .identificador
 * - Indexación: [expresión]
 * - Llamada a función: (argumentos)
 *
 * @see PostfixOperatorNode
 */
public class PostfixOperatorParser implements IParser<PostfixOperatorNode> {

    @Override
    public PostfixOperatorNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing postfix operator");
        }

        if (context.match(TokenType.DOT)) {
            Token dotToken = context.consume(TokenType.DOT);

            if (!context.match(TokenType.IDENTIFIER)) {
                throw new Parser.ParseException("Expected identifier after '.' at line " + dotToken.getLine());
            }

            Token identifierToken = context.consume(TokenType.IDENTIFIER);
            return new PropertyAccessOperatorNode(dotToken, identifierToken.getValue());
        }

        if (context.match(TokenType.LEFT_BRACKET)) {
            Token leftBracket = context.consume(TokenType.LEFT_BRACKET);

            ExpressionParser exprParser = new ExpressionParser();
            ExpressionNode indexExpr = exprParser.parse(context);

            if (!context.match(TokenType.RIGHT_BRACKET)) {
                throw new Parser.ParseException("Expected ']' after index expression at line " + leftBracket.getLine());
            }
            context.consume(TokenType.RIGHT_BRACKET);

            return new IndexAccessOperatorNode(leftBracket, indexExpr);
        }

        if (context.match(TokenType.LEFT_PAREN)) {
            Token leftParen = context.consume(TokenType.LEFT_PAREN);
            List<ExpressionNode> arguments = new ArrayList<>();

            if (!context.match(TokenType.RIGHT_PAREN)) {
                ExpressionParser exprParser = new ExpressionParser();
                arguments.add(exprParser.parse(context));

                while (context.match(TokenType.COMMA)) {
                    context.consume(TokenType.COMMA);
                    arguments.add(exprParser.parse(context));
                }
            }

            if (!context.match(TokenType.RIGHT_PAREN)) {
                throw new Parser.ParseException("Expected ')' to close function call at line " + leftParen.getLine());
            }
            context.consume(TokenType.RIGHT_PAREN);

            return new FunctionCallOperatorNode(leftParen, arguments);
        }

        throw new Parser.ParseException("Unexpected token '" + current.getValue() +
                "' while parsing postfix operator at line " + current.getLine());

    }
}
