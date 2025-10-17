package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;

/**
 * Represents property access expressions.
 * Example: customer.name, order.total_amount, Http.get
 */
public class PropertyAccessNode extends ExpressionNode {
  private final ExpressionNode object;
  private final String propertyName;

  public PropertyAccessNode(ExpressionNode object, Token dotToken, String propertyName) {
    super(dotToken);
    this.object = object;
    this.propertyName = propertyName;
  }

  public ExpressionNode getObject() {
    return object;
  }

  public String getPropertyName() {
    return propertyName;
  }

  @Override
  public String getNodeType() {
    return "PropertyAccess";
  }

  @Override
  public String getExpressionType() {
    // Type would be determined by symbol table lookup during semantic analysis
    return "property";
  }

  @Override
  public String toString() {
    return "PropertyAccess(" + object.getNodeType() + "." + propertyName + ")";
  }
}
