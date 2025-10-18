package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

/**
 * Represents ternary conditional expressions.
 * Example: condition ? value_if_true : value_if_false
 */
public class TernaryExpressionNode extends ExpressionNode {
    private final ExpressionNode condition;
    private final ExpressionNode trueExpression;
    private final ExpressionNode falseExpression;

    public TernaryExpressionNode(ExpressionNode condition, Token questionToken,
                                ExpressionNode trueExpression, ExpressionNode falseExpression) {
        super(questionToken);
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public ExpressionNode getTrueExpression() {
        return trueExpression;
    }

    public ExpressionNode getFalseExpression() {
        return falseExpression;
    }

    @Override
    public String getNodeType() {
        return "TernaryExpression";
    }

    @Override
    public String getExpressionType() {
        // The type is determined by the true/false branches
        // In a proper type system, these should be compatible
        return trueExpression.getExpressionType();
    }

    @Override
    public String toString() {
        return "TernaryExpression(? :)";
    }
}