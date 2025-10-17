package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para bucles for-each.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * ForStatement ::= 'for' 'each' IDENTIFIER 'in' Expression Statement
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * // For-each básico
 * for each numero in [1, 2, 3, 4, 5] {
 *     imprimir(numero)
 * }
 *
 * // For-each con objetos
 * usuarios = [
 *     { nombre: "Juan", edad: 25 },
 *     { nombre: "Ana", edad: 30 }
 * ]
 * for each usuario in usuarios {
 *     imprimir(usuario.nombre + " tiene " + usuario.edad + " años")
 * }
 *
 * // For-each anidado
 * matriz = [[1, 2], [3, 4]]
 * for each fila in matriz {
 *     for each numero in fila {
 *         imprimir(numero)
 *     }
 * }
 * </pre>
 *
 * @see ForStatementNode
 */
public class ForStatementParser implements IParser<ForStatementNode> {
  private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();
  private static final StatementParser STATEMENT_PARSER = new StatementParser();

  @Override
  public ForStatementNode parse(ParserContext context) throws ParseException {
    Token forToken = context.consume(TokenType.FOR);
    context.consume(TokenType.EACH);
    String iteratorVariable = context.consume(TokenType.IDENTIFIER).getValue();
    context.consume(TokenType.IN);

    ExpressionNode iterableExpression = EXPRESSION_PARSER.parse(context);
    StatementNode body = STATEMENT_PARSER.parse(context);

    return new ForStatementNode(forToken, iteratorVariable, iterableExpression, body);
  }
}