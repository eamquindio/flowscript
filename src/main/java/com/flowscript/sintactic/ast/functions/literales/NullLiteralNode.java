package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

/**
 * Represents null literal expressions.
 * Grammar: NullLiteral ::= 'nulo'
 * Examples: nulo
 */
public class NullLiteralNode extends ExpressionNode {

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