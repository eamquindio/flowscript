package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;

/**
 * Represents binary expressions like addition, comparison, logical operations.
 * Examples: a + b, x == y, condition and other_condition
 */
public class BinaryExpressionNode extends ExpressionNode {
  private final ExpressionNode left;
  private final String operator;
  private final ExpressionNode right;

  public BinaryExpressionNode(ExpressionNode left, Token operatorToken, ExpressionNode right) {
    super(operatorToken);
    this.left = left;
    this.operator = operatorToken.getValue();
    this.right = right;
  }

  public ExpressionNode getLeft() {
    return left;
  }

  public String getOperator() {
    return operator;
  }

  public ExpressionNode getRight() {
    return right;
  }

  @Override
  public String getNodeType() {
    return "BinaryExpression";
  }

  @Override
  public String getExpressionType() {
    // Type inference based on operator
    switch (operator) {
      case "==":
      case "!=":
      case "<":
      case ">":
      case "<=":
      case ">=":
      case "and":
      case "or":
        return "boolean";
      case "+":
      case "-":
      case "*":
      case "/":
      case "%":
        // Would need type checking to determine if integer or decimal
        return "numeric";
      default:
        return "unknown";
    }
  }

  @Override
  public String toString() {
    return "BinaryExpression(" + operator + ")";
  }
}
