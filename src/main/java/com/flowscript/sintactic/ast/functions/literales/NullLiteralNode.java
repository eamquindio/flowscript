package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;

public class NullLiteralNode extends LiteralNode  {

    public NullLiteralNode(Token literalToken) {
        super(literalToken);
    }

    public Object getValue() {
        return null;
    }

    @Override
    public String getNodeType() {
        return "NullLiteral";
    }

    @Override
    public String getExpressionType() {
        return "nulo";
    }

    @Override
    public String toString() {
        return "NullLiteral(nulo)";
    }
}