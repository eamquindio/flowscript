package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.statements_basicos.ExpressionStatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para sentencias de expresión.
 *
 * Grammar (informal):
 *   ExpressionStatement ::= Expression (';')?
 *
 * En FlowScript el ';' no es obligatorio; si está, se consume.
 */
public class ExpressionStatementParser implements IParser<ExpressionStatementNode> {

    private final ExpressionParser expressionParser;

    public ExpressionStatementParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public ExpressionStatementNode parse(ParserContext context) throws Parser.ParseException {
        Token start = context.getCurrentToken();
        if (start == null) {
            throw new Parser.ParseException("Se esperaba una expresión, pero no hay más tokens.");
        }

        // 1) Parsear la expresión con el parser de expresiones existente
        ExpressionNode expr = expressionParser.parse(context);

        // 2) Consumir ';' opcional
        consumeOptionalSemicolon(context);

        // 3) Construir el nodo de sentencia de expresión
        // Nota: si tu ExpressionStatementNode tiene un constructor diferente,
        // ajusta esta línea (por ejemplo: new ExpressionStatementNode(start, expr))
        return new ExpressionStatementNode(expr);
    }

    private static void consumeOptionalSemicolon(ParserContext ctx) {
        Token t = ctx.getCurrentToken();
        if (t == null) return;
        if (t.getType() == TokenType.SEMICOLON || ";".equals(t.getValue())) {
            ctx.advance();
        }
    }
}
