package com.flowscript.sintactic.parsers.process.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.JoinClauseNode;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ParallelGatewayNode;
import com.flowscript.sintactic.parsers.process.clausulas_control.JoinClauseParser;
import com.flowscript.sintactic.parsers.process.clausulas_control.ParallelBranchParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para gateways paralelos (AND).
 *
 * Gramática (BNF simplificada):
 * ParallelGateway ::= 'gateway' IDENTIFIER 'parallel' '{' ParallelBranch+ JoinClause '}'
 */
public class ParallelGatewayParser implements IParser<ParallelGatewayNode> {

    private final ParallelBranchParser branchParser;
    private final JoinClauseParser joinParser;

    public ParallelGatewayParser() {
        this.branchParser = new ParallelBranchParser();
        this.joinParser = new JoinClauseParser();
    }

    @Override
    public ParallelGatewayNode parse(ParserContext context) throws Parser.ParseException {
        // 'gateway'
        Token gatewayTok = consumeKeyword(context, TokenType.GATEWAY, "gateway");

        // nombre del gateway
        Token nameTok = consumeIdentifier(context, "nombre del gateway");
        String gatewayName = nameTok.getValue();

        // 'parallel'
        consumeKeyword(context, TokenType.PARALLEL, "parallel");

        // '{'
        consumeSymbol(context, TokenType.LEFT_BRACE, "{");

        // Uno o más branch
        List<ParallelBranchNode> branches = new ArrayList<>();
        while (check(context, TokenType.BRANCH, "branch")) {
            ParallelBranchNode b = branchParser.parse(context);
            branches.add(b);
        }
        if (branches.isEmpty()) {
            throw error(peekCurrent(context), "Se esperaba al menos una cláusula 'branch' en el gateway paralelo");
        }

        // join obligatorio
        if (!check(context, TokenType.JOIN, "join")) {
            throw error(peekCurrent(context), "Se esperaba la cláusula 'join' en el gateway paralelo");
        }
        JoinClauseNode join = joinParser.parse(context);

        // '}'
        consumeSymbol(context, TokenType.RIGHT_BRACE, "}");

        return new ParallelGatewayNode(gatewayTok, gatewayName, branches, join);
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
