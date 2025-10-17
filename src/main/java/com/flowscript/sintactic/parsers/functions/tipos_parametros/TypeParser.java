package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;

public class TypeParser implements IParser<TypeNode> {

  @Override
  public TypeNode parse(ParserContext context) throws ParseException {
    Token typeToken = context.getCurrentToken();

    if (typeToken == null) {
      throw new Parser.ParseException("Expected type but reached end of input");
    }

    // Verificar si es un tipo válido
    if (!isValidType(typeToken)) {
      throw new Parser.ParseException(
          "Expected type (entero, decimal, booleano, texto, lista, objeto, vacio) " +
              "but found '" + typeToken.getValue() + "' at line " + typeToken.getLine());
    }

    context.consume();
    return new TypeNode(typeToken);
  }

  private boolean isValidType(Token token) {
    TokenType type = token.getType();
    String value = token.getValue();

    if (type == TokenType.INTEGER_TYPE || type == TokenType.DECIMAL_TYPE ||
        type == TokenType.BOOLEAN_TYPE || type == TokenType.TEXT_TYPE ||
        type == TokenType.LIST_TYPE || type == TokenType.OBJECT_TYPE ||
        type == TokenType.VOID) {
      return true;
    }

    return value.equals("entero") || value.equals("integer") ||
        value.equals("decimal") ||
        value.equals("booleano") || value.equals("boolean") ||
        value.equals("texto") || value.equals("text") ||
        value.equals("lista") || value.equals("list") ||
        value.equals("objeto") || value.equals("object") ||
        value.equals("vacio") || value.equals("void");
  }
}
