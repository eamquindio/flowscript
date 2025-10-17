package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

/**
 * Represents throw statements for error throwing.
 * Example: throw { type: "ValidationError", message: "Invalid input" }
 */
public class ThrowStatementNode extends StatementNode {
  private final ExpressionNode expression;

  public ThrowStatementNode(Token throwToken, ExpressionNode expression) {
    super(throwToken);
    this.expression = expression;
  }

  public ExpressionNode getExpression() {
    return expression;
  }

  @Override
  public String getNodeType() {
    return "ThrowStatement";
  }

  @Override
  public String toString() {
    return "ThrowStatement(" + expression.getNodeType() + ")";
  }
}
