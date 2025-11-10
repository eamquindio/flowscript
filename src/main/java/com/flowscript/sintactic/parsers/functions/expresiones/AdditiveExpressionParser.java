package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode;

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

    private final MultiplicativeExpressionParser multiplicativeParser;

    public AdditiveExpressionParser() {
        this.multiplicativeParser = new MultiplicativeExpressionParser();
    }

    @Override
    public AdditiveExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first operand
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstOperand = multiplicativeParser.parse(context);

        // Check if there are additive operators
        if (context.getCurrentToken() == null || !isAdditiveOperator(context.getCurrentToken())) {
            // No operators, just wrap the first operand and return as-is (no simplification)
            return new AdditiveExpressionNode(firstOperand.getToken(), firstOperand);
        }

        // Create node with first operand
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        AdditiveExpressionNode node = new AdditiveExpressionNode(firstToken, firstOperand);

        // Parse operator and right operands (left-associative)
        while (context.getCurrentToken() != null && isAdditiveOperator(context.getCurrentToken())) {
            com.flowscript.lexer.Token operator = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode right = multiplicativeParser.parse(context);
            node.addOperand(operator, right);
        }

        return node;
    }

    private boolean isAdditiveOperator(com.flowscript.lexer.Token token) {
        com.flowscript.lexer.TokenType type = token.getType();
        return type == com.flowscript.lexer.TokenType.PLUS ||
               type == com.flowscript.lexer.TokenType.MINUS;
    }
}
