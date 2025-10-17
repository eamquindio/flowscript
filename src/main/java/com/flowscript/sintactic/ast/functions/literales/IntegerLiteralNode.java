package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;
import java.math.BigInteger;

/**
 * Represents integer literal expressions.
 * Grammar: IntegerLiteral ::= INTEGER_TOKEN ( '_' INTEGER_TOKEN )*
 * Examples: 42, 1000, 1_000_000
 */
public class IntegerLiteralNode extends ExpressionNode {
  private final String rawValue; // As written in source
  private final BigInteger value; // Parsed numeric value

  public IntegerLiteralNode(Token literalToken) {
    super(literalToken);
    this.rawValue = literalToken.getValue();
    // Parse the integer, removing underscores
    String cleanValue = rawValue.replace("_", "");
    this.value = new BigInteger(cleanValue);
  }

  public String getRawValue() {
    return rawValue;
  }

  public String getValue() {
    return rawValue;
  }

  public BigInteger getBigIntegerValue() {
    return value;
  }

  public long getLongValue() {
    return value.longValue();
  }

  public int getIntValue() {
    return value.intValue();
  }

  public boolean fitsInInt() {
    return value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 &&
        value.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0;
  }

  public boolean fitsInLong() {
    return value.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0 &&
        value.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) >= 0;
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
