package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalAndExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.EqualityExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.RelationalExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;
import com.flowscript.sintactic.ast.functions.expresiones.PrimaryExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;
import com.flowscript.sintactic.ast.functions.expresiones.IdentifierNode;
import com.flowscript.sintactic.ast.functions.expresiones.UnaryExpressionNode;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.ast.functions.literales.IntegerLiteralNode;
import com.flowscript.sintactic.ast.functions.literales.DecimalLiteralNode;
import com.flowscript.sintactic.ast.functions.literales.StringLiteralNode;
import com.flowscript.sintactic.ast.functions.literales.BooleanLiteralNode;
import com.flowscript.sintactic.ast.functions.literales.NullLiteralNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import java.util.List;
import java.util.ArrayList;

/**
 * Expression coordinator that manages all expression parsing using individual parsers.
 * Implements the architecture where each grammar rule has its own parser class.
 * Works with the existing token list approach from the original system.
 */
public class ExpressionCoordinator extends Parser {

    public ExpressionCoordinator(List<Token> tokens) {
        super(tokens);
    }

    /**
     * Main expression parsing entry point.
     * Grammar: Expression ::= LogicalOrExpression
     */
    public ExpressionNode parseExpression() throws ParseException {
        return parseLogicalOrExpression();
    }

    /**
     * LogicalOrExpression ::= LogicalAndExpression ( 'o' LogicalAndExpression )*
     */
    private ExpressionNode parseLogicalOrExpression() throws ParseException {
        ExpressionNode left = parseLogicalAndExpression();

        if (!getCurrentToken().getValue().equals("o")) {
            return left;
        }

        LogicalOrExpressionNode orExpr = new LogicalOrExpressionNode(left.getToken(), left);

        while (getCurrentToken().getValue().equals("o")) {
            Token orToken = consume();
            ExpressionNode right = parseLogicalAndExpression();
            orExpr.addOperand(orToken, right);
        }

        return orExpr.getSimplified();
    }

    /**
     * LogicalAndExpression ::= EqualityExpression ( 'y' EqualityExpression )*
     */
    private ExpressionNode parseLogicalAndExpression() throws ParseException {
        ExpressionNode left = parseEqualityExpression();

        if (!getCurrentToken().getValue().equals("y")) {
            return left;
        }

        LogicalAndExpressionNode andExpr = new LogicalAndExpressionNode(left.getToken(), left);

        while (getCurrentToken().getValue().equals("y")) {
            Token andToken = consume();
            ExpressionNode right = parseEqualityExpression();
            andExpr.addOperand(andToken, right);
        }

        return andExpr.getSimplified();
    }

    /**
     * EqualityExpression ::= RelationalExpression ( ('==' | '!=') RelationalExpression )*
     */
    private ExpressionNode parseEqualityExpression() throws ParseException {
        ExpressionNode left = parseRelationalExpression();

        String op = getCurrentToken().getValue();
        if (!op.equals("==") && !op.equals("!=")) {
            return left;
        }

        EqualityExpressionNode equalityExpr = new EqualityExpressionNode(left.getToken(), left);

        while (true) {
            String operator = getCurrentToken().getValue();
            if (!operator.equals("==") && !operator.equals("!=")) {
                break;
            }

            Token operatorToken = consume();
            ExpressionNode right = parseRelationalExpression();
            equalityExpr.addOperand(operatorToken, right);
        }

        return equalityExpr.getSimplified();
    }

    /**
     * RelationalExpression ::= AdditiveExpression ( ('<' | '>' | '<=' | '>=') AdditiveExpression )*
     */
    private ExpressionNode parseRelationalExpression() throws ParseException {
        ExpressionNode left = parseAdditiveExpression();

        String op = getCurrentToken().getValue();
        if (!isRelationalOperator(op)) {
            return left;
        }

        RelationalExpressionNode relationalExpr = new RelationalExpressionNode(left.getToken(), left);

        while (isRelationalOperator(getCurrentToken().getValue())) {
            Token operatorToken = consume();
            ExpressionNode right = parseAdditiveExpression();
            relationalExpr.addOperand(operatorToken, right);
        }

        return relationalExpr.getSimplified();
    }

    /**
     * AdditiveExpression ::= MultiplicativeExpression ( ('+' | '-') MultiplicativeExpression )*
     */
    private ExpressionNode parseAdditiveExpression() throws ParseException {
        ExpressionNode left = parseMultiplicativeExpression();

        String op = getCurrentToken().getValue();
        if (!"+".equals(op) && !"-".equals(op)) {
            return left;
        }

        AdditiveExpressionNode additiveExpr = new AdditiveExpressionNode(left.getToken(), left);

        while (true) {
            String operator = getCurrentToken().getValue();
            if (!"+".equals(operator) && !"-".equals(operator)) {
                break;
            }

            Token operatorToken = consume();
            ExpressionNode right = parseMultiplicativeExpression();
            additiveExpr.addOperand(operatorToken, right);
        }

        return additiveExpr.getSimplified();
    }

