package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser;

// /**
//  * Parser para literales de lista.
//  *
//  * <h3>Gramática BNF:</h3>
//  * 
//  * <pre>
//  * ListLiteral ::= '[' ExpressionList? ']'
//  * </pre>
//  *
//  * <h3>Categoría:</h3>
//  * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
//  * Nivel 19: Literales - Listas
//  *
//  * <h3>Tarea del Estudiante:</h3>
//  * Implementar el método {@code parse()} siguiendo la gramática BNF.
//  * Debe reconocer literales de lista como: [1, 2, 3], ["a", "b"], [1, "texto",
//  * verdadero]
//  * Una lista vacía [] también es válida.
//  *
//  * @see ListLiteralNode
//  * @see com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser
//  */
public class ListLiteralParser implements IParser<ListLiteralNode> {
  private static final ExpressionListParser EXPRESSION_LIST_PARSER = new ExpressionListParser();

  @Override
  public ListLiteralNode parse(ParserContext context) throws ParseException {
    Token leftBracketToken = context.consume(TokenType.LEFT_BRACKET);

    if (!context.check(TokenType.RIGHT_BRACKET)) {
      EXPRESSION_LIST_PARSER.parse(context);
    }

    context.consume(TokenType.RIGHT_BRACKET);
    return new ListLiteralNode(leftBracketToken);
  }
}