package com.flowscript.sintactic.ast.functions.literales;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

public class BooleanLiteralNode extends ExpressionNode {
    private final boolean value;
    public BooleanLiteralNode(Token literalToken) {
        super(literalToken);
        this.value = "true".equals(literalToken.getValue());
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

