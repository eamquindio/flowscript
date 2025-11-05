package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.UnaryExpressionNode;

/**
 * Parser para expresiones unarias.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * UnaryExpression ::= ( 'not' | 'no' | '-' )? PostfixExpression
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 8: Expresiones - Unarias
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores unarios: 'not'/'no' (negación lógica) y '-' (negación aritmética).
 * El operador es asociativo por la derecha (se evalúa de derecha a izquierda).
 *
 * @see UnaryExpressionNode
 */
public class UnaryExpressionParser implements IParser<UnaryExpressionNode> {

    @Override
    public UnaryExpressionNode parse(ParserContext context) throws Parser.ParseException {
        if (context.checkValue("not") || context.checkValue("no") || context.checkValue("-")) {
            Token operatorT = context.consume();
            UnaryExpressionNode operand = parse(context);
            return new UnaryExpressionNode(operatorT, operand);
        } else {
            ExpressionNode operand = new PostfixExpressionParser().parse(context);
            return new UnaryExpressionNode(operand.getToken(), operand);
        }
    }
}
