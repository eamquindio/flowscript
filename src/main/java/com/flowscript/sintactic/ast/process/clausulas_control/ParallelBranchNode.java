package com.flowscript.sintactic.ast.process.clausulas_control;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.lexer.Token;

/**
 * Represents a parallel branch in a parallel gateway.
 * Syntax: branch -> TargetTask
 * Defines one of multiple concurrent execution paths.
 */
public class ParallelBranchNode extends ASTNode {
    private final String targetTask;

    public ParallelBranchNode(Token branchToken, String targetTask) {
        super(branchToken);
        this.targetTask = targetTask;
    }

    public String getTargetTask() {
        return targetTask;
    }

    @Override
    public String getNodeType() {
        return "ParallelBranch";
    }

    @Override
    public String toString() {
        return "branch -> " + targetTask;
    }
}