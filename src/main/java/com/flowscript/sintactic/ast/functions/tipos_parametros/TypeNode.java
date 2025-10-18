package com.flowscript.sintactic.ast.functions.tipos_parametros;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Represents type annotations in FlowScript.
 * Examples: integer, decimal, boolean, text, list, object, void
 */
public class TypeNode extends ASTNode {
    private final String typeName;

    public TypeNode(Token typeToken) {
        super(typeToken);
        this.typeName = typeToken.getValue();
    }

    public String getTypeName() {
        return typeName;
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
        return "Type(" + typeName + ")";
    }
}