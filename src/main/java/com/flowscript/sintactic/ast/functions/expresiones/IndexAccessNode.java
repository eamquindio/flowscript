package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;

/**
 * Represents array/object index access expressions.
 * Examples: items[0], customer["name"], order[item_id]
 */
public class IndexAccessNode extends ExpressionNode {
  private final ExpressionNode object;
  private final ExpressionNode index;

  public IndexAccessNode(ExpressionNode object, Token leftBracketToken, ExpressionNode index) {
    super(leftBracketToken);
    this.object = object;
    this.index = index;
  }

  public ExpressionNode getObject() {
    return object;
  }

  public ExpressionNode getIndex() {
    return index;
  }

  @Override
  public String getNodeType() {
    return "IndexAccess";
  }

  @Override
  public String getExpressionType() {
    // Type would be determined by symbol table lookup during semantic analysis
    // Typically the element type of the array/list
    return "element";
  }

  @Override
  public String toString() {
    return "IndexAccess(" + object.getNodeType() + "[" + index.getNodeType() + "])";
  }
}
