package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

/**
 * Represents boolean literal expressions.
 * Grammar: BooleanLiteral ::= 'verdadero' | 'falso'
 * Examples: verdadero, falso
 */
public class BooleanLiteralNode extends ExpressionNode {
    private final boolean value;

    public BooleanLiteralNode(Token literalToken) {
        super(literalToken);
        String val = literalToken.getValue();
        this.value = "verdadero".equals(val) || "true".equals(val);
    }

    public boolean getValue() {
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