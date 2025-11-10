package com.flowscript.semantic.symbols;

import com.flowscript.semantic.types.Type;
import com.flowscript.sintactic.ast.ASTNode;

public abstract class Symbol {
    protected final String name;
    protected final Type type;
    protected final ASTNode declarationNode;
    protected boolean used = false;

    public Symbol(String name, Type type, ASTNode declarationNode) {
        this.name = name;
        this.type = type;
        this.declarationNode = declarationNode;
    }

    public String getName() { return name; }
    public Type getType() { return type; }
    public ASTNode getDeclarationNode() { return declarationNode; }
    public boolean isUsed() { return used; }
    public void markUsed() { this.used = true; }

    public abstract String getKind();
}
