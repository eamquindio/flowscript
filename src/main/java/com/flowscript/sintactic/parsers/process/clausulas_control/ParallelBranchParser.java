package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;

public class ParallelBranchParser implements IParser<ParallelBranchNode> {

  @Override
  public ParallelBranchNode parse(ParserContext context) throws ParseException {
    Token branchToken = context.getCurrentToken();

    if (branchToken.getType() != TokenType.BRANCH) {
      throw new ParseException("Se esperaba 'branch' o 'rama'");
    }
    context.consume(TokenType.BRANCH);

    Token arrowToken = context.getCurrentToken();
    if (arrowToken.getType() != TokenType.ARROW) {
      throw new ParseException("Se esperaba '->' después de 'branch'");
    }
    context.consume(TokenType.ARROW);

    Token targetToken = context.getCurrentToken();
    if (targetToken.getType() != TokenType.IDENTIFIER) {
      throw new ParseException("Se esperaba un IDENTIFIER (nodo destino) después de '->' en la cláusula branch.");
    }
    context.consume(TokenType.IDENTIFIER);

    return new ParallelBranchNode(branchToken, targetToken.getValue());
  }
}
