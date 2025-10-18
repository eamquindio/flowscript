package com.flowscript.sintactic.ast.functions.literales;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.lexer.Token;
import java.util.List;

/**
 * Represents object literal expressions.
 * Grammar: ObjectLiteral ::= '{' ObjectMemberList? '}'
 * Examples: {}, { name: "Ana", age: 25 }
 */
public class ObjectLiteralNode extends ExpressionNode {
    private final ObjectMemberListNode memberList;

    public ObjectLiteralNode(Token leftBraceToken) {
        super(leftBraceToken);
        this.memberList = null;
    }

    public ObjectLiteralNode(Token leftBraceToken, ObjectMemberListNode memberList) {
        super(leftBraceToken);
        this.memberList = memberList;
    }

    public ObjectMemberListNode getMemberList() {
        return memberList;
    }

    public boolean isEmpty() {
        return memberList == null || memberList.isEmpty();
    }

    public int size() {
        return memberList == null ? 0 : memberList.size();
    }

    public List<ObjectMemberListNode.ObjectMemberNode> getMembers() {
        return memberList == null ? List.of() : memberList.getMembers();
    }

    @Override
    public String getNodeType() {
        return "ObjectLiteral";
    }

    @Override
    public String getExpressionType() {
        return "objeto";
    }

    @Override
    public String toString() {
        int count = memberList == null ? 0 : memberList.size();
        return "ObjectLiteral with " + count + " members";
    }
}