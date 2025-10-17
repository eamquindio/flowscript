package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.BooleanLiteralNode;

public class BooleanLiteralParser implements IParser<BooleanLiteralNode> {

  @Override
  public BooleanLiteralNode parse(ParserContext context) throws ParseException {
    Token literalToken = context.getCurrentToken();
    TokenType type = literalToken.getType();

    if (type == TokenType.TRUE || type == TokenType.FALSE) {
      context.advance();
      return new BooleanLiteralNode(literalToken);
    }

    throw new ParseException("Se esperaba un literal booleano (true/false)");
  }
}