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
public class UnaryExpressionParser implements IParser<com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode> {

    private final PostfixExpressionParser postfixParser;

    public UnaryExpressionParser() {
        this.postfixParser = new PostfixExpressionParser();
    }

    @Override
    public com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Expected unary expression but reached end of input");
        }

        // Check for unary operator
        if (isUnaryOperator(current)) {
            com.flowscript.lexer.Token operator = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode operand = postfixParser.parse(context);
            return new UnaryExpressionNode(operator, operand);
        }

        // No unary operator, just parse postfix expression
        return postfixParser.parse(context);
    }

    private boolean isUnaryOperator(com.flowscript.lexer.Token token) {
        com.flowscript.lexer.TokenType type = token.getType();
        return type == com.flowscript.lexer.TokenType.NOT ||
               type == com.flowscript.lexer.TokenType.MINUS;
    }
}
