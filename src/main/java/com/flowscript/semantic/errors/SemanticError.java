package com.flowscript.semantic.errors;

import com.flowscript.sintactic.ast.ASTNode;

public class SemanticError extends SemanticIssue {
    public SemanticError(String code, String message, ASTNode location) {
        super(code, message, location);
    }

    @Override
    public String getSeverity() {
        return "ERROR";
    }
}
