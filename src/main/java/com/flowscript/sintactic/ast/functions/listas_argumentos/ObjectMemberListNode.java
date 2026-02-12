package com.flowscript.sintactic.ast.functions.listas_argumentos;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode; 
import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Represents object member lists: key1: value1, key2: value2
 * Grammar: ObjectMemberList ::= ObjectMember ( ',' ObjectMember )*
 * Used within object literals
 */
public class ObjectMemberListNode extends ASTNode {
    private final List<ObjectMemberNode> members;

    public ObjectMemberListNode(ObjectMemberNode firstMember) {
        super(firstMember.getLine(), firstMember.getColumn(), firstMember.getPosition());
        this.members = new ArrayList<>();
        this.members.add(firstMember);
    }

    public void addMember(ObjectMemberNode member) {
        members.add(member);
    }

    public List<ObjectMemberNode> getMembers() {
        return new ArrayList<>(members);
    }

    public int size() {
        return members.size();
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }

    public ObjectMemberNode getMember(int index) {
        return members.get(index);
    }

    public Map<String, ExpressionNode> getMemberMap() {
        Map<String, ExpressionNode> map = new LinkedHashMap<>();
        for (ObjectMemberNode member : members) {
            map.put(member.getKey(), member.getValue());
        }
        return map;
    }

    @Override
    public String getNodeType() {
        return "ObjectMemberList";
    }

    @Override
    public String toString() {
        return "ObjectMemberList with " + members.size() + " members";
    }
    public ObjectMemberListNode(List<ObjectMemberNode> members) {
        super(members != null && !members.isEmpty() ? members.get(0).getLine() : -1,
            members != null && !members.isEmpty() ? members.get(0).getColumn() : -1,
            members != null && !members.isEmpty() ? members.get(0).getPosition() : -1);
        this.members = new ArrayList<>(members);
    }
}