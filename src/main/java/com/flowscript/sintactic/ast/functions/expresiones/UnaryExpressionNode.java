package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;

/**
 * Represents unary expressions like negation, logical not.
 * Examples: -x, not condition
 */
public class UnaryExpressionNode extends ExpressionNode {
  private final String operator;
  private final ExpressionNode operand;

  public UnaryExpressionNode(Token operatorToken, ExpressionNode operand) {
    super(operatorToken);
    this.operator = operatorToken.getValue();
    this.operand = operand;
  }

  public String getOperator() {
    return operator;
  }

  public ExpressionNode getOperand() {
    return operand;
  }

  @Override
  public String getNodeType() {
    return "UnaryExpression";
  }

  @Override
  public String getExpressionType() {
    switch (operator) {
      case "not":
        return "boolean";
      case "-":
        return operand.getExpressionType(); // Preserve numeric type
      default:
        return "unknown";
    }
  }

  @Override
  public String toString() {
    return "UnaryExpression(" + operator + ")";
  }
}
