package com.flowscript.sintactic.parsers.functions.literales;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberListParser;

public class ObjectLiteralParser implements IParser<ObjectLiteralNode> {
  private static final ObjectMemberListParser MEMBER_LIST_PARSER = new ObjectMemberListParser();

  @Override
  public ObjectLiteralNode parse(ParserContext context) throws ParseException {
    Token leftBraceToken = context.consume(TokenType.LEFT_BRACE);

    if (context.check(TokenType.RIGHT_BRACE)) {
      context.consume(TokenType.RIGHT_BRACE);
      return new ObjectLiteralNode(leftBraceToken);
    }

    ObjectMemberListNode memberList = mergeMembers(leftBraceToken, MEMBER_LIST_PARSER.parse(context));
    context.consume(TokenType.RIGHT_BRACE);

    return new ObjectLiteralNode(leftBraceToken, memberList);
  }

  private static ObjectMemberListNode mergeMembers(Token firstToken, List<ObjectMemberListNode> nodes) {
    List<ObjectMemberNode> memberList = new ArrayList<>();
    for (ObjectMemberListNode node : nodes) {
      memberList.addAll(node.getMembers());
    }
    return new ObjectMemberListNode(firstToken, memberList);
  }
}