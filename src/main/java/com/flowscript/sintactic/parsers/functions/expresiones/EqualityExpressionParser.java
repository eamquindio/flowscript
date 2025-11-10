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

    private final RelationalExpressionParser relationalParser;

    public EqualityExpressionParser() {
        this.relationalParser = new RelationalExpressionParser();
    }

    @Override
    public EqualityExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first operand
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstOperand = relationalParser.parse(context);

        // Check if there are equality operators
        if (context.getCurrentToken() == null || !isEqualityOperator(context.getCurrentToken())) {
            // No operators, just wrap the first operand and return as-is (no simplification)
            return new EqualityExpressionNode(firstOperand.getToken(), firstOperand);
        }

        // Create node with first operand
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        EqualityExpressionNode node = new EqualityExpressionNode(firstToken, firstOperand);

        // Parse operator and right operands (left-associative)
        while (context.getCurrentToken() != null && isEqualityOperator(context.getCurrentToken())) {
            com.flowscript.lexer.Token operator = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode right = relationalParser.parse(context);
            node.addOperand(operator, right);
        }

        return node;
    }

    private boolean isEqualityOperator(com.flowscript.lexer.Token token) {
        com.flowscript.lexer.TokenType type = token.getType();
        return type == com.flowscript.lexer.TokenType.EQUAL ||
               type == com.flowscript.lexer.TokenType.NOT_EQUAL;
    }
}
