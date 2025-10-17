package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.IfStatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para statements if/else_if/else.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * IfStatement ::= 'if' Expression Statement ( 'else_if' Expression Statement )* ( 'else' Statement )?
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * // If simple
 * if x > 10 {
 *     imprimir("Mayor que 10")
 * }
 *
 * // If-else
 * if x > 10 {
 *     imprimir("Mayor")
 * } else {
 *     imprimir("Menor o igual")
 * }
 *
 * // If-else_if-else
 * if x > 10 {
 *     imprimir("Mayor que 10")
 * } else_if x > 5 {
 *     imprimir("Entre 6 y 10")
 * } else {
 *     imprimir("5 o menor")
 * }
 * </pre>
 *
 * @see IfStatementNode
 */
public class IfStatementParser implements IParser<IfStatementNode> {
  private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();
  private static final StatementParser STATEMENT_PARSER = new StatementParser();

  @Override
  public IfStatementNode parse(ParserContext context) throws ParseException {
    Token ifToken = context.consume(TokenType.IF);
    ExpressionNode condition = EXPRESSION_PARSER.parse(context);
    StatementNode thenStatement = STATEMENT_PARSER.parse(context);

    IfStatementNode ifNode = new IfStatementNode(ifToken, condition, thenStatement);

    while (context.check(TokenType.ELSE_IF)) {
      context.consume(TokenType.ELSE_IF);
      ifNode.addElseIfClause(
          EXPRESSION_PARSER.parse(context),
          STATEMENT_PARSER.parse(context));
    }

    if (context.check(TokenType.ELSE)) {
      context.consume(TokenType.ELSE);
      ifNode.setElseStatement(STATEMENT_PARSER.parse(context));
    }

    return ifNode;
  }
}