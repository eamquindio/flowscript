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
        RelationalExpressionParser relationalParser = new RelationalExpressionParser();
        ExpressionNode firstOperand = relationalParser.parse(context);
        Token firstToken = firstOperand.getToken();

        EqualityExpressionNode equalityNode = new EqualityExpressionNode(firstToken, firstOperand);

        while (true) {
            Token currentToken = context.getCurrentToken();

            String value = currentToken.getValue();
            if ("==".equals(value) || "!=".equals(value)) {
                context.consume();

                ExpressionNode nextOperand = relationalParser.parse(context);
                equalityNode.addOperand(currentToken, nextOperand);
            } else {
                break;
            }
        }

        return (EqualityExpressionNode) equalityNode.getSimplified();
    }
}

