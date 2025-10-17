package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents postfix expressions: primary followed by postfix operators
 * Grammar: PostfixExpression ::= PrimaryExpression PostfixOperator*
 * Left-associative with precedence levels 14-15
 */
public class PostfixExpressionNode extends ExpressionNode {
  private final ExpressionNode primary;
  private final List<PostfixOperatorNode> operators;

  public PostfixExpressionNode(Token firstToken, ExpressionNode primary) {
    super(firstToken);
    this.primary = primary;
    this.operators = new ArrayList<>();
  }

  public void addOperator(PostfixOperatorNode operator) {
    operators.add(operator);
  }

  public ExpressionNode getPrimary() {
    return primary;
  }

  public List<PostfixOperatorNode> getOperators() {
    return new ArrayList<>(operators);
  }

  public boolean hasOperators() {
    return !operators.isEmpty();
  }

  /**
   * If no postfix operators, return the primary expression directly.
   */
  public ExpressionNode getSimplified() {
    if (operators.isEmpty()) {
      return primary;
    }
    return this;
  }

  @Override
  public String getNodeType() {
    return "PostfixExpression";
  }

  @Override
  public String getExpressionType() {
    // Type depends on the postfix operators applied
    return "postfix_result";
  }

  @Override
  public String toString() {
    return "PostfixExpression with " + operators.size() + " operators on " + primary.getNodeType();
  }
}
