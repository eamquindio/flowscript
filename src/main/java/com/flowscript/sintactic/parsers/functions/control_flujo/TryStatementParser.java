package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ast.functions.control_flujo.TryStatementNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.BlockParser;
import java.util.List;

/**
 * Parser for TryStatement grammar rule.
 * Grammar: TryStatement ::= 'try' Block 'catch' '(' IDENTIFIER ')' Block
 */
public class TryStatementParser extends Parser {

    private final BlockParser blockParser;

    public TryStatementParser(List<Token> tokens) {
        super(tokens);
        this.blockParser = new BlockParser(tokens);
    }

    public TryStatementNode parseTryStatement() throws ParseException {
        Token tryToken = consume(); // consume 'try' or 'intentar'

        // Parse try block
        blockParser.syncTo(getCurrentToken(), getCurrentIndex());
        BlockNode tryBlock = blockParser.parseBlock();
        syncTo(blockParser.getCurrentToken(), blockParser.getCurrentIndex());

        // Expect 'catch' or 'capturar'
        if (!getCurrentToken().getValue().equals("capturar") &&
            !getCurrentToken().getValue().equals("catch")) {
            throw new ParseException("Expected 'catch' after try block");
        }
        consume(); // consume 'catch' or 'capturar'

        // Expect '('
        if (!getCurrentToken().getValue().equals("(")) {
            throw new ParseException("Expected '(' after catch");
        }
        consume(); // consume '('

        // Expect identifier
        if (getCurrentToken().getType() != TokenType.IDENTIFIER) {
            throw new ParseException("Expected exception variable identifier");
        }
        String exceptionVariable = consume().getValue();

        // Expect ')'
        if (!getCurrentToken().getValue().equals(")")) {
            throw new ParseException("Expected ')' after exception variable");
        }
        consume(); // consume ')'

        // Parse catch block
        blockParser.syncTo(getCurrentToken(), getCurrentIndex());
        BlockNode catchBlock = blockParser.parseBlock();
        syncTo(blockParser.getCurrentToken(), blockParser.getCurrentIndex());

        return new TryStatementNode(tryToken, tryBlock, exceptionVariable, catchBlock);
    }
}