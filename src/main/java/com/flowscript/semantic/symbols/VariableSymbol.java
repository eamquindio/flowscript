package com.flowscript.semantic.symbols;

import com.flowscript.semantic.types.Type;
import com.flowscript.sintactic.ast.ASTNode;

public class VariableSymbol extends Symbol {
    private final boolean isConstant;
    private final boolean isParameter;

    public VariableSymbol(String name, Type type, ASTNode declarationNode) {
        this(name, type, declarationNode, false, false);
    }

    public VariableSymbol(String name, Type type, ASTNode declarationNode,
                         boolean isConstant, boolean isParameter) {
        super(name, type, declarationNode);
        this.isConstant = isConstant;
        this.isParameter = isParameter;
    }

    public boolean isConstant() { return isConstant; }
    public boolean isParameter() { return isParameter; }

    @Override
    public String getKind() {
        if (isParameter) return "parameter";
        if (isConstant) return "constant";
        return "variable";
    }
}
