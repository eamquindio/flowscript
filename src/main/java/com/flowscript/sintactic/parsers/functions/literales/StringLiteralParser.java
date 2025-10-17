package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.StringLiteralNode;

public class StringLiteralParser implements IParser<StringLiteralNode> {

  @Override
  public StringLiteralNode parse(ParserContext context) throws ParseException {
    Token literalToken = context.consume(TokenType.STRING_LITERAL);
    return new StringLiteralNode(literalToken);
  }
}