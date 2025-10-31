package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.EqualityExpressionNode;

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
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode left =
            new com.flowscript.sintactic.parsers.functions.expresiones.RelationalExpressionParser().parse(context);

        com.flowscript.sintactic.ast.functions.expresiones.EqualityExpressionNode node =
            new com.flowscript.sintactic.ast.functions.expresiones.EqualityExpressionNode(firstToken, left);

        while (context.check(com.flowscript.lexer.TokenType.EQUAL) || context.check(com.flowscript.lexer.TokenType.NOT_EQUAL)) {
            com.flowscript.lexer.Token op = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode right =
                new com.flowscript.sintactic.parsers.functions.expresiones.RelationalExpressionParser().parse(context);
            node.addOperand(op, right);
        }

        return node;
    }
}
