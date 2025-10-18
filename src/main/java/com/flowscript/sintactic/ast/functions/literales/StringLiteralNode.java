package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;

public class StringLiteralNode extends LiteralNode  {
    private final String rawValue;
    private final String value;

    public StringLiteralNode(Token literalToken) {
        super(literalToken);
        this.rawValue = "\"" + literalToken.getValue() + "\"";
        this.value = parseStringLiteral(rawValue);
    }

    private String parseStringLiteral(String raw) {
        if (raw.length() >= 2 && raw.startsWith("\"") && raw.endsWith("\"")) {
            return raw.substring(1, raw.length() - 1);
        }
        return raw;
    }

    public String getRawValue() {
        return rawValue;
    }

    public String getValue() {
        return value;
    }

    public int length() {
        return value.length();
    }

    @Override
    public String getNodeType() {
        return "StringLiteral";
    }

    @Override
    public String getExpressionType() {
        return "texto";
    }

    @Override
    public String toString() {
        return rawValue;
    }
}