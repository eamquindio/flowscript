package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Represents a single key-value pair in an object literal.
 * Syntax: key: value or "string_key": value
 * Part of object literal expressions.
 */
public class ObjectMemberNode extends ASTNode {
    private final String key;
    private final boolean keyIsString; // true if key is "string", false if identifier
    private final ExpressionNode value;

    public ObjectMemberNode(String key, boolean keyIsString, ExpressionNode value) {
        super(value.getLine(), value.getColumn(), value.getPosition());
        this.key = key;
        this.keyIsString = keyIsString;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public boolean isKeyString() {
        return keyIsString;
    }

    public ExpressionNode getValue() {
        return value;
    }

    @Override
    public String getNodeType() {
        return "ObjectMember";
    }

    @Override
    public String toString() {
        String keyRepresentation = keyIsString ? "\"" + key + "\"" : key;
        return keyRepresentation + ": " + value.getNodeType();
    }
}