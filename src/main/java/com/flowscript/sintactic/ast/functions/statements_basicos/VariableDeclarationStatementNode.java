package com.flowscript.sintactic.ast.functions.statements_basicos;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

/**
 * Represents variable declaration statements within functions and processes.
 * Example: customer_data = get_customer(customer_id)
 */
public class VariableDeclarationStatementNode extends StatementNode {
  private final String variableName;
  private final ExpressionNode initializer;

  public VariableDeclarationStatementNode(Token identifierToken, String variableName, ExpressionNode initializer) {
    super(identifierToken);
    this.variableName = variableName;
    this.initializer = initializer;
  }

  public String getVariableName() {
    return variableName;
  }

  public ExpressionNode getInitializer() {
    return initializer;
  }

  @Override
  public String getNodeType() {
    return "VariableDeclarationStatement";
  }

  @Override
  public String toString() {
    return "VariableDeclarationStatement(" + variableName + " = " + initializer.getNodeType() + ")";
  }
}
