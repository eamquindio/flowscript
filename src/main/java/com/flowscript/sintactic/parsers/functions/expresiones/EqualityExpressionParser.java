package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.TokenType;
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
        RelationalExpressionParser relationalParser = new RelationalExpressionParser();
        ExpressionNode left = relationalParser.parse(context);

        EqualityExpressionNode node = new EqualityExpressionNode(context.getCurrentToken(), left);

        while (context.checkAny(TokenType.EQUAL, TokenType.NOT_EQUAL)) {
            Token operator = context.consume();
            ExpressionNode right = relationalParser.parse(context);
            node.addOperand(operator, right);
        }

        return node;
    }
}
