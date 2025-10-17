package com.flowscript.sintactic.ast.functions.listas_argumentos;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents expression lists: expr1, expr2, expr3
 * Grammar: ExpressionList ::= Expression ( ',' Expression )*
 * Used within list literals and other constructs requiring expression sequences
 */
public class ExpressionListNode extends ASTNode {
  private final List<ExpressionNode> expressions;

  public ExpressionListNode(Token firstToken, List<ExpressionNode> expressions) {
    super(firstToken);
    this.expressions = new ArrayList<>(expressions);
  }

  public ExpressionListNode(ExpressionNode firstExpression) {
    super(firstExpression.getLine(), firstExpression.getColumn(), firstExpression.getPosition());
    this.expressions = new ArrayList<>();
    this.expressions.add(firstExpression);
  }

  public void addExpression(ExpressionNode expression) {
    expressions.add(expression);
  }

  public List<ExpressionNode> getExpressions() {
    return new ArrayList<>(expressions);
  }

  public int size() {
    return expressions.size();
  }

  public boolean isEmpty() {
    return expressions.isEmpty();
  }

  public ExpressionNode getExpression(int index) {
    return expressions.get(index);
  }

  @Override
  public String getNodeType() {
    return "ExpressionList";
  }

  @Override
  public String toString() {
    return "ExpressionList with " + expressions.size() + " expressions";
  }
}
