package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents function call expressions.
 * Examples: calculate_tax(amount, rate), Http.get(url), validate_email(email)
 */
public class FunctionCallNode extends ExpressionNode {
  private final ExpressionNode function;
  private final List<ExpressionNode> arguments;

  public FunctionCallNode(ExpressionNode function, Token leftParenToken, List<ExpressionNode> arguments) {
    super(leftParenToken);
    this.function = function;
    this.arguments = new ArrayList<>(arguments);
  }

  public ExpressionNode getFunction() {
    return function;
  }

  public List<ExpressionNode> getArguments() {
    return new ArrayList<>(arguments);
  }

  public int getArgumentCount() {
    return arguments.size();
  }

  @Override
  public String getNodeType() {
    return "FunctionCall";
  }

  @Override
  public String getExpressionType() {
    // Type would be determined by function signature lookup during semantic
    // analysis
    return "function_result";
  }

  @Override
  public String toString() {
    return "FunctionCall(" + function.getNodeType() + " with " + arguments.size() + " args)";
  }
}
