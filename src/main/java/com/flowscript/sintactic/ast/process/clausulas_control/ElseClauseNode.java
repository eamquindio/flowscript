package com.flowscript.sintactic.ast.process.clausulas_control;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.lexer.Token;

/**
 * Represents an else clause in an exclusive gateway.
 * Syntax: else -> TargetTask
 * Defines the default branch when no when conditions match.
 */
public class ElseClauseNode extends ASTNode {
    private final String targetTask;

    public ElseClauseNode(Token elseToken, String targetTask) {
        super(elseToken);
        this.targetTask = targetTask;
    }

    public String getTargetTask() {
        return targetTask;
    }

    @Override
    public String getNodeType() {
        return "ElseClause";
    }

    @Override
    public String toString() {
        return "else -> " + targetTask;
    }
}