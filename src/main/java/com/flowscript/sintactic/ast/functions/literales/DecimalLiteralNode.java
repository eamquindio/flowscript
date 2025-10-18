package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;
import java.math.BigDecimal;

/**
 * Represents decimal literal expressions.
 * Grammar: DecimalLiteral ::= DECIMAL_TOKEN
 * Examples: 3.14, 1.5e10, 0.001
 */
public class DecimalLiteralNode extends ExpressionNode {
    private final String rawValue;
    private final BigDecimal value;

    public DecimalLiteralNode(Token literalToken) {
        super(literalToken);
        this.rawValue = literalToken.getValue();
        this.value = new BigDecimal(rawValue);
    }

    public String getRawValue() {
        return rawValue;
    }

    public BigDecimal getValue() {
        return value;
    }

    public double getDoubleValue() {
        return value.doubleValue();
    }

    public float getFloatValue() {
        return value.floatValue();
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
        return "DecimalLiteral(" + rawValue + ")";
    }
}