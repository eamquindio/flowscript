package com.flowscript.sintactic.ast.functions.programa_declaraciones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Base class for all declaration nodes in FlowScript.
 * Declarations define new entities like imports, functions, processes, or
 * variables.
 */
public abstract class DeclarationNode extends ASTNode {

  public DeclarationNode(int line, int column, int position) {
    super(line, column, position);
  }

  public DeclarationNode(Token token) {
    super(token);
  }

  @Override
  public String getNodeType() {
    return "Declaration";
  }

  /**
   * Returns the name of the declared entity (if applicable).
   */
  public abstract String getDeclaredName();
}
