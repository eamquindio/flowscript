package com.flowscript.sintactic.ast.functions.programa_declaraciones;

import java.util.List;
import java.util.ArrayList;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Root node of the FlowScript AST representing the entire program.
 * Contains all top-level declarations (imports, functions, processes,
 * variables).
 */
public class ProgramNode extends ASTNode {
  private final List<DeclarationNode> declarations;

  public ProgramNode() {
    super(1, 1, 0); // Program starts at line 1, column 1
    this.declarations = new ArrayList<>();
  }

  public ProgramNode(List<DeclarationNode> declarations) {
    super(1, 1, 0);
    this.declarations = new ArrayList<>(declarations);
  }

  public void addDeclaration(DeclarationNode declaration) {
    declarations.add(declaration);
  }

  public List<DeclarationNode> getDeclarations() {
    return new ArrayList<>(declarations);
  }

  @Override
  public String getNodeType() {
    return "Program";
  }

  @Override
  public String toString() {
    return "Program with " + declarations.size() + " declarations";
  }
}
