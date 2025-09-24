package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberListParser;
import java.util.List;

/**
 * Parser for ObjectLiteral grammar rule.
 * Grammar: ObjectLiteral ::= '{' ObjectMemberList? '}'
 */
public class ObjectLiteralParser extends Parser {

    private final ObjectMemberListParser memberListParser;

    public ObjectLiteralParser(List<Token> tokens) {
        super(tokens);
        this.memberListParser = new ObjectMemberListParser(tokens);
    }

    public ObjectLiteralNode parseObjectLiteral() throws ParseException {
        Token leftBraceToken = consume(); // consume '{'

        // Check for empty object
        if ("}".equals(getCurrentToken().getValue())) {
            consume(); // consume '}'
            return new ObjectLiteralNode(leftBraceToken);
        }

        // Parse member list
        memberListParser.syncTo(getCurrentToken(), getCurrentIndex());
        ObjectMemberListNode memberList = memberListParser.parseObjectMemberList();
        syncTo(memberListParser.getCurrentToken(), memberListParser.getCurrentIndex());

        // Expect closing brace
        if (!"}".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected '}' after object members");
        }
        consume(); // consume '}'

        return new ObjectLiteralNode(leftBraceToken, memberList);
    }
}