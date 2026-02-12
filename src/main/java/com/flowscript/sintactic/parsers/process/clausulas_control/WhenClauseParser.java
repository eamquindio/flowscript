package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * WhenClause ::= 'when' Expression '->' IDENTIFIER
 */
public class WhenClauseParser implements IParser<WhenClauseNode> {

    private final ExpressionParser exprParser;

    public WhenClauseParser() {
        this.exprParser = new ExpressionParser();
    }

    @Override
    public WhenClauseNode parse(ParserContext context) throws Parser.ParseException {
        // 'when'
        Token whenTok = consumeKeyword(context, TokenType.WHEN, "when");

        // Expression
        ExpressionNode condition = exprParser.parse(context);

        // '->'
        consumeSymbol(context, TokenType.ARROW, "->");

        // target label
        Token labelTok = consumeIdentifier(context, "destino del when");
        String target = labelTok.getValue();

        return new WhenClauseNode(whenTok, condition, target);
    }

    // ---- utils ----
    private static Token consumeKeyword(ParserContext ctx, TokenType type, String lexeme) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) throw new Parser.ParseException("Se esperaba '" + lexeme + "', pero no hay más tokens.");
        if (t.getType() != type && !lexeme.equals(t.getValue())) {
            throw error(t, "Se esperaba '" + lexeme + "'");
        }
        ctx.advance();
        return t;
    }

    private static void consumeSymbol(ParserContext ctx, TokenType type, String lexeme) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) throw new Parser.ParseException("Se esperaba '" + lexeme + "', pero no hay más tokens.");
        if (t.getType() != type && !lexeme.equals(t.getValue())) {
            throw error(t, "Se esperaba '" + lexeme + "'");
        }
        ctx.advance();
    }

    private static Token consumeIdentifier(ParserContext ctx, String what) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) throw new Parser.ParseException("Se esperaba " + what + ", pero no hay más tokens.");
        if (t.getType() != TokenType.IDENTIFIER) {
            throw error(t, "Se esperaba un identificador para " + what);
        }
        ctx.advance();
        return t;
    }

    private static Parser.ParseException error(Token t, String msg) {
        if (t == null) return new Parser.ParseException(msg + ". Fin de entrada.");
        return new Parser.ParseException(msg + " pero se encontró '" + t.getValue()
                + "' en línea " + t.getLine() + ", columna " + t.getColumn());
    }
}
