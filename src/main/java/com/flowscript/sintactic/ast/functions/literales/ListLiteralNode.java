package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PrimaryExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.lexer.Token;
import java.util.List;

/**
 * Represents list literal expressions.
 * Grammar: ListLiteral ::= '[' ExpressionList? ']'
 * Examples: [], [1, 2, 3], ["a", "b"]
 */
public class ListLiteralNode extends PrimaryExpressionNode {
  private final ExpressionListNode expressionList;

  public ListLiteralNode(Token leftBracketToken) {
    super(leftBracketToken);
    this.expressionList = null;
  }

  public ListLiteralNode(Token leftBracketToken, ExpressionListNode expressionList) {
    super(leftBracketToken);
    this.expressionList = expressionList;
  }

  public ExpressionListNode getExpressionList() {
    return expressionList;
  }

  public boolean isEmpty() {
    return expressionList == null || expressionList.isEmpty();
  }

  public int size() {
    return expressionList == null ? 0 : expressionList.size();
  }

  public List<ExpressionNode> getElements() {
    return expressionList == null ? List.of() : expressionList.getExpressions();
  }

  public ExpressionNode getElement(int index) {
    if (expressionList == null) {
      throw new IndexOutOfBoundsException("Empty list");
    }
    return expressionList.getExpression(index);
  }

  @Override
  public String getNodeType() {
    return "ListLiteral";
  }

  @Override
  public String getExpressionType() {
    return "lista";
  }

  @Override
  public String toString() {
    int count = expressionList == null ? 0 : expressionList.size();
    return "ListLiteral with " + count + " elements";
  }
}
