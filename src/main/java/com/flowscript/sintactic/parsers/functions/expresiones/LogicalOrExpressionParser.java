package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode;

/**
 * Parser para expresiones lógicas OR.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * LogicalOrExpression ::= LogicalAndExpression ( ( 'or' | 'o' ) LogicalAndExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 2: Expresiones - OR Lógico
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta tanto 'or' (inglés) como 'o' (español).
 * El operador es asociativo por la izquierda.
 *
 * @see LogicalOrExpressionNode
 */
public class LogicalOrExpressionParser implements IParser<LogicalOrExpressionNode> {

    private final LogicalAndExpressionParser andParser;

    public LogicalOrExpressionParser() {
        this.andParser = new LogicalAndExpressionParser();
    }

    @Override
    public LogicalOrExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first operand
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstOperand = andParser.parse(context);

        // Check if there are OR operators
        if (context.getCurrentToken() == null || !isOrOperator(context.getCurrentToken())) {
            // No operators, just wrap the first operand and return as-is (no simplification)
            return new LogicalOrExpressionNode(firstOperand.getToken(), firstOperand);
        }

        // Create node with first operand
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        LogicalOrExpressionNode node = new LogicalOrExpressionNode(firstToken, firstOperand);

        // Parse operator and right operands (left-associative)
        while (context.getCurrentToken() != null && isOrOperator(context.getCurrentToken())) {
            com.flowscript.lexer.Token operator = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode right = andParser.parse(context);
            node.addOperand(operator, right);
        }

        return node;
    }

    private boolean isOrOperator(com.flowscript.lexer.Token token) {
        return token.getType() == com.flowscript.lexer.TokenType.OR;
    }
}
