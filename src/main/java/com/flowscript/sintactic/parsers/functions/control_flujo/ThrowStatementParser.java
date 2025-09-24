package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ThrowStatementNode;
import java.util.List;

/**
 * Parser for ThrowStatement grammar rule.
 * Grammar: ThrowStatement ::= 'throw' Expression
 */
public class ThrowStatementParser extends Parser {

    private final ExpressionParser expressionParser;

    public ThrowStatementParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
    }

    public ThrowStatementNode parseThrowStatement() throws ParseException {
        Token throwToken = consume(); // consume 'throw' or 'lanzar'

        // Parse exception expression
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionNode expression = expressionParser.parseExpression();
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        return new ThrowStatementNode(throwToken, expression);
    }
}