package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode;

/**
 * Parser para expresiones multiplicativas.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * MultiplicativeExpression ::= UnaryExpression ( ( '*' | '/' | '%' ) UnaryExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 7: Expresiones - Multiplicativas
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores * (multiplicación), / (división) y % (módulo).
 * El operador es asociativo por la izquierda.
 *
 * @see MultiplicativeExpressionNode
 */
public class MultiplicativeExpressionParser implements IParser<MultiplicativeExpressionNode> {

    private final UnaryExpressionParser unaryParser;

    public MultiplicativeExpressionParser() {
        this.unaryParser = new UnaryExpressionParser();
    }

    @Override
    public MultiplicativeExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first operand
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstOperand = unaryParser.parse(context);

        // Check if there are multiplicative operators
        if (context.getCurrentToken() == null || !isMultiplicativeOperator(context.getCurrentToken())) {
            // No operators, just wrap the first operand and return as-is (no simplification)
            // We need to return a MultiplicativeExpressionNode, even with single operand
            return new MultiplicativeExpressionNode(firstOperand.getToken(), firstOperand);
        }

        // Create node with first operand
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        MultiplicativeExpressionNode node = new MultiplicativeExpressionNode(firstToken, firstOperand);

        // Parse operator and right operands (left-associative)
        while (context.getCurrentToken() != null && isMultiplicativeOperator(context.getCurrentToken())) {
            com.flowscript.lexer.Token operator = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode right = unaryParser.parse(context);
            node.addOperand(operator, right);
        }

        return node;
    }

    private boolean isMultiplicativeOperator(com.flowscript.lexer.Token token) {
        com.flowscript.lexer.TokenType type = token.getType();
        return type == com.flowscript.lexer.TokenType.MULTIPLY ||
               type == com.flowscript.lexer.TokenType.DIVIDE ||
               type == com.flowscript.lexer.TokenType.MODULO;
    }
}
