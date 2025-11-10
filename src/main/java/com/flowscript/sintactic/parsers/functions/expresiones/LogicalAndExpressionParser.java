package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalAndExpressionNode;

/**
 * Parser para expresiones lógicas AND.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * LogicalAndExpression ::= EqualityExpression ( ( 'and' | 'y' ) EqualityExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 3: Expresiones - AND Lógico
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta tanto 'and' (inglés) como 'y' (español).
 * El operador es asociativo por la izquierda.
 *
 * @see LogicalAndExpressionNode
 */
public class LogicalAndExpressionParser implements IParser<LogicalAndExpressionNode> {

    private final EqualityExpressionParser equalityParser;

    public LogicalAndExpressionParser() {
        this.equalityParser = new EqualityExpressionParser();
    }

    @Override
    public LogicalAndExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first operand
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstOperand = equalityParser.parse(context);

        // Check if there are AND operators
        if (context.getCurrentToken() == null || !isAndOperator(context.getCurrentToken())) {
            // No operators, just wrap the first operand and return as-is (no simplification)
            return new LogicalAndExpressionNode(firstOperand.getToken(), firstOperand);
        }

        // Create node with first operand
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        LogicalAndExpressionNode node = new LogicalAndExpressionNode(firstToken, firstOperand);

        // Parse operator and right operands (left-associative)
        while (context.getCurrentToken() != null && isAndOperator(context.getCurrentToken())) {
            com.flowscript.lexer.Token operator = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode right = equalityParser.parse(context);
            node.addOperand(operator, right);
        }

        return node;
    }

    private boolean isAndOperator(com.flowscript.lexer.Token token) {
        return token.getType() == com.flowscript.lexer.TokenType.AND;
    }
}
