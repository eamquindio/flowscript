package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser;
import java.util.List;

/**
 * Parser for ListLiteral grammar rule.
 * Grammar: ListLiteral ::= '[' ExpressionList? ']'
 */
public class ListLiteralParser extends Parser {

    private final ExpressionListParser expressionListParser;

    public ListLiteralParser(List<Token> tokens) {
        super(tokens);
        this.expressionListParser = new ExpressionListParser(tokens);
    }

    public ListLiteralNode parseListLiteral() throws ParseException {
        Token leftBracketToken = consume(); // consume '['

        // Check for empty list
        if ("]".equals(getCurrentToken().getValue())) {
            consume(); // consume ']'
            return new ListLiteralNode(leftBracketToken);
        }

        // Parse expression list
        expressionListParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionListNode expressionList = expressionListParser.parseExpressionList();
        syncTo(expressionListParser.getCurrentToken(), expressionListParser.getCurrentIndex());

        // Expect closing bracket
        if (!"]".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected ']' after list elements");
        }
        consume(); // consume ']'

        return new ListLiteralNode(leftBracketToken, expressionList);
    }
}