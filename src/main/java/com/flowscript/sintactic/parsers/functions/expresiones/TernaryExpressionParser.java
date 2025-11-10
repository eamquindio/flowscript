package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.TernaryExpressionNode;

/**
 * Parser para expresiones ternarias (operador condicional).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * TernaryExpression ::= LogicalOrExpression ( '?' Expression ':' Expression )?
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 1: Expresiones - Ternarias (más baja precedencia)
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * El operador ternario tiene la forma: condición ? valor_si_verdadero : valor_si_falso
 *
 * @see TernaryExpressionNode
 */
public class TernaryExpressionParser implements IParser<com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode> {

    private final LogicalOrExpressionParser orParser;
    private final ExpressionParser expressionParser;

    public TernaryExpressionParser() {
        this.orParser = new LogicalOrExpressionParser();
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Parse condition
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode condition = orParser.parse(context);

        // Check for ternary operator '?'
        if (context.getCurrentToken() != null &&
            context.getCurrentToken().getType() == com.flowscript.lexer.TokenType.QUESTION) {
            com.flowscript.lexer.Token questionToken = context.consume(); // consume '?'

            // Parse true branch
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode trueBranch = expressionParser.parse(context);

            // Consume ':'
            context.consume(com.flowscript.lexer.TokenType.COLON);

            // Parse false branch
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode falseBranch = expressionParser.parse(context);

            return new TernaryExpressionNode(condition, questionToken, trueBranch, falseBranch);
        }

        // No ternary operator, just return the condition
        return condition;
    }
}
