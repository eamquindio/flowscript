package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.EqualityExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Parser para expresiones de igualdad.
 *
 * <h3>Gramática BNF:</h3>
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

    @Override
    public EqualityExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        ExpressionNode left = new RelationalExpressionParser().parse(context);
        Token firstT = left.getToken();

        EqualityExpressionNode node = new EqualityExpressionNode(firstT, left);

        while (context.hasMoreTokens()) {
            Token current = context.getCurrentToken();
            if (current == null)
                break;

            String dato = current.getValue();
            if (!dato.equals("==") && !dato.equals("!="))
                break;
            Token operatorT = context.consume();

            ExpressionNode right = new RelationalExpressionParser().parse(context);
            node.addOperand(operatorT, right);
        }
        return node;
    }
}
