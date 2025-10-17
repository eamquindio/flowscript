package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.TernaryExpressionNode;

/**
 * Parser para expresiones ternarias (operador condicional).
 *
 * TernaryExpression ::= LogicalOrExpression ( '?' Expression ':' Expression )?
 */
public class TernaryExpressionParser implements IParser<ExpressionNode> {
  private static final LogicalOrExpressionParser LOGICAL_OR_PARSER = new LogicalOrExpressionParser();

  @Override
  public ExpressionNode parse(ParserContext context) throws ParseException {
    ExpressionNode condition = LOGICAL_OR_PARSER.parse(context);

    if (!context.check(TokenType.QUESTION)) {
      return condition;
    }

    Token questionToken = context.consume(TokenType.QUESTION);
    ExpressionNode trueExpression = parse(context);
    context.consume(TokenType.COLON);
    ExpressionNode falseExpression = parse(context);

    return new TernaryExpressionNode(condition, questionToken, trueExpression, falseExpression);
  }
}