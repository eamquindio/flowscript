package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.sintactic.parsers.functions.literales.ObjectMemberParser;
import java.util.List;

/**
 * Parser for ObjectMemberList grammar rule.
 * Grammar: ObjectMemberList ::= ObjectMember ( ',' ObjectMember )*
 */
public class ObjectMemberListParser extends Parser {

    private final ObjectMemberParser memberParser;

    public ObjectMemberListParser(List<Token> tokens) {
        super(tokens);
        this.memberParser = new ObjectMemberParser(tokens);
    }

    public ObjectMemberListNode parseObjectMemberList() throws ParseException {
        // Parse first member
        memberParser.syncTo(getCurrentToken(), getCurrentIndex());
        ObjectMemberListNode.ObjectMemberNode firstMember = memberParser.parseObjectMember();
        syncTo(memberParser.getCurrentToken(), memberParser.getCurrentIndex());

        ObjectMemberListNode memberList = new ObjectMemberListNode(firstMember);

        // Parse additional members separated by commas
        while (",".equals(getCurrentToken().getValue())) {
            consume(); // consume ','

            memberParser.syncTo(getCurrentToken(), getCurrentIndex());
            ObjectMemberListNode.ObjectMemberNode member = memberParser.parseObjectMember();
            syncTo(memberParser.getCurrentToken(), memberParser.getCurrentIndex());

            memberList.addMember(member);
        }

        return memberList;
    }
}