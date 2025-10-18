package com.flowscript.sintactic.parsers.process.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ElseClauseNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ExclusiveGatewayNode;
import com.flowscript.sintactic.parsers.process.clausulas_control.ElseClauseParser;
import com.flowscript.sintactic.parsers.process.clausulas_control.WhenClauseParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para gateways exclusivos (XOR).
 *
 * Gramática (BNF simplificada):
 * ExclusiveGateway ::= 'gateway' IDENTIFIER '{' WhenClause+ ElseClause? '}'
 */
public class ExclusiveGatewayParser implements IParser<ExclusiveGatewayNode> {

    private final WhenClauseParser whenParser;
    private final ElseClauseParser elseParser;

    public ExclusiveGatewayParser() {
        this.whenParser = new WhenClauseParser();
        this.elseParser = new ElseClauseParser();
    }

    @Override
    public ExclusiveGatewayNode parse(ParserContext context) throws Parser.ParseException {
        // 'gateway'
        Token gatewayTok = consumeKeyword(context, TokenType.GATEWAY, "gateway");

        // nombre del gateway
        Token nameTok = consumeIdentifier(context, "nombre del gateway");
        String gatewayName = nameTok.getValue();

        // '{'
        consumeSymbol(context, TokenType.LEFT_BRACE, "{");

        // Uno o más when
        List<WhenClauseNode> whens = new ArrayList<>();
        while (check(context, TokenType.WHEN, "when")) {
            WhenClauseNode when = whenParser.parse(context);
            whens.add(when);
        }
        if (whens.isEmpty()) {
            throw error(peekCurrent(context), "Se esperaba al menos una cláusula 'when' dentro del gateway exclusivo");
        }

        // else opcional
        ElseClauseNode elseNode = null;
        if (check(context, TokenType.ELSE, "else")) {
            elseNode = elseParser.parse(context);
        }

        // '}'
        consumeSymbol(context, TokenType.RIGHT_BRACE, "}");

        return new ExclusiveGatewayNode(gatewayTok, gatewayName, whens, elseNode);
    }

    // ------------------ utilidades ------------------

    private static boolean check(ParserContext ctx, TokenType type, String lexeme) {
        Token t = ctx.getCurrentToken();
        return t != null && (t.getType() == type || lexeme.equals(t.getValue()));
    }

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

    private static Token peekCurrent(ParserContext ctx) {
        return ctx.getCurrentToken();
    }

    private static Parser.ParseException error(Token t, String msg) {
        if (t == null) return new Parser.ParseException(msg + ". Fin de entrada.");
        return new Parser.ParseException(msg + " pero se encontró '" + t.getValue()
                + "' en línea " + t.getLine() + ", columna " + t.getColumn());
    }
}
