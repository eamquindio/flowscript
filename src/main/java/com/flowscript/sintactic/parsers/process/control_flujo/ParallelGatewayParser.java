package com.flowscript.sintactic.parsers.process.control_flujo;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.JoinClauseNode;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ParallelGatewayNode;
import com.flowscript.sintactic.parsers.process.clausulas_control.JoinClauseParser;
import com.flowscript.sintactic.parsers.process.clausulas_control.ParallelBranchParser;

public class ParallelGatewayParser implements IParser<ParallelGatewayNode> {

  private final ParallelBranchParser branchParser;
  private final JoinClauseParser joinParser;

  public ParallelGatewayParser() {
    this.branchParser = new ParallelBranchParser();
    this.joinParser = new JoinClauseParser();
  }

  @Override
  public ParallelGatewayNode parse(ParserContext context) throws Parser.ParseException {
    Token gatewayToken = context.getCurrentToken();

    if (gatewayToken.getType() != TokenType.GATEWAY) {
      throw new Parser.ParseException("Se esperaba la palabra clave 'gateway' para iniciar un gateway paralelo.");
    }
    context.consume(TokenType.GATEWAY);

    Token identifierToken = context.getCurrentToken();
    if (identifierToken.getType() != TokenType.IDENTIFIER) {
      throw new Parser.ParseException("Se esperaba un IDENTIFIER (nombre del gateway) después de 'gateway'.");
    }
    context.consume(TokenType.IDENTIFIER);

    Token parallelToken = context.getCurrentToken();
    if (parallelToken.getType() != TokenType.PARALLEL) {
      throw new Parser.ParseException("Se esperaba la palabra clave 'parallel' después del nombre del gateway.");
    }
    context.consume(TokenType.PARALLEL);

    Token openBraceToken = context.getCurrentToken();
    if (openBraceToken.getType() != TokenType.LEFT_BRACE) {
      throw new Parser.ParseException("Se esperaba '{' después de 'parallel'.");
    }
    context.consume(TokenType.LEFT_BRACE);

    List<ParallelBranchNode> branches = new ArrayList<>();

    while (context.check(TokenType.BRANCH)) {
      ParallelBranchNode branch = branchParser.parse(context);
      branches.add(branch);
    }

    if (!context.check(TokenType.JOIN)) {
      throw new Parser.ParseException("Se esperaba al menos una cláusula 'join' en el gateway paralelo.");
    }

    JoinClauseNode joinClause = joinParser.parse(context);

    Token rightBraceToken = context.getCurrentToken();
    if (rightBraceToken.getType() != TokenType.RIGHT_BRACE) {
      throw new Parser.ParseException("Se esperaba '}' al final del gateway paralelo.");
    }
    context.consume(TokenType.RIGHT_BRACE);

    return new ParallelGatewayNode(gatewayToken, identifierToken.getValue(), branches, joinClause);
  }
}
