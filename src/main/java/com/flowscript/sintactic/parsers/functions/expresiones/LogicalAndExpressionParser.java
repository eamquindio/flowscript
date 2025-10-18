package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalAndExpressionNode;

/**
 * Parser para manejar operaciones lógicas con AND.
 *
 * Regla:
 *   logicalAndExpr := equalityExpr ( 'and' equalityExpr )*
 *
 * Se evalúa de izquierda a derecha (asociatividad izquierda).
 */
public class LogicalAndExpressionParser implements IParser<ExpressionNode> {

    private final EqualityExpressionParser equalityParser;

    public LogicalAndExpressionParser() {
        this.equalityParser = new EqualityExpressionParser();
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Empezamos leyendo la primera expresión de igualdad
        ExpressionNode left = equalityParser.parse(context);

        // Mientras encontremos operadores "and", los encadenamos
        while (isAnd(context.getCurrentToken())) {
            Token op = context.consume();  // consumimos el 'and'
            ExpressionNode right = equalityParser.parse(context);

            // Construimos un nuevo nodo que une izquierda y derecha con AND
            left = new LogicalAndExpressionNode(left, op, right);
        }

        // Devolvemos la expresión resultante (posiblemente encadenada)
        return left;
    }

    private static boolean isAnd(Token t) {
        if (t == null) return false;
        // Aceptamos tanto el tipo como el texto literal del token
        return t.getType() == TokenType.AND || "and".equalsIgnoreCase(t.getValue());
    }
}
