package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import java.util.List;

/**
 * Parser for ExpressionList grammar rule.
 * Grammar: ExpressionList ::= Expression ( ',' Expression )*
 */
public class ExpressionListParser extends Parser {

    private final ExpressionParser expressionParser;

    public ExpressionListParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
    }

    public ExpressionListNode parseExpressionList() throws ParseException {
        // Parse first expression
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionNode firstExpr = expressionParser.parseExpression();
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        ExpressionListNode expressionList = new ExpressionListNode(firstExpr);

        // Parse additional expressions separated by commas
        while (",".equals(getCurrentToken().getValue())) {
            consume(); // consume ','

            expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
            ExpressionNode expr = expressionParser.parseExpression();
            syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

            expressionList.addExpression(expr);
        }

        return expressionList;
    }
}