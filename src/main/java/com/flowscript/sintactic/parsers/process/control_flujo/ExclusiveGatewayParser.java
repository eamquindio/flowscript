package com.flowscript.sintactic.parsers.process.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.ExclusiveGatewayNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.ast.process.clausulas_control.ElseClauseNode;
import com.flowscript.sintactic.parsers.process.clausulas_control.WhenClauseParser;
import com.flowscript.sintactic.parsers.process.clausulas_control.ElseClauseParser;

import java.util.ArrayList;
import java.util.List;

public class ExclusiveGatewayParser implements IParser<ExclusiveGatewayNode> {

  private final WhenClauseParser whenParser;
  private final ElseClauseParser elseParser;

  public ExclusiveGatewayParser() {
    this.whenParser = new WhenClauseParser();
    this.elseParser = new ElseClauseParser();
  }

  @Override
  public ExclusiveGatewayNode parse(ParserContext context) throws Parser.ParseException {
    Token gatewayToken = context.getCurrentToken();

    if (gatewayToken.getType() != TokenType.GATEWAY) {
      throw new Parser.ParseException("Se esperaba la palabra clave 'gateway' para iniciar un gateway exclusivo.");
    }
    context.consume(TokenType.GATEWAY);

    Token identifierToken = context.getCurrentToken();
    if (identifierToken.getType() != TokenType.IDENTIFIER) {
      throw new Parser.ParseException("Se esperaba un IDENTIFIER (nombre del gateway) después de 'gateway'.");
    }
    context.consume(TokenType.IDENTIFIER);

    Token openBraceToken = context.getCurrentToken();
    if (openBraceToken.getType() != TokenType.LEFT_BRACE) {
      throw new Parser.ParseException("Se esperaba '{' después del nombre del gateway.");
    }
    context.consume(TokenType.LEFT_BRACE);

    List<WhenClauseNode> whenClauses = new ArrayList<>();

    while (context.check(TokenType.WHEN)) {
      WhenClauseNode whenClause = whenParser.parse(context);
      whenClauses.add(whenClause);
    }

    ElseClauseNode elseClause = null;

    if (context.check(TokenType.ELSE)) {
      elseClause = elseParser.parse(context);
    }

    Token closeBraceToken = context.getCurrentToken();
    if (closeBraceToken.getType() != TokenType.RIGHT_BRACE) {
      throw new Parser.ParseException("Se esperaba '}' al final del gateway exclusivo.");
    }
    context.consume(TokenType.RIGHT_BRACE);

    return new ExclusiveGatewayNode(gatewayToken, identifierToken.getValue(), whenClauses, elseClause);
  }
}
