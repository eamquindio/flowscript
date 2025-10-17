package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Base class for all expression nodes in the FlowScript AST.
 * Expressions are units that evaluate to values and can be used in
 * computations.
 */
public abstract class ExpressionNode extends ASTNode {
  private final Token token;

  public ExpressionNode(int line, int column, int position) {
    super(line, column, position);
    this.token = null;
  }

  public ExpressionNode(Token token) {
    super(token);
    this.token = token;
  }

  @Override
  public String getNodeType() {
    return "Expression";
  }

  /**
   * Returns the token associated with this expression.
   */
  public Token getToken() {
    return token;
  }

  /**
   * Returns the data type that this expression evaluates to.
   * This is used for type checking and code generation.
   */
  public abstract String getExpressionType();
}
