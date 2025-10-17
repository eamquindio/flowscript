package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
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
        // TODO: Implementar este método
        ExpressionNode left = new AdditiveExpressionParser().parse(context);
        RelationalExpressionNode node = new RelationalExpressionNode(context.getCurrentToken(), left);

        while (context.getCurrentToken() != null) {
            TokenType type = context.getCurrentToken().getType();
            if (type != TokenType.LESS_THAN && type != TokenType.GREATER_THAN && type != TokenType.LESS_EQUAL && type != TokenType.GREATER_EQUAL) {
                break;
            }
            Token op = context.consume();
            ExpressionNode right = new AdditiveExpressionParser().parse(context);
            node.addOperand(op, right);
        }
        return node;
    }
}
