package com.flowscript.sintactic.ast.functions.tipos_parametros;
import com.flowscript.semantic.visitor.ASTVisitor;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Represents type annotations in FlowScript.
 * Examples: integer, decimal, boolean, text, list, object, void
 * Supports generic types: lista<entero>, lista<lista<texto>>
 */
public class TypeNode extends ASTNode {
    private final String typeName;
    private final TypeNode genericType;

    public TypeNode(Token typeToken) {
        super(typeToken);
        this.typeName = typeToken.getValue();
        this.genericType = null;
    }

    public TypeNode(String typeName) {
        super(null);
        this.typeName = typeName;
        this.genericType = null;
    }

    public TypeNode(String typeName, TypeNode genericType) {
        super(null);
        this.typeName = typeName;
        this.genericType = genericType;
    }

    public String getTypeName() {
        return typeName;
    }

    public TypeNode getGenericType() {
        return genericType;
    }

    public boolean isPrimitive() {
        return typeName.equals("integer") || typeName.equals("decimal") ||
               typeName.equals("boolean") || typeName.equals("text");
    }

    public boolean isComplex() {
        return typeName.equals("list") || typeName.equals("object");
    }

    public boolean isVoid() {
        return typeName.equals("void");
    }

    @Override
    public String getNodeType() {
        return "Type";
    }

    @Override
    public String toString() {
        if (genericType != null) {
            return "Type(" + typeName + "<" + genericType.toString() + ">)";
        }
        return "Type(" + typeName + ")";
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
