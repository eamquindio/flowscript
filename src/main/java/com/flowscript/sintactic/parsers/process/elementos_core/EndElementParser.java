package com.flowscript.sintactic.parsers.process.elementos_core;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.EndElementNode;

public class EndElementParser implements IParser<EndElementNode> {

  @Override
  public EndElementNode parse(ParserContext context) throws ParseException {
    Token endToken = context.getCurrentToken();

    if (endToken.getType() != TokenType.END) {
      throw new ParseException("Se esperaba 'end'");
    }

    context.consume(TokenType.END);

    Token identifierToken = context.consume(TokenType.IDENTIFIER);

    return new EndElementNode(endToken, identifierToken.getValue());
  }
}
