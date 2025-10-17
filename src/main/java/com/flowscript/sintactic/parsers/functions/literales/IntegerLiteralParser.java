package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.IntegerLiteralNode;

public class IntegerLiteralParser implements IParser<IntegerLiteralNode> {

  @Override
  public IntegerLiteralNode parse(ParserContext context) throws ParseException {
    Token literalToken = context.consume(TokenType.INTEGER_LITERAL);
    return new IntegerLiteralNode(literalToken);
  }
}