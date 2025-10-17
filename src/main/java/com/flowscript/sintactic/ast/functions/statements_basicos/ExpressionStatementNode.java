package com.flowscript.sintactic.ast.functions.statements_basicos;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Represents a statement that consists of a single expression.
 * Example: function_call(); variable_assignment = value;
 */
public class ExpressionStatementNode extends StatementNode {
  private final ExpressionNode expression;

  public ExpressionStatementNode(ExpressionNode expression) {
    super(expression.getLine(), expression.getColumn(), expression.getPosition());
    this.expression = expression;
  }

  public ExpressionNode getExpression() {
    return expression;
  }

  @Override
  public String getNodeType() {
    return "ExpressionStatement";
  }

  @Override
  public String toString() {
    return "ExpressionStatement(" + expression.getNodeType() + ")";
  }
}