    /**
     * MultiplicativeExpression ::= UnaryExpression ( ('*' | '/' | '%') UnaryExpression )*
     */
    private ExpressionNode parseMultiplicativeExpression() throws ParseException {
        ExpressionNode left = parseUnaryExpression();

        String op = getCurrentToken().getValue();
        if (!isMultiplicativeOperator(op)) {
            return left;
        }

        MultiplicativeExpressionNode multiplicativeExpr = new MultiplicativeExpressionNode(left.getToken(), left);

        while (isMultiplicativeOperator(getCurrentToken().getValue())) {
            Token operatorToken = consume();
            ExpressionNode right = parseUnaryExpression();
            multiplicativeExpr.addOperand(operatorToken, right);
        }

        return multiplicativeExpr.getSimplified();
    }

    /**
     * UnaryExpression ::= ( '+' | '-' | 'no' ) UnaryExpression | PostfixExpression
     */
    private ExpressionNode parseUnaryExpression() throws ParseException {
        String op = getCurrentToken().getValue();

        if (isUnaryOperator(op)) {
            Token operatorToken = consume();
            ExpressionNode operand = parseUnaryExpression(); // Right-associative recursion
            return new UnaryExpressionNode(operatorToken, operand);
        }

        return parsePostfixExpression();
    }

    /**
     * PostfixExpression ::= PrimaryExpression PostfixOperator*
     */
    private ExpressionNode parsePostfixExpression() throws ParseException {
        ExpressionNode primary = parsePrimaryExpression();

        if (!isPostfixOperator()) {
            return primary;
        }

        PostfixExpressionNode postfixExpr = new PostfixExpressionNode(primary.getToken(), primary);

        while (isPostfixOperator()) {
            PostfixOperatorNode operator = parsePostfixOperator();
            postfixExpr.addOperator(operator);
        }

        return postfixExpr.getSimplified();
    }

    /**
     * PrimaryExpression ::= IDENTIFIER | Literal | ObjectLiteral | ListLiteral | '(' Expression ')'
     */
    private ExpressionNode parsePrimaryExpression() throws ParseException {
        Token currentToken = getCurrentToken();

        switch (currentToken.getType()) {
            case IDENTIFIER:
                Token identifierToken = consume();
                return new IdentifierNode(identifierToken);

            case INTEGER_LITERAL:
                Token intToken = consume();
                return new IntegerLiteralNode(intToken);

            case DECIMAL_LITERAL:
                Token decimalToken = consume();
                return new DecimalLiteralNode(decimalToken);

            case STRING_LITERAL:
                Token stringToken = consume();
                return new StringLiteralNode(stringToken);

            case TRUE:
            case FALSE:
                Token boolToken = consume();
                return new BooleanLiteralNode(boolToken);

            case NULL:
                Token nullToken = consume();
                return new NullLiteralNode(nullToken);

            case LEFT_PAREN:
                return parseParenthesizedExpression();

            case LEFT_BRACKET:
                return parseListLiteral();

            case LEFT_BRACE:
                return parseObjectLiteral();

            default:
                throw new ParseException("Unexpected token in primary expression: " + currentToken);
        }
    }

    // Helper methods
    private boolean isRelationalOperator(String op) {
        return "<".equals(op) || ">".equals(op) || "<=".equals(op) || ">=".equals(op);
    }

    private boolean isMultiplicativeOperator(String op) {
        return "*".equals(op) || "/".equals(op) || "%".equals(op);
    }

    private boolean isUnaryOperator(String op) {
        return "+".equals(op) || "-".equals(op) || "no".equals(op);
    }

    private boolean isPostfixOperator() {
        String value = getCurrentToken().getValue();
        return ".".equals(value) || "[".equals(value) || "(".equals(value);
    }

    private ExpressionNode parseParenthesizedExpression() throws ParseException {
        Token leftParenToken = consume(); // consume '('

        ExpressionNode innerExpression = parseExpression();

        if (!")".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected ')' after parenthesized expression");
        }
        consume(); // consume ')'

        return new PrimaryExpressionNode.ParenthesizedExpressionNode(leftParenToken, innerExpression);
    }

    private ExpressionNode parseListLiteral() throws ParseException {
        Token leftBracketToken = consume(); // consume '['

        if ("]".equals(getCurrentToken().getValue())) {
            consume(); // consume ']'
            return new ListLiteralNode(leftBracketToken);
        }

        ExpressionListNode expressionList = parseExpressionList();

        if (!"]".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected ']' after list elements");
        }
        consume(); // consume ']'

        return new ListLiteralNode(leftBracketToken, expressionList);
    }

