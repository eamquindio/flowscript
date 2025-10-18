package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode;

/**
 * Parser para manejar operaciones lógicas con OR.
 *
 * Regla:
 *   logicalOrExpr := logicalAndExpr ( 'or' logicalAndExpr )*
 *
 * Se evalúa de izquierda a derecha (asociatividad izquierda).
 */
public class LogicalOrExpressionParser implements IParser<ExpressionNode> {

    private final LogicalAndExpressionParser andParser;

    public LogicalOrExpressionParser() {
        this.andParser = new LogicalAndExpressionParser();
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Empezamos leyendo la primera parte (una expresión AND)
        ExpressionNode left = andParser.parse(context);

        // Mientras encontremos un "or", seguimos encadenando
        while (isOr(context.getCurrentToken())) {
            Token op = context.consume();  // consumimos el 'or'
            ExpressionNode right = andParser.parse(context);

            // Unimos la expresión izquierda y derecha en un nuevo nodo OR
            left = new LogicalOrExpressionNode(left, op, right);
        }

        // Devolvemos la expresión final (puede ser compuesta)
        return left;
    }

    private static boolean isOr(Token t) {
        if (t == null) return false;
        // Aceptamos tanto el tipo de token OR como el texto literal "or"
        return t.getType() == TokenType.OR || "or".equalsIgnoreCase(t.getValue());
    }
}
