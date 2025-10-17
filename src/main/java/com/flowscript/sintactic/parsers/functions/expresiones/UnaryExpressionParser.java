package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
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
        Token current = context.getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("fin de entrada inesperado en ExpresionUnaria");
        }

        if (isUnaryOperator(current)) {
            Token operatorToken = context.consume(); 

            UnaryExpressionParser recursiveParser = new UnaryExpressionParser();
            UnaryExpressionNode operand = recursiveParser.parse(context);

            return new UnaryExpressionNode(operatorToken, operand);
        }

        PrimaryExpressionParser primaryParser = new PrimaryExpressionParser();
        ExpressionNode operand = primaryParser.parse(context);


        return new UnaryExpressionNode(operand.getToken(), operand);
    }

    private boolean isUnaryOperator(Token token) {
        if (token == null) return false;
        String value = token.getValue();
        return value.equals("not") || value.equals("no") || value.equals("-");
    }
}
