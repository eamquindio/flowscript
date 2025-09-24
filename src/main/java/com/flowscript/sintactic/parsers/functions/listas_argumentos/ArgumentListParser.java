package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;
import java.util.List;

/**
 * Parser for ArgumentList grammar rule.
 * Grammar: ArgumentList ::= Expression ( ',' Expression )*
 */
public class ArgumentListParser extends Parser {

    private final ExpressionParser expressionParser;

    public ArgumentListParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
    }

    public ArgumentListNode parseArgumentList() throws ParseException {
        // Parse first argument
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionNode firstArg = expressionParser.parseExpression();
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        ArgumentListNode argumentList = new ArgumentListNode(firstArg);

        // Parse additional arguments separated by commas
        while (",".equals(getCurrentToken().getValue())) {
            consume(); // consume ','

            expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
            ExpressionNode arg = expressionParser.parseExpression();
            syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

            argumentList.addArgument(arg);
        }

        return argumentList;
    }
}