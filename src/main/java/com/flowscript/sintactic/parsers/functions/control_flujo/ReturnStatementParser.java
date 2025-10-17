package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ReturnStatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

public class ReturnStatementParser implements IParser<ReturnStatementNode> {

  private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();

  @Override
  public ReturnStatementNode parse(ParserContext context) throws ParseException {
    Token returnToken = context.consume(TokenType.RETURN);

    ExpressionNode expression = isEndOfStatement(context)
        ? EXPRESSION_PARSER.parse(context)
        : null;

    return new ReturnStatementNode(returnToken, expression);
  }

  private boolean isEndOfStatement(ParserContext context) {
    return !context.match(TokenType.RIGHT_BRACE)
        && !context.match(TokenType.EOF)
        && !context.match(TokenType.SEMICOLON);
  }
}
