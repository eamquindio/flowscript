package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.EqualityExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalAndExpressionNode;

/**
 * Parser para expresiones lógicas AND.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * LogicalAndExpression ::= EqualityExpression ( ( 'and' | 'y' ) EqualityExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 3: Expresiones - AND Lógico
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta tanto 'and' (inglés) como 'y' (español).
 * El operador es asociativo por la izquierda.
 *
 * @see LogicalAndExpressionNode
 */
public class LogicalAndExpressionParser implements IParser<LogicalAndExpressionNode> {

  private static final EqualityExpressionParser EQUALITY_PARSER = new EqualityExpressionParser();

  @Override
  public LogicalAndExpressionNode parse(ParserContext context) throws ParseException {
    EqualityExpressionNode firstOperand = EQUALITY_PARSER.parse(context);
    LogicalAndExpressionNode node = new LogicalAndExpressionNode(firstOperand.getToken(), firstOperand);

    while (context.check(TokenType.AND)) {
      Token operator = context.consume();
      node.addOperand(operator, EQUALITY_PARSER.parse(context));
    }
    return node;
  }
}