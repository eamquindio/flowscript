package com.flowscript.sintactic.ast.functions.statements_basicos;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;

/**
 * Represents an else_if clause with its condition and statement.
 * Syntax: else_if condition { ... }
 * Part of the if statement's conditional chain.
 */
public class ElseIfClauseNode extends ASTNode {
  private final ExpressionNode condition;
  private final StatementNode statement;

  public ElseIfClauseNode(ExpressionNode condition, StatementNode statement) {
    super(condition.getLine(), condition.getColumn(), condition.getPosition());
    this.condition = condition;
    this.statement = statement;
  }

  public ExpressionNode getCondition() {
    return condition;
  }

  public StatementNode getStatement() {
    return statement;
  }

  @Override
  public String getNodeType() {
    return "ElseIfClause";
  }

  @Override
  public String toString() {
    return "ElseIfClause(" + condition.getNodeType() + ")";
  }
}
