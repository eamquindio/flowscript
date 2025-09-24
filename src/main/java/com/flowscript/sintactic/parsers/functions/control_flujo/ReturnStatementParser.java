package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ReturnStatementNode;
import java.util.List;

/**
 * Parser for ReturnStatement grammar rule.
 * Grammar: ReturnStatement ::= 'return' Expression?
 */
public class ReturnStatementParser extends Parser {

    private final ExpressionParser expressionParser;

    public ReturnStatementParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
    }

    public ReturnStatementNode parseReturnStatement() throws ParseException {
        Token returnToken = consume(); // consume 'return' or 'retornar'

        // Check if there's an expression to return
        if (getCurrentToken().getType() == TokenType.EOF ||
            getCurrentToken().getValue().equals("}") ||
            getCurrentToken().getValue().equals(";")) {
            // No expression, return null
            return new ReturnStatementNode(returnToken, null);
        }

        // Parse return expression
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionNode expression = expressionParser.parseExpression();
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        return new ReturnStatementNode(returnToken, expression);
    }
}