package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

/**
 * Represents string literal expressions.
 * Grammar: StringLiteral ::= STRING_TOKEN
 * Examples: "Hello", "World\n", "Multi\nLine"
 */
public class StringLiteralNode extends ExpressionNode {
    private final String rawValue;
    private final String value;

    public StringLiteralNode(Token literalToken) {
        super(literalToken);
        this.rawValue = literalToken.getValue();
        this.value = parseStringLiteral(rawValue);
    }

    private String parseStringLiteral(String raw) {
        if (raw.length() < 2) return raw;

        String content = raw.substring(1, raw.length() - 1);

        return content.replace("\\n", "\n")
                     .replace("\\t", "\t")
                     .replace("\\r", "\r")
                     .replace("\\\\", "\\")
                     .replace("\\\"", "\"");
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
        return "StringLiteral(" + rawValue + ")";
    }
}