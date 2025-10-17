package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalAndExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode;

/**
 * Parser para expresiones lógicas OR.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * LogicalOrExpression ::= LogicalAndExpression ( ( 'or' | 'o' ) LogicalAndExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 2: Expresiones - OR Lógico
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta tanto 'or' (inglés) como 'o' (español).
 * El operador es asociativo por la izquierda.
 *
 * @see LogicalOrExpressionNode
 */
public class LogicalOrExpressionParser implements IParser<LogicalOrExpressionNode> {
  private static final LogicalAndExpressionParser AND_PARSER = new LogicalAndExpressionParser();

  @Override
  public LogicalOrExpressionNode parse(ParserContext context) throws ParseException {
    LogicalAndExpressionNode firstOperand = AND_PARSER.parse(context);
    LogicalOrExpressionNode node = new LogicalOrExpressionNode(firstOperand.getToken(), firstOperand);

    while (context.check(TokenType.OR)) {
      Token operator = context.consume();
      node.addOperand(operator, AND_PARSER.parse(context));
    }
    return node;
  }

}
