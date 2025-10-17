package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;
import com.flowscript.lexer.Token;
import java.math.BigInteger;

/**
 * Represents integer literal expressions.
 * Grammar: IntegerLiteral ::= INTEGER_TOKEN ( '_' INTEGER_TOKEN )*
 * Examples: 42, 1000, 1_000_000
 */
public class IntegerLiteralNode extends LiteralNode {
    private final String rawValue;         
    private final BigInteger numericValue; 

    public IntegerLiteralNode(Token literalToken) {
        super(literalToken);
        this.rawValue = literalToken.getValue();
        this.numericValue = new BigInteger(rawValue.replace("_", ""));
    }

    public String getRawValue() {
        return rawValue;
    }

    public BigInteger getNumericValue() {
        return numericValue;
    }

    public long getLongValue() {
        return numericValue.longValue();
    }

    public int getIntValue() {
        return numericValue.intValue();
    }

    public boolean fitsInInt() {
        return numericValue.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 &&
               numericValue.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0;
    }

    public boolean fitsInLong() {
        return numericValue.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0 &&
               numericValue.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) >= 0;
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