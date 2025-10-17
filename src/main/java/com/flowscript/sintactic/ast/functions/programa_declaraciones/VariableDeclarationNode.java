package com.flowscript.sintactic.ast.functions.programa_declaraciones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Represents a top-level variable declaration (global constant).
 * Example: API_KEY = "secret-123"
 *
 * Note: This is different from VariableDeclarationStatementNode which is used
 * inside functions and process tasks.
 */
public class VariableDeclarationNode extends DeclarationNode {
  private final String variableName;
  private final ExpressionNode initializer;

  public VariableDeclarationNode(Token identifierToken, String variableName, ExpressionNode initializer) {
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
  public String getDeclaredName() {
    return variableName;
  }

  @Override
  public String getNodeType() {
    return "VariableDeclaration";
  }

  @Override
  public String toString() {
    return "VariableDeclaration(" + variableName + " = " + initializer.getNodeType() + ")";
  }
}
