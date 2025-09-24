package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import java.util.List;
import java.util.ArrayList;

/**
 * Parser for Block grammar rule.
 * Grammar: Block ::= '{' StatementList '}'
 */
public class BlockParser extends Parser {

    private final StatementParser statementParser;

    public BlockParser(List<Token> tokens) {
        super(tokens);
        this.statementParser = new StatementParser(tokens);
    }

    public BlockNode parseBlock() throws ParseException {
        Token leftBraceToken = consume(); // consume '{'

        List<StatementNode> statements = new ArrayList<>();

        // Parse statements until we encounter '}'
        while (!getCurrentToken().getValue().equals("}")) {
            if (getCurrentToken().getType() == com.flowscript.lexer.TokenType.EOF) {
                throw new ParseException("Expected '}' to close block");
            }

            statementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode statement = statementParser.parseStatement();
            syncTo(statementParser.getCurrentToken(), statementParser.getCurrentIndex());

            statements.add(statement);
        }

        consume(); // consume '}'

        BlockNode block = new BlockNode(leftBraceToken);
        for (StatementNode statement : statements) {
            block.addStatement(statement);
        }
        return block;
    }
}