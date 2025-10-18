package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;
import com.flowscript.lexer.Token;

/**
 * Represents decimal literal expressions.
 * Grammar: DecimalLiteral ::= DECIMAL_TOKEN
 * Examples: 3.14, 1.23e-5, 2.0e10
 */
public class DecimalLiteralNode extends LiteralNode {
    private final String rawValue;
    private final String normalizedValue;

    public DecimalLiteralNode(Token literalToken) {
        super(literalToken);
        this.rawValue = literalToken.getValue();
        this.normalizedValue = normalize(rawValue);
    }

    private String normalize(String value) {
        if (value == null) return "0.0";
        return value.replace("E+", "e")
                    .replace("E", "e");
    }

    public String getRawValue() {
        return rawValue;
    }

    public String getValue() {
        return normalizedValue;
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
        return normalizedValue;
    }
}