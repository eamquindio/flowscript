package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;

import java.util.ArrayList;
import java.util.List;

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
        Token token = context.getCurrentToken();
        if (token == null) {
            throw new Parser.ParseException("Expected a postfix operator, but no more tokens are available.");
        }

        switch (token.getType()) {
            case DOT: {
                context.consume();

                Token idToken = context.getCurrentToken();
                if (idToken == null || idToken.getType() != TokenType.IDENTIFIER) {
                    throw new Parser.ParseException("Expected an identifier after '.'");
                }
                context.consume();

                return new PostfixOperatorNode.PropertyAccessOperatorNode(token, idToken.getValue());
            }
            case LEFT_BRACKET: {
                context.consume();

                ExpressionParser exprParser = new ExpressionParser();
                ExpressionNode indexExpr = exprParser.parse(context);

                Token rightBracket = context.getCurrentToken();
                if (rightBracket == null || rightBracket.getType() != TokenType.RIGHT_BRACKET) {
                    throw new Parser.ParseException("Expected ']' after index expression");
                }
                context.consume();

                return new PostfixOperatorNode.IndexAccessOperatorNode(token, indexExpr);
            }
            case LEFT_PAREN: {
                context.consume();

                List<ExpressionNode> arguments = new ArrayList<>();
                ExpressionParser exprParser = new ExpressionParser();

                Token nextToken = context.getCurrentToken();
                if (nextToken != null && nextToken.getType() != TokenType.RIGHT_PAREN) {
                    while (true) {
                        ExpressionNode arg = exprParser.parse(context);
                        arguments.add(arg);

                        Token commaOrRightParen = context.getCurrentToken();
                        if (commaOrRightParen == null) {
                            throw new Parser.ParseException("Expected ')' or ',' in argument list");
                        }

                        if (commaOrRightParen.getType() == TokenType.COMMA) {
                            context.consume();
                            continue;
                        } else if (commaOrRightParen.getType() == TokenType.RIGHT_PAREN) {
                            break;
                        } else {
                            throw new Parser.ParseException("Expected ',' or ')' in argument list");
                        }
                    }
                }

                Token rightParen = context.getCurrentToken();
                if (rightParen == null || rightParen.getType() != TokenType.RIGHT_PAREN) {
                    throw new Parser.ParseException("Expected ')' at the end of function call");
                }
                context.consume();

                return new PostfixOperatorNode.FunctionCallOperatorNode(token, arguments);
            }
            default:
                throw new Parser.ParseException("Expected a postfix operator, but found: " + token);
        }
    }
}