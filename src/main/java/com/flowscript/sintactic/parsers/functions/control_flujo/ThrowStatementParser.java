package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ThrowStatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

public class ThrowStatementParser implements IParser<ThrowStatementNode> {

  private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();

  @Override
  public ThrowStatementNode parse(ParserContext context) throws ParseException {
    Token throwToken = context.consume(TokenType.THROW);
    return new ThrowStatementNode(throwToken, EXPRESSION_PARSER.parse(context));
  }
}
