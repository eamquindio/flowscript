package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para listas de expresiones.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * ExpressionList ::= Expression ( ',' Expression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 21: Listas - Expresiones
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer listas de expresiones como las usadas en literales de lista:
 * [1, 2, 3]
 * Nota: Este parser NO implementa IParser porque retorna una List.
 *
 * @see ExpressionListNode
 */
public class ExpressionListParser implements IParser<ExpressionListNode> {
  private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();

  @Override
  public ExpressionListNode parse(ParserContext context) throws ParseException {
    List<ExpressionNode> expressions = new ArrayList<>();
    expressions.add(EXPRESSION_PARSER.parse(context));

    while (context.check(TokenType.COMMA)) {
      context.consume(TokenType.COMMA);
      expressions.add(EXPRESSION_PARSER.parse(context));
    }

    return new ExpressionListNode(context.getCurrentToken(), expressions);
  }
}