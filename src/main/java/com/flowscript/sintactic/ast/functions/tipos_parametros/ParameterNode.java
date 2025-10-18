package com.flowscript.sintactic.ast.functions.tipos_parametros;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;

/**
 * Represents a function parameter.
 * Syntax: paramName: type
 * Part of function declarations.
 */
public class ParameterNode extends ASTNode {
    private final String name;
    private final TypeNode type;

    public ParameterNode(String name, TypeNode type) {
        super(type.getLine(), type.getColumn(), type.getPosition());
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TypeNode getType() {
        return type;
    }

    public String getTypeName() {
        return type.getTypeName();
    }

    @Override
    public String getNodeType() {
        return "Parameter";
    }

    @Override
    public String toString() {
        return name + ": " + type.getTypeName();
    }
}