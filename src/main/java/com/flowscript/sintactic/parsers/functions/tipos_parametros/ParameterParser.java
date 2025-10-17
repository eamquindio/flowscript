package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;

public class ParameterParser implements IParser<ParameterNode> {

  private final TypeParser typeParser;

  public ParameterParser() {
    this.typeParser = new TypeParser();
  }

  @Override
  public ParameterNode parse(ParserContext context) throws ParseException {
    Token nameToken = context.consume(TokenType.IDENTIFIER);
    String paramName = nameToken.getValue();

    Token colon = context.getCurrentToken();
    if (colon == null || !colon.getValue().equals(":")) {
      throw new Parser.ParseException(
          "Expected ':' after parameter name '" + paramName +
              "' at line " + nameToken.getLine());
    }
    context.consume();

    TypeNode paramType = typeParser.parse(context);

    return new ParameterNode(paramName, paramType);
  }
}
