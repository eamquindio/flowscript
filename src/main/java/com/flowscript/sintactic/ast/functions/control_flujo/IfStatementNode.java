package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.statements_basicos.ElseIfClauseNode;
import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents if/else_if/else conditional statements.
 * Example: if condition { ... } else_if other_condition { ... } else { ... }
 */
public class IfStatementNode extends StatementNode {
  private final ExpressionNode condition;
  private final StatementNode thenStatement;
  private final List<ElseIfClauseNode> elseIfClauses;
  private StatementNode elseStatement;

  public IfStatementNode(Token ifToken, ExpressionNode condition, StatementNode thenStatement) {
    super(ifToken);
    this.condition = condition;
    this.thenStatement = thenStatement;
    this.elseIfClauses = new ArrayList<>();
  }

  public void addElseIfClause(ExpressionNode condition, StatementNode statement) {
    elseIfClauses.add(new ElseIfClauseNode(condition, statement));
  }

  public void setElseStatement(StatementNode elseStatement) {
    this.elseStatement = elseStatement;
  }

  public ExpressionNode getCondition() {
    return condition;
  }

  public StatementNode getThenStatement() {
    return thenStatement;
  }

  public List<ElseIfClauseNode> getElseIfClauses() {
    return new ArrayList<>(elseIfClauses);
  }

  public StatementNode getElseStatement() {
    return elseStatement;
  }

  @Override
  public String getNodeType() {
    return "IfStatement";
  }

  @Override
  public String toString() {
    return "IfStatement with " + elseIfClauses.size() + " else_if clauses" +
        (elseStatement != null ? " and else clause" : "");
  }
}
