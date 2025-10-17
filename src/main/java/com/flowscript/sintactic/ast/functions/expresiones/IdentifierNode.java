package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;

/**
 * Represents an identifier (variable name, function name, etc.).
 * Example: variable_name, function_name
 */
public class IdentifierNode extends PrimaryExpressionNode {
  private final String name;

  public IdentifierNode(Token identifierToken) {
    super(identifierToken);
    this.name = identifierToken.getValue();
  }

  public String getName() {
    return name;
  }

  @Override
  public String getNodeType() {
    return "Identifier";
  }

  @Override
  public String getExpressionType() {
    // Type would be determined by symbol table lookup during semantic analysis
    return "identifier";
  }

  @Override
  public String toString() {
    return "Identifier(" + name + ")";
  }
}
