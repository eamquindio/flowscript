package com.flowscript.sintactic.ast;

import com.flowscript.lexer.Token;

/**
 * Base class for all Abstract Syntax Tree nodes in FlowScript.
 * Represents a node in the parse tree with position information.
 */
public abstract class ASTNode {
    private final int line;
    private final int column;
    private final int position;

    public ASTNode(int line, int column, int position) {
        this.line = line;
        this.column = column;
        this.position = position;
    }

    public ASTNode(Token token) {
        this(token.getLine(), token.getColumn(), token.getPosition());
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getPosition() {
        return position;
    }

    /**
     * Returns a string representation of this AST node type.
     */
    public abstract String getNodeType();

    /**
     * Returns a detailed string representation for debugging.
     */
    @Override
    public String toString() {
        return getNodeType() + " at line " + line + ", column " + column;
    }
}