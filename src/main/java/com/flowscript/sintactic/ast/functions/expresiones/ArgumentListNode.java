package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents argument lists in function calls: expr1, expr2, expr3
 * Grammar: ArgumentList ::= Expression ( ',' Expression )*
 * Used within function calls and similar constructs
 */
public class ArgumentListNode extends ASTNode {
  private final List<ExpressionNode> arguments;

  public ArgumentListNode(Token firstToken, List<ExpressionNode> arguments) {
    super(firstToken);
    this.arguments = new ArrayList<>(arguments);
  }

  public ArgumentListNode(ExpressionNode firstArgument) {
    super(firstArgument.getLine(), firstArgument.getColumn(), firstArgument.getPosition());
    this.arguments = new ArrayList<>();
    this.arguments.add(firstArgument);
  }

  public void addArgument(ExpressionNode argument) {
    arguments.add(argument);
  }

  public List<ExpressionNode> getArguments() {
    return new ArrayList<>(arguments);
  }

  public int size() {
    return arguments.size();
  }

  public boolean isEmpty() {
    return arguments.isEmpty();
  }

  public ExpressionNode getArgument(int index) {
    return arguments.get(index);
  }

  @Override
  public String getNodeType() {
    return "ArgumentList";
  }

  @Override
  public String toString() {
    return "ArgumentList with " + arguments.size() + " arguments";
  }
}
