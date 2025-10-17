package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;

public class BlockParser implements IParser<BlockNode> {
  private static final StatementListParser STMT_LIST_PARSER = new StatementListParser();

  @Override
  public BlockNode parse(ParserContext context) throws ParseException {
    Token leftBraceToken = context.consume(TokenType.LEFT_BRACE);
    BlockNode blockNode = new BlockNode(leftBraceToken);

    STMT_LIST_PARSER.parse(context).forEach(blockNode::addStatement);

    context.consume(TokenType.RIGHT_BRACE);
    return blockNode;
  }
}
