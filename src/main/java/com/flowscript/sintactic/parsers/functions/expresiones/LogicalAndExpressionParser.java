package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalAndExpressionNode;

/**
 * logicalAndExpr := equalityExpr ( 'and' equalityExpr )*
 *
 * Asociatividad izquierda.
 */
public class LogicalAndExpressionParser implements IParser<ExpressionNode> {

    private final EqualityExpressionParser equalityParser;

    public LogicalAndExpressionParser() {
        this.equalityParser = new EqualityExpressionParser();
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        ExpressionNode left = equalityParser.parse(context);

        while (isAnd(context.getCurrentToken())) {
            Token op = context.consume();              // consume 'and'
            ExpressionNode right = equalityParser.parse(context);
            // Encadenamos en un nodo AND (izq op der)
            left = new LogicalAndExpressionNode(left, op, right);
        }

        return left;
    }

    private static boolean isAnd(Token t) {
        if (t == null) return false;
        // Acepta por tipo o por lexema (según como venga del lexer)
        return t.getType() == TokenType.AND || "and".equalsIgnoreCase(t.getValue());
    }
}
