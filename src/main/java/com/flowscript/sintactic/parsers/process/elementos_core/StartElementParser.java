package com.flowscript.sintactic.parsers.process.elementos_core;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.StartElementNode;

public class StartElementParser implements IParser<StartElementNode> {

  @Override
  public StartElementNode parse(ParserContext context) throws ParseException {
    Token startToken = context.getCurrentToken();

    if (startToken.getType() != TokenType.START) {
      throw new ParseException("Se esperaba 'start'");
    }

    context.consume(TokenType.START);
    context.consume(TokenType.ARROW);

    Token targetToken = context.consume(TokenType.IDENTIFIER);

    return new StartElementNode(startToken, targetToken.getValue());
  }
}
