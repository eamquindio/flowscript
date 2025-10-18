package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;

/**
 * ParallelBranch ::= 'branch' '->' IDENTIFIER
 */
public class ParallelBranchParser implements IParser<ParallelBranchNode> {

    @Override
    public ParallelBranchNode parse(ParserContext context) throws Parser.ParseException {
        // 'branch'
        Token branchTok = consumeKeyword(context, TokenType.BRANCH, "branch");

        // '->'
        consumeSymbol(context, TokenType.ARROW, "->");

        // target label
        Token labelTok = consumeIdentifier(context, "destino del branch");
        String target = labelTok.getValue();

        return new ParallelBranchNode(branchTok, target);
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
