package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ElseClauseNode;

public class ElseClauseParser implements IParser<ElseClauseNode> {

  @Override
  public ElseClauseNode parse(ParserContext context) throws ParseException {
    Token elseToken = context.getCurrentToken();

    if (elseToken.getType() != TokenType.ELSE) {
      throw new ParseException("Se esperaba 'else' o 'sino'");
    }
    context.consume(TokenType.ELSE);

    Token arrowToken = context.getCurrentToken();
    if (arrowToken.getType() != TokenType.ARROW) {
      throw new ParseException("Se esperaba '->' después de 'else'");
    }
    context.consume(TokenType.ARROW);

    Token targetToken = context.getCurrentToken();
    if (targetToken.getType() != TokenType.IDENTIFIER) {
      throw new ParseException("Se esperaba un IDENTIFIER (nodo destino) después de '->' en la cláusula else.");
    }
    context.consume(TokenType.IDENTIFIER);

    return new ElseClauseNode(elseToken, targetToken.getValue());
  }
}
