package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode;

/**
 * Parser para expresiones aditivas (suma y resta).
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * AdditiveExpression ::= MultiplicativeExpression ( ( '+' | '-' ) MultiplicativeExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 6: Expresiones - Aditivas
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores + (suma) y - (resta).
 * El operador es asociativo por la izquierda.
 *
 * @see AdditiveExpressionNode
 */
public class AdditiveExpressionParser implements IParser<AdditiveExpressionNode> {
  private static final MultiplicativeExpressionParser MULTIPLICATIVE_PARSER = new MultiplicativeExpressionParser();

  @Override
  public AdditiveExpressionNode parse(ParserContext context) throws ParseException {
    MultiplicativeExpressionNode firstOperand = MULTIPLICATIVE_PARSER.parse(context);
    AdditiveExpressionNode node = new AdditiveExpressionNode(firstOperand.getToken(), firstOperand);

    while (isAdditiveOperator(context.getCurrentToken().getType())) {
      Token operator = context.getCurrentToken();
      context.advance();
      node.addOperand(operator, MULTIPLICATIVE_PARSER.parse(context));
    }
    return node;
  }

  private boolean isAdditiveOperator(TokenType type) {
    return type == TokenType.PLUS || type == TokenType.MINUS;
  }
}
