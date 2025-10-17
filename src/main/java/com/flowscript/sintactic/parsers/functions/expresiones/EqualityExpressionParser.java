package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.EqualityExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.RelationalExpressionNode;

/**
 * Parser para expresiones de igualdad.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * EqualityExpression ::= RelationalExpression ( ( '==' | '!=' ) RelationalExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 4: Expresiones - Igualdad
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores == (igual) y != (distinto).
 * El operador es asociativo por la izquierda.
 *
 * @see EqualityExpressionNode
 */
public class EqualityExpressionParser implements IParser<EqualityExpressionNode> {
  private static final RelationalExpressionParser RELATIONAL_PARSER = new RelationalExpressionParser();

  @Override
  public EqualityExpressionNode parse(ParserContext context) throws ParseException {
    RelationalExpressionNode firstOperand = RELATIONAL_PARSER.parse(context);
    EqualityExpressionNode node = new EqualityExpressionNode(firstOperand.getToken(), firstOperand);

    while (isEqualityOperator(context.getCurrentToken().getType())) {
      Token operator = context.consume();
      node.addOperand(operator, RELATIONAL_PARSER.parse(context));
    }

    return node;
  }

  private boolean isEqualityOperator(TokenType type) {
    return type == TokenType.EQUAL || type == TokenType.NOT_EQUAL;
  }
}
