package com.flowscript.semantic.errors;

import com.flowscript.sintactic.ast.ASTNode;

public class SemanticWarning extends SemanticIssue {
    public SemanticWarning(String code, String message, ASTNode location) {
        super(code, message, location);
    }

    @Override
    public String getSeverity() {
        return "WARNING";
    }
}
