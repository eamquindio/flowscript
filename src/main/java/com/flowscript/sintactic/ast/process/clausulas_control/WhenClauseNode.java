package com.flowscript.sintactic.ast.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Represents a when clause in an exclusive gateway.
 * Syntax: when condition -> TargetTask
 * Defines a conditional branch in the process flow.
 */
public class WhenClauseNode extends ASTNode {
    private final ExpressionNode condition;
    private final String targetTask;

    public WhenClauseNode(Token whenTok, ExpressionNode condition, String targetTask) {
        super(condition.getLine(), condition.getColumn(), condition.getPosition());
        this.condition = condition;
        this.targetTask = targetTask;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public String getTargetTask() {
        return targetTask;
    }

    @Override
    public String getNodeType() {
        return "WhenClause";
    }

    @Override
    public String toString() {
        return "when " + condition.getNodeType() + " -> " + targetTask;
    }
}