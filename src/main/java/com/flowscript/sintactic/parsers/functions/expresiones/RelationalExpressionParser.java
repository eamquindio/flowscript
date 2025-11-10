package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.RelationalExpressionNode;

/**
 * Parser para expresiones relacionales.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * RelationalExpression ::= AdditiveExpression ( ( '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ) AdditiveExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 5: Expresiones - Relacionales
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores &lt;, &gt;, &lt;=, &gt;= (menor, mayor, menor o igual, mayor o igual).
 * El operador es asociativo por la izquierda.
 *
 * @see RelationalExpressionNode
 */
public class RelationalExpressionParser implements IParser<RelationalExpressionNode> {

    private final AdditiveExpressionParser additiveParser;

    public RelationalExpressionParser() {
        this.additiveParser = new AdditiveExpressionParser();
    }

    @Override
    public RelationalExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first operand
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstOperand = additiveParser.parse(context);

        // Check if there are relational operators
        if (context.getCurrentToken() == null || !isRelationalOperator(context.getCurrentToken())) {
            // No operators, just wrap the first operand and return as-is (no simplification)
            return new RelationalExpressionNode(firstOperand.getToken(), firstOperand);
        }

        // Create node with first operand
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        RelationalExpressionNode node = new RelationalExpressionNode(firstToken, firstOperand);

        // Parse operator and right operands (left-associative)
        while (context.getCurrentToken() != null && isRelationalOperator(context.getCurrentToken())) {
            com.flowscript.lexer.Token operator = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode right = additiveParser.parse(context);
            node.addOperand(operator, right);
        }

        return node;
    }

    private boolean isRelationalOperator(com.flowscript.lexer.Token token) {
        com.flowscript.lexer.TokenType type = token.getType();
        return type == com.flowscript.lexer.TokenType.LESS_THAN ||
               type == com.flowscript.lexer.TokenType.GREATER_THAN ||
               type == com.flowscript.lexer.TokenType.LESS_EQUAL ||
               type == com.flowscript.lexer.TokenType.GREATER_EQUAL;
    }
}
