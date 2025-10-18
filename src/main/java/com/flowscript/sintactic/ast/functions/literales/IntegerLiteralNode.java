package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.lexer.Token;
import java.math.BigInteger;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;

public class IntegerLiteralNode extends LiteralNode {
    private final String rawValue;
    private final BigInteger numValue;

    public IntegerLiteralNode(Token literalToken) {
        super(literalToken);
        String value = literalToken.getValue();
        this.rawValue = value;
        this.numValue = new BigInteger(value.replace("_", ""));
    }

    public String getRawValue() {
        return rawValue;
    }

    public BigInteger getNumValue() {
        return numValue;
    }

    public long getLongValue() {
        return numValue.longValue();
    }

    public int getIntValue() {
        return numValue.intValue();
    }

    public boolean fitsInInt() {
        return numValue.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 &&
               numValue.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0;
    }

    public boolean fitsInLong() {
        return numValue.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0 &&
               numValue.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) >= 0;
    }

    @Override
    public String getNodeType() {
        return "IntegerLiteral";
    }

    @Override
    public String getExpressionType() {
        return "integer";
    }

    @Override
    public String toString() {
        return "IntegerLiteral(" + rawValue + ")";
    }
}