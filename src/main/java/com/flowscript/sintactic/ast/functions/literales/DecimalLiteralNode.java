package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;

public class DecimalLiteralNode extends LiteralNode {
    private final String rawValue;
    private final String normValue;

    public DecimalLiteralNode(Token literalToken) {
        super(literalToken);
        String value = literalToken.getValue();
        this.rawValue = value;
        this.normValue = normalize(value);
    }

    private String normalize(String value) {
        if (value == null) {
            return "0.0";
        }
        else {
            return value.replace("E+", "e").replace("E", "e");
        }
    }

    public String getRawValue() {
        return rawValue;
    }

    public String getValue() {
        return normValue;
    }

    @Override
    public String getNodeType() {
        return "DecimalLiteral";
    }

    @Override
    public String getExpressionType() {
        return "decimal";
    }

    @Override
    public String toString() {
        return normValue;
    }
}