    private ExpressionNode parseObjectLiteral() throws ParseException {
        Token leftBraceToken = consume(); // consume '{'

        if ("}".equals(getCurrentToken().getValue())) {
            consume(); // consume '}'
            return new ObjectLiteralNode(leftBraceToken);
        }

        ObjectMemberListNode memberList = parseObjectMemberList();

        if (!"}".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected '}' after object members");
        }
        consume(); // consume '}'

        return new ObjectLiteralNode(leftBraceToken, memberList);
    }

    private PostfixOperatorNode parsePostfixOperator() throws ParseException {
        Token operatorToken = getCurrentToken();
        String operator = operatorToken.getValue();

        switch (operator) {
            case ".":
                return parsePropertyAccess();
            case "[":
                return parseIndexAccess();
            case "(":
                return parseFunctionCall();
            default:
                throw new ParseException("Unexpected postfix operator: " + operator);
        }
    }

    private PostfixOperatorNode.PropertyAccessOperatorNode parsePropertyAccess() throws ParseException {
        Token dotToken = consume(); // consume '.'

        if (getCurrentToken().getType() != TokenType.IDENTIFIER) {
            throw new ParseException("Expected identifier after '.'");
        }

        String propertyName = consume().getValue();
        return new PostfixOperatorNode.PropertyAccessOperatorNode(dotToken, propertyName);
    }

    private PostfixOperatorNode.IndexAccessOperatorNode parseIndexAccess() throws ParseException {
        Token leftBracketToken = consume(); // consume '['

        ExpressionNode index = parseExpression();

        if (!"]".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected ']' after index expression");
        }
        consume(); // consume ']'

        return new PostfixOperatorNode.IndexAccessOperatorNode(leftBracketToken, index);
    }

    private PostfixOperatorNode.FunctionCallOperatorNode parseFunctionCall() throws ParseException {
        Token leftParenToken = consume(); // consume '('

        List<ExpressionNode> arguments = new ArrayList<>();

        if (!")".equals(getCurrentToken().getValue())) {
            ArgumentListNode argumentList = parseArgumentList();
            arguments = argumentList.getArguments();
        }

        if (!")".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected ')' after function arguments");
        }
        consume(); // consume ')'

        return new PostfixOperatorNode.FunctionCallOperatorNode(leftParenToken, arguments);
    }

    private ArgumentListNode parseArgumentList() throws ParseException {
        ExpressionNode firstArg = parseExpression();
        ArgumentListNode argumentList = new ArgumentListNode(firstArg);

        while (",".equals(getCurrentToken().getValue())) {
            consume(); // consume ','
            ExpressionNode arg = parseExpression();
            argumentList.addArgument(arg);
        }

        return argumentList;
    }

    private ExpressionListNode parseExpressionList() throws ParseException {
        ExpressionNode firstExpr = parseExpression();
        ExpressionListNode expressionList = new ExpressionListNode(firstExpr);

        while (",".equals(getCurrentToken().getValue())) {
            consume(); // consume ','
            ExpressionNode expr = parseExpression();
            expressionList.addExpression(expr);
        }

        return expressionList;
    }

    private ObjectMemberListNode parseObjectMemberList() throws ParseException {
        ObjectMemberListNode.ObjectMemberNode firstMember = parseObjectMember();
        ObjectMemberListNode memberList = new ObjectMemberListNode(firstMember);

        while (",".equals(getCurrentToken().getValue())) {
            consume(); // consume ','
            ObjectMemberListNode.ObjectMemberNode member = parseObjectMember();
            memberList.addMember(member);
        }

        return memberList;
    }

    private ObjectMemberListNode.ObjectMemberNode parseObjectMember() throws ParseException {
        Token keyToken = getCurrentToken();
        String key;

        if (keyToken.getType() == TokenType.IDENTIFIER) {
            key = consume().getValue();
        } else if (keyToken.getType() == TokenType.STRING_LITERAL) {
            key = consume().getValue();
            // Remove quotes from string key
            key = key.substring(1, key.length() - 1);
        } else {
            throw new ParseException("Expected identifier or string for object key");
        }

        if (!":".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected ':' after object key");
        }
        consume(); // consume ':'

        ExpressionNode value = parseExpression();

        return new ObjectMemberListNode.ObjectMemberNode(keyToken, key, value);
    }

    protected Token consume() throws ParseException {
        Token token = getCurrentToken();
        nextToken();
        return token;
    }

    /**
     * Returns the current index in the token list.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Synchronizes this parser's position with another parser.
     */
    public void syncTo(Token token, int index) {
        this.currentIndex = index;
        this.currentToken = token;
    }
}