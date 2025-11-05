package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;


/**
 * Parser para expresiones aditivas (suma y resta).
 *
 * <h3>Gramática BNF:</h3>
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

    @Override
    public AdditiveExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        ExpressionNode first = new MultiplicativeExpressionParser().parse(context);
        Token firstT = first.getToken();

        AdditiveExpressionNode node = new AdditiveExpressionNode(firstT, first);
        while (context.checkValue("+") || context.checkValue("-")) {
            Token op = context.consume();
            ExpressionNode right = new MultiplicativeExpressionParser().parse(context);
            node.addOperand(op, right);
        }
        return node;
    }
}
