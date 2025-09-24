package com.flowscript.sintactic.parsers.process.navegacion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ast.process.navegacion.GotoStatementNode;
import java.util.List;

/**
 * Parser for GotoStatement grammar rule.
 * Grammar: GotoStatement ::= 'go_to' IDENTIFIER
 */
public class GotoStatementParser extends Parser {

    public GotoStatementParser(List<Token> tokens) {
        super(tokens);
    }

    public GotoStatementNode parseGotoStatement() throws ParseException {
        Token gotoToken = consume(); // consume 'go_to'

        if (getCurrentToken().getType() != TokenType.IDENTIFIER) {
            throw new ParseException("Expected identifier after 'go_to'");
        }

        Token targetToken = consume(); // consume target identifier

        return new GotoStatementNode(gotoToken, targetToken.getValue());
    }
}