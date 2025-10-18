package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;
import com.flowscript.lexer.Token;

/**
 * Represents boolean literal expressions.
 * Grammar: BooleanLiteral ::= 'verdadero' | 'falso'
 * Examples: verdadero, falso
 */
public class BooleanLiteralNode extends LiteralNode  {
    private final boolean value;

    public BooleanLiteralNode(Token literalToken) {
        super(literalToken);
        this.value = "true".equalsIgnoreCase(literalToken.getValue());
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