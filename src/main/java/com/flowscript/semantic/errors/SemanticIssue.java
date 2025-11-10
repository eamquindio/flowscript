package com.flowscript.semantic.errors;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.lexer.Token;

public abstract class SemanticIssue {
    protected final String code;
    protected final String message;
    protected final int line;
    protected final int column;
    protected final ASTNode location;

    public SemanticIssue(String code, String message, ASTNode location) {
        this.code = code;
        this.message = message;
        this.location = location;

        // Extract line/column from AST node
        if (location != null) {
            this.line = location.getLine();
            this.column = location.getColumn();
        } else {
            this.line = -1;
            this.column = -1;
        }
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public int getLine() { return line; }
    public int getColumn() { return column; }
    public ASTNode getLocation() { return location; }

    public abstract String getSeverity();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSeverity()).append(" ");
        sb.append(code).append(": ");
        sb.append(message);
        if (line >= 0) {
            sb.append(" at line ").append(line);
            if (column >= 0) {
                sb.append(", column ").append(column);
            }
        }
        return sb.toString();
    }
}
