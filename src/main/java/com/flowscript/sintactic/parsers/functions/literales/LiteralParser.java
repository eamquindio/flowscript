package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;

public class LiteralParser implements IParser<LiteralNode> {
  private static final ObjectLiteralParser OBJECT_PARSER = new ObjectLiteralParser();
  private static final ListLiteralParser LIST_PARSER = new ListLiteralParser();

  @Override
  public LiteralNode parse(ParserContext context) throws ParseException {
    Token literalToken = context.getCurrentToken();
    TokenType type = literalToken.getType();

    switch (type) {
      case INTEGER_LITERAL:
      case DECIMAL_LITERAL:
      case STRING_LITERAL:
      case TRUE:
      case FALSE:
      case NULL:
        context.advance();
        return new LiteralNode(literalToken);

      case LEFT_BRACE:
        return OBJECT_PARSER.parse(context);

      case LEFT_BRACKET:
        return LIST_PARSER.parse(context);

      default:
        throw new ParseException("Se esperaba un literal");
    }
  }
}