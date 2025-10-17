package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;

/**
 * Represents primary expressions: the basic building blocks
 * Grammar: PrimaryExpression ::= IDENTIFIER | Literal | ObjectLiteral |
 * ListLiteral | '(' Expression ')'
 * Highest precedence, cannot be broken down further
 */
public abstract class PrimaryExpressionNode extends ExpressionNode {

  protected PrimaryExpressionNode(Token token) {
    super(token);
  }

  @Override
  public String getNodeType() {
    return "PrimaryExpression";
  }

  /**
   * Parenthesized expression: (expression)
   */
  public static class ParenthesizedExpressionNode extends PrimaryExpressionNode {
    private final ExpressionNode innerExpression;

    public ParenthesizedExpressionNode(Token leftParenToken, ExpressionNode innerExpression) {
      super(leftParenToken);
      this.innerExpression = innerExpression;
    }

    public ExpressionNode getInnerExpression() {
      return innerExpression;
    }

    @Override
    public String getNodeType() {
      return "ParenthesizedExpression";
    }

    @Override
    public String getExpressionType() {
      return innerExpression.getExpressionType();
    }

    @Override
    public String toString() {
      return "(" + innerExpression.getNodeType() + ")";
    }
  }
}
