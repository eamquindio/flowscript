package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents equality expressions: expr == expr, expr != expr
 * Grammar: EqualityExpression ::= RelationalExpression ( ( '==' | '!=' ) RelationalExpression )*
 * Left-associative with precedence level 9
 */
public class EqualityExpressionNode extends ExpressionNode {
    private final List<ExpressionNode> operands;
    private final List<Token> operators; // '==' or '!=' tokens

    public EqualityExpressionNode(ExpressionNode left, Token firstToken, ExpressionNode firstOperand) {
        super(firstToken);
        this.operands = new ArrayList<>();
        this.operators = new ArrayList<>();
        operands.add(firstOperand);
    }

    public void addOperand(Token operatorToken, ExpressionNode operand) {
        operators.add(operatorToken);
        operands.add(operand);
    }

    public List<ExpressionNode> getOperands() {
        return new ArrayList<>(operands);
    }

    public List<Token> getOperators() {
        return new ArrayList<>(operators);
    }

    public boolean isSingleOperand() {
        return operands.size() == 1;
    }

    /**
     * If this is a single operand, return it directly.
     */
    public ExpressionNode getSingleOperand() {
        if (operands.size() == 1) {
            return operands.get(0);
        }
        throw new IllegalStateException("Not a single operand expression");
    }

    /**
     * Returns a simplified version of this expression.
     * If there's only one operand, returns that operand directly.
     */
    public ExpressionNode getSimplified() {
        if (operands.size() == 1) {
            return operands.get(0);
        }
        return this;
    }

    @Override
    public String getNodeType() {
        return "EqualityExpression";
    }

    @Override
    public String getExpressionType() {
        return "boolean";
    }

    @Override
    public String toString() {
        return "EqualityExpression with " + operands.size() + " operands";
    }
}