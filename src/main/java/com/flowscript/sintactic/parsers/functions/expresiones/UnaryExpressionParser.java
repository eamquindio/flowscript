package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
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
        // TODO: Implementar este método
        if (context.match(TokenType.NOT) ||
            context.match(TokenType.MINUS) ||
            context.match(TokenType.IDENTIFIER, "no")) {

            Token operatorToken = context.getCurrentToken();
            context.consume();

            UnaryExpressionParser recursiveParser = new UnaryExpressionParser();
            ExpressionNode operand = recursiveParser.parse(context);

            return new UnaryExpressionNode(operatorToken, operand);
        }

        PostfixExpressionParser postfixParser = new PostfixExpressionParser();
        ExpressionNode expr = postfixParser.parse(context);

        if (expr instanceof UnaryExpressionNode) {
            return (UnaryExpressionNode) expr;
        } else {
            return new UnaryExpressionNode(new Token(TokenType.ERROR, "", context.getLine(), context.getColumn(), context.getPosition()), expr);
        }
    }
}
