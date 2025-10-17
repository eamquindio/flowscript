package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.UnaryExpressionNode;

/**
 * Parser para expresiones unarias.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * UnaryExpression ::= ( 'not' | '-' )? PostfixExpression
 * </pre>
 *
 * <p>
 * Soporta operadores unarios: 'not' (negación lógica) y '-' (negación
 * aritmética).
 * El operador es asociativo por la derecha (se evalúa de derecha a izquierda).
 * </p>
 *
 * @see UnaryExpressionNode
 */
public class UnaryExpressionParser implements IParser<UnaryExpressionNode> {
    private static final PostfixExpressionParser POSTFIX_PARSER = new PostfixExpressionParser();

    @Override
    public UnaryExpressionNode parse(ParserContext context) throws ParseException {
        Token operatorToken = context.getCurrentToken();

        if (isUnaryOperator(operatorToken.getType())) {
            Token operator = context.consume();
            return new UnaryExpressionNode(operator, parse(context));
        }

        PostfixExpressionNode operand = POSTFIX_PARSER.parse(context);
        return new UnaryExpressionNode(operatorToken, operand);
    }

    private static boolean isUnaryOperator(TokenType type) {
        return type == TokenType.NOT || type == TokenType.MINUS;
    }
}