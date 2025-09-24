package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import java.util.List;

/**
 * Parser for ObjectMember grammar rule.
 * Grammar: ObjectMember ::= IDENTIFIER ':' Expression | STRING_LITERAL ':' Expression
 */
public class ObjectMemberParser extends Parser {

    private final ExpressionParser expressionParser;

    public ObjectMemberParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
    }

    public ObjectMemberListNode.ObjectMemberNode parseObjectMember() throws ParseException {
        Token keyToken = getCurrentToken();
        String key;

        // Parse key (identifier or string literal)
        if (keyToken.getType() == TokenType.IDENTIFIER) {
            key = consume().getValue();
        } else if (keyToken.getType() == TokenType.STRING_LITERAL) {
            key = consume().getValue();
            // Remove quotes from string key
            key = key.substring(1, key.length() - 1);
        } else {
            throw new ParseException("Expected identifier or string for object key");
        }

        // Expect ':'
        if (!":".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected ':' after object key");
        }
        consume(); // consume ':'

        // Parse value expression
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionNode value = expressionParser.parseExpression();
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        return new ObjectMemberListNode.ObjectMemberNode(keyToken, key, value);
    }
}