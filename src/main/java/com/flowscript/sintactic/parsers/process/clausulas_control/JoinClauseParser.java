package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.JoinClauseNode;

public class JoinClauseParser implements IParser<JoinClauseNode> {

  @Override
  public JoinClauseNode parse(ParserContext context) throws ParseException {
    Token joinToken = context.getCurrentToken();

    if (joinToken.getType() != TokenType.JOIN) {
      throw new ParseException("Se esperaba 'join' o 'unir'");
    }
    context.consume(TokenType.JOIN);

    Token arrowToken = context.getCurrentToken();
    if (arrowToken.getType() != TokenType.ARROW) {
      throw new ParseException("Se esperaba '->' después de 'join'");
    }
    context.consume(TokenType.ARROW);

    Token targetToken = context.getCurrentToken();
    if (targetToken.getType() != TokenType.IDENTIFIER) {
      throw new ParseException("Se esperaba un IDENTIFIER (nodo destino) después de '->' en la cláusula join.");
    }
    context.consume(TokenType.IDENTIFIER);

    return new JoinClauseNode(joinToken, targetToken.getValue());
  }
}
