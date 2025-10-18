package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.RelationalExpressionNode;

/**
 * Parser para expresiones relacionales.
 *
 * <h3>Gramática BNF:</h3>
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
 * Soporta operadores &lt;, &gt;, &lt;=, &gt;= (menor, mayor, menor o igual, mayor o igual).
 * El operador es asociativo por la izquierda.
 *
 * @see RelationalExpressionNode
 */
public class RelationalExpressionParser implements IParser<RelationalExpressionNode> {

    @Override
    public RelationalExpressionNode parse(ParserContext context) throws Parser.ParseException {
        AdditiveExpressionParser additiveParser = new AdditiveExpressionParser();
        ExpressionNode firstOperand = additiveParser.parse(context);

        RelationalExpressionNode relationalExpr = new RelationalExpressionNode(firstOperand.getToken(), firstOperand);

        while (true) {
            Token token = context.getCurrentToken();
            if (token == null) break;

            switch (token.getType()) {
                case LESS_THAN:
                case GREATER_THAN:
                case LESS_EQUAL:
                case GREATER_EQUAL:
                    context.consume(); // consume operator token

                    ExpressionNode nextOperand = additiveParser.parse(context);
                    relationalExpr.addOperand(token, nextOperand);
                    break;

                default:
                    return relationalExpr;
            }
        }

        return relationalExpr;
    }
}
