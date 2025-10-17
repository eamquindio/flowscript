package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;

public class ParameterListParser {

  private final ParameterParser parameterParser;

  public ParameterListParser() {
    this.parameterParser = new ParameterParser();
  }

  public List<ParameterNode> parse(ParserContext context) throws ParseException {
    List<ParameterNode> parameters = new ArrayList<>();

    ParameterNode firstParam = parameterParser.parse(context);
    parameters.add(firstParam);

    while (context.checkValue(",")) {
      context.consume();
      ParameterNode param = parameterParser.parse(context);
      parameters.add(param);
    }

    if (isParameterStart(context.getCurrentToken())) {
      throw new ParseException("Se esperaba un parámetro o el cierre de paréntesis.");
    }

    return parameters;
  }

  private boolean isParameterStart(Token token) {
    return token.getType() == TokenType.IDENTIFIER;
  }
}
