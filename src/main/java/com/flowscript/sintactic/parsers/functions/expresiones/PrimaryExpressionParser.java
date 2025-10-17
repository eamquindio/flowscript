package com.flowscript.sintactic.parsers.functions.expresiones;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.IdentifierNode;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
import com.flowscript.sintactic.ast.functions.expresiones.PrimaryExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberParser;

/**
 * Parser para expresiones primarias.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * PrimaryExpression ::= IDENTIFIER
 *                     | Literal
 *                     | '(' Expression ')'
 *                     | '[' ExpressionList? ']'
 *                     | '{' ObjectMemberList? '}'
 * </pre>
 */
public class PrimaryExpressionParser implements IParser<PrimaryExpressionNode> {
  private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();
  private static final ObjectMemberParser OBJECT_MEMBER_PARSER = new ObjectMemberParser();

  @Override
  public PrimaryExpressionNode parse(ParserContext context) throws ParseException {
    Token token = context.getCurrentToken();

    switch (token.getType()) {
      case INTEGER_LITERAL:
      case DECIMAL_LITERAL:
      case STRING_LITERAL:
      case TRUE:
      case FALSE:
      case NULL:
        return new LiteralNode(context.consume());

      case IDENTIFIER:
        return new IdentifierNode(context.consume());

      case LEFT_PAREN:
        return parseParenthesizedExpression(context);

      case LEFT_BRACKET:
        return parseListLiteral(context);

      case LEFT_BRACE:
        return parseObjectLiteral(context);

      default:
        throw new ParseException("Se esperaba una expresión primaria");
    }
  }

  private PrimaryExpressionNode parseParenthesizedExpression(ParserContext context) throws ParseException {
    Token leftParenToken = context.consume(TokenType.LEFT_PAREN);
    ExpressionNode innerExpression = EXPRESSION_PARSER.parse(context);
    context.consume(TokenType.RIGHT_PAREN);

    return new PrimaryExpressionNode.ParenthesizedExpressionNode(leftParenToken, innerExpression);
  }

  private PrimaryExpressionNode parseListLiteral(ParserContext context) throws ParseException {
    Token leftBracketToken = context.consume(TokenType.LEFT_BRACKET);
    List<ExpressionNode> expressions = new ArrayList<>();

    if (!context.check(TokenType.RIGHT_BRACKET)) {
      do {
        expressions.add(EXPRESSION_PARSER.parse(context));
      } while (context.match(TokenType.COMMA));
    }

    context.consume(TokenType.RIGHT_BRACKET);
    ExpressionListNode expressionList = new ExpressionListNode(leftBracketToken, expressions);

    return new ListLiteralNode(leftBracketToken, expressionList);
  }

  private PrimaryExpressionNode parseObjectLiteral(ParserContext context) throws ParseException {
    Token leftBraceToken = context.consume(TokenType.LEFT_BRACE);
    List<ObjectMemberNode> members = new ArrayList<>();

    if (!context.check(TokenType.RIGHT_BRACE)) {
      do {
        members.add(OBJECT_MEMBER_PARSER.parse(context));
      } while (context.match(TokenType.COMMA));
    }

    context.consume(TokenType.RIGHT_BRACE);
    ObjectMemberListNode memberList = new ObjectMemberListNode(leftBraceToken, members);

    return new ObjectLiteralNode(leftBraceToken, memberList);
  }
}