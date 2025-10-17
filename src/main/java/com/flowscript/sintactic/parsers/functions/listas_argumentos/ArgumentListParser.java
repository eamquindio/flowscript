package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para listas de argumentos en llamadas a funciones.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * ArgumentList ::= Expression ( ',' Expression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 20: Listas - Argumentos de Funciones
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer listas de argumentos como: (x, y, z), (42), ("hola", 3.14,
 * verdadero)
 * Nota: Este parser NO implementa IParser porque retorna una List.
 *
 * @see ArgumentListNode
 */
public class ArgumentListParser {
  private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();

  /**
   * Parsea una lista de argumentos.
   *
   * @param context El contexto del parser
   * @return Lista de nodos ArgumentListNode
   * @throws ParseException Si hay un error de sintaxis
   */
  public List<ArgumentListNode> parse(ParserContext context) throws ParseException {
    List<ArgumentListNode> arguments = new ArrayList<>();
    arguments.add(new ArgumentListNode(EXPRESSION_PARSER.parse(context)));

    while (context.check(TokenType.COMMA)) {
      context.consume(TokenType.COMMA);
      arguments.add(new ArgumentListNode(EXPRESSION_PARSER.parse(context)));
    }

    return arguments;
  }
}