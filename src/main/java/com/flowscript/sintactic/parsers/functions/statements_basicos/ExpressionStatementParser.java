package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.statements_basicos.ExpressionStatementNode;
import java.util.List;

/**
 * Parser for ExpressionStatement grammar rule.
 * Grammar: ExpressionStatement ::= Expression
 */
public class ExpressionStatementParser extends Parser {

    private final ExpressionParser expressionParser;

    public ExpressionStatementParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
    }

    public ExpressionStatementNode parseExpressionStatement() throws ParseException {
        // Sync position with main parser
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());

        ExpressionNode expression = expressionParser.parseExpression();

        // Sync back position
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        return new ExpressionStatementNode(expression);
    }
}