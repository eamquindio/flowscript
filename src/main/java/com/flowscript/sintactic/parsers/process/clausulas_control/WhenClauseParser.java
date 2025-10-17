package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

public class WhenClauseParser implements IParser<WhenClauseNode> {

  private final ExpressionParser expressionParser;

  public WhenClauseParser() {
    this.expressionParser = new ExpressionParser();
  }

  @Override
  public WhenClauseNode parse(ParserContext context) throws ParseException {
    Token whenToken = context.getCurrentToken();

    if (whenToken.getType() != TokenType.WHEN) {
      throw new ParseException("Se esperaba 'when' o 'cuando' al inicio de la cláusula when.");
    }
    context.consume(TokenType.WHEN);

    ExpressionNode condition = expressionParser.parse(context);

    Token arrowToken = context.getCurrentToken();
    if (arrowToken.getType() != TokenType.ARROW) {
      throw new ParseException("Se esperaba '->' después de la condición en la cláusula when.");
    }
    context.consume(TokenType.ARROW);

    Token targetToken = context.getCurrentToken();
    if (targetToken.getType() != TokenType.IDENTIFIER) {
      throw new ParseException("Se esperaba un IDENTIFIER (nodo destino) después de '->' en la cláusula when.");
    }
    context.consume(TokenType.IDENTIFIER);

    return new WhenClauseNode(condition, targetToken.getValue());
  }
}
