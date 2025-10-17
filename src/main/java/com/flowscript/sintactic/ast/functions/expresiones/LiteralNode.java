package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * Represents literal values (numbers, strings, booleans, null).
 * Examples: 42, 3.14, "hello", true, false, null
 */
public class LiteralNode extends PrimaryExpressionNode {
  private final Object value;
  private final TokenType literalType;

  public LiteralNode(Token literalToken) {
    super(literalToken);
    this.literalType = literalToken.getType();
    this.value = parseLiteralValue(literalToken);
  }

  private Object parseLiteralValue(Token token) {
    System.out.println("DEBUG: LiteralNode recibió token " + token.getType() + " con valor " + token.getValue());
    switch (token.getType()) {
      case INTEGER_LITERAL:
        return Long.parseLong(token.getValue().replace("_", ""));
      case DECIMAL_LITERAL:
        return Double.parseDouble(token.getValue());
      case STRING_LITERAL:
        return token.getValue(); // Already processed by lexer
      case TRUE:
        return true;
      case FALSE:
        return false;
      case NULL:
        return null;
      default:
        throw new IllegalArgumentException("Unsupported literal type: " + token.getType());
    }
  }

  public Object getValue() {
    return value;
  }

  public TokenType getLiteralType() {
    return literalType;
  }

  @Override
  public String getNodeType() {
    return "Literal";
  }

  @Override
  public String getExpressionType() {
    switch (literalType) {
      case INTEGER_LITERAL:
        return "integer";
      case DECIMAL_LITERAL:
        return "decimal";
      case STRING_LITERAL:
        return "text";
      case TRUE:
      case FALSE:
        return "boolean";
      case NULL:
        return "null";
      default:
        return "unknown";
    }
  }

  @Override
  public String toString() {
    return "Literal(" + literalType + "=" + value + ")";
  }
}
