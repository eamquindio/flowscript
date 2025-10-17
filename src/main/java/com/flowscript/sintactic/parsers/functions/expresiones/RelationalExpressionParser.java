package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.RelationalExpressionNode;

/**
 * Parser para expresiones relacionales.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * RelationalExpression ::= AdditiveExpression ( ( '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ) AdditiveExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 5: Expresiones - Relacionales
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores &lt;, &gt;, &lt;=, &gt;= (menor, mayor, menor o igual,
 * mayor o igual).
 * El operador es asociativo por la izquierda.
 *
 * @see RelationalExpressionNode
 */
public class RelationalExpressionParser implements IParser<RelationalExpressionNode> {
  private static final AdditiveExpressionParser ADDITIVE_PARSER = new AdditiveExpressionParser();

  @Override
  public RelationalExpressionNode parse(ParserContext context) throws ParseException {
    AdditiveExpressionNode leftOperand = ADDITIVE_PARSER.parse(context);
    RelationalExpressionNode node = new RelationalExpressionNode(leftOperand.getToken(), leftOperand);

    while (isRelationalOperator(context.getCurrentToken().getType())) {
      Token operator = context.consume();
      node.addOperand(operator, ADDITIVE_PARSER.parse(context));
    }

    return node;
  }

  private static boolean isRelationalOperator(TokenType type) {
    return type == TokenType.LESS_THAN
        || type == TokenType.GREATER_THAN
        || type == TokenType.LESS_EQUAL
        || type == TokenType.GREATER_EQUAL;
  }
}