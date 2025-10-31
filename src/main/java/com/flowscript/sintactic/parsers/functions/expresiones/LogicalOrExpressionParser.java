package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode;

/**
 * Analizador para expresiones con operador lógico OR.
 *
 * Regla de producción:
 *   LogicalOrExpression ::= LogicalAndExpression ('or' LogicalAndExpression)*
 *
 * Asociación de izquierda a derecha.
 */
public class LogicalOrExpressionParser implements IParser<ExpressionNode> {

    private final LogicalAndExpressionParser andExprParser;

    public LogicalOrExpressionParser() {
        this.andExprParser = new LogicalAndExpressionParser();
    }

    @Override
    public ExpressionNode parse(ParserContext ctx) throws Parser.ParseException {
        // Parseamos el primer operando
        ExpressionNode leftExpr = andExprParser.parse(ctx);

        // Mientras encontremos 'or', seguimos creando nodos encadenados
        while (isOrOperator(ctx.getCurrentToken())) {
            Token operator = ctx.consume(); // consumir 'or'
            ExpressionNode rightExpr = andExprParser.parse(ctx);

            // Encadenar en un nuevo nodo OR
            leftExpr = new LogicalOrExpressionNode(leftExpr, operator, rightExpr);
        }

        return leftExpr;
    }

    /**
     * Verifica si el token actual representa el operador lógico OR.
     */
    private static boolean isOrOperator(Token token) {
        if (token == null) return false;
        return token.getType() == TokenType.OR || "or".equalsIgnoreCase(token.getValue());
    }
}
