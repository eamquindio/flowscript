package com.flowscript.sintactic.ast.process.clausulas_control;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.lexer.Token;

/**
 * Represents a join clause in a parallel gateway.
 * Syntax: join -> TargetTask
 * Defines the synchronization point where all parallel branches converge.
 */
public class JoinClauseNode extends ASTNode {
    private final String targetTask;

    public JoinClauseNode(Token joinToken, String targetTask) {
        super(joinToken);
        this.targetTask = targetTask;
    }

    public String getTargetTask() {
        return targetTask;
    }

    @Override
    public String getNodeType() {
        return "JoinClause";
    }

    @Override
    public String toString() {
        return "join -> " + targetTask;
    }
}