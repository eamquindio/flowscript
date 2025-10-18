package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;

public class BooleanLiteralNode extends LiteralNode  {
    private final boolean value;

    public BooleanLiteralNode(Token literalToken) {
        super(literalToken);
        String value = literalToken.getValue();
        this.value = "true".equalsIgnoreCase(value);
    }

    public boolean getBooleanValue() {
        return value;
    }

    @Override
    public Object getValue() {
        return value; 
    }

    @Override
    public String getNodeType() {
        return "BooleanLiteral";
    }

    @Override
    public String getExpressionType() {
        return "booleano";
    }

    @Override
    public String toString() {
        return "BooleanLiteral(" + value + ")";
    }
}