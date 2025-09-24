package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import java.util.List;

/**
 * Parser for ForStatement grammar rule.
 * Grammar: ForStatement ::= 'for' 'each' IDENTIFIER 'in' Expression Statement
 */
public class ForStatementParser extends Parser {

    private final ExpressionParser expressionParser;
    private final StatementParser statementParser;

    public ForStatementParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
        this.statementParser = new StatementParser(tokens);
    }

    public ForStatementNode parseForStatement() throws ParseException {
        Token forToken = consume(); // consume 'for' or 'para'

        // Expect 'each' or 'cada'
        if (!getCurrentToken().getValue().equals("cada") &&
            !getCurrentToken().getValue().equals("each")) {
            throw new ParseException("Expected 'each' after 'for'");
        }
        consume(); // consume 'each' or 'cada'

        // Expect iterator variable identifier
        if (getCurrentToken().getType() != TokenType.IDENTIFIER) {
            throw new ParseException("Expected iterator variable identifier");
        }
        String iteratorVariable = consume().getValue();

        // Expect 'in' or 'en'
        if (!getCurrentToken().getValue().equals("en") &&
            !getCurrentToken().getValue().equals("in")) {
            throw new ParseException("Expected 'in' after iterator variable");
        }
        consume(); // consume 'in' or 'en'

        // Parse iterable expression
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionNode iterable = expressionParser.parseExpression();
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        // Parse loop body statement
        statementParser.syncTo(getCurrentToken(), getCurrentIndex());
        StatementNode body = statementParser.parseStatement();
        syncTo(statementParser.getCurrentToken(), statementParser.getCurrentIndex());

        return new ForStatementNode(forToken, iteratorVariable, iterable, body);
    }
}