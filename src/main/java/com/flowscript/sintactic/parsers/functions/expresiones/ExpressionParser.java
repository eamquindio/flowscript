package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.*;

import java.util.List;

/**
 * Specialized parser for FlowScript expressions.
 * Handles operator precedence and expression parsing according to BNF grammar.
 */
public class ExpressionParser extends Parser {

    public ExpressionParser(List<Token> tokens) {
        super(tokens);
    }

    /**
     * Parses a complete expression.
     * Entry point for expression parsing.
     * Now delegates to the token-based expression parser.
     */
    public ExpressionNode parseExpression() throws ParseException {
        ExpressionCoordinator expressionCoordinator = new ExpressionCoordinator(tokens);
        expressionCoordinator.syncTo(currentToken, currentIndex);

        ExpressionNode result = expressionCoordinator.parseExpression();

        // Update our position to match the expression coordinator
        currentIndex = expressionCoordinator.getCurrentIndex();
        if (currentIndex < tokens.size()) {
            currentToken = tokens.get(currentIndex);
        }

        return result;
    }

    // Legacy methods kept for backward compatibility
    // All parsing is now delegated to ComprehensiveExpressionParser

    @Deprecated
    private ExpressionNode parseTernaryExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parseLogicalOrExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parseLogicalAndExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parseEqualityExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parseRelationalExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parseAdditiveExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parseMultiplicativeExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parseUnaryExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parsePostfixExpression() throws ParseException {
        return parseExpression();
    }

    @Deprecated
    private ExpressionNode parsePrimaryExpression() throws ParseException {
        return parseExpression();
    }

    // Legacy methods kept for backward compatibility but deprecated
    // All parsing now handled by ComprehensiveExpressionParser

    @Deprecated
    private List<ExpressionNode> parseArgumentList() throws ParseException {
        // This method is now handled by ComprehensiveExpressionParser
        throw new UnsupportedOperationException("Use ComprehensiveExpressionParser instead");
    }

    @Deprecated
    private ObjectMemberNode parseObjectMember() throws ParseException {
        // This method is now handled by ComprehensiveExpressionParser
        throw new UnsupportedOperationException("Use ComprehensiveExpressionParser instead");
    }

    @Deprecated
    private ExpressionNode parseObjectLiteral() throws ParseException {
        // This method is now handled by ComprehensiveExpressionParser
        throw new UnsupportedOperationException("Use ComprehensiveExpressionParser instead");
    }

    @Deprecated
    private ExpressionNode parseListLiteral() throws ParseException {
        // This method is now handled by ComprehensiveExpressionParser
        throw new UnsupportedOperationException("Use ComprehensiveExpressionParser instead");
    }

    /**
     * Synchronizes this parser's position with another parser.
     */
    public void syncTo(Token token, int index) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals(token)) {
                this.currentIndex = i;
                this.currentToken = token;
                break;
            }
        }
    }
}