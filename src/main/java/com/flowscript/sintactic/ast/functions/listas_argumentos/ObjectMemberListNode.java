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

  public ObjectMemberListNode(Token firstToken, List<ObjectMemberNode> members) {
    super(firstToken);
    this.members = new ArrayList<>(members);
  }

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

  /**
   * Represents a single object member: key: value
   */
  // public static class ObjectMemberNode extends ASTNode {
  // private final String key;
  // private final ExpressionNode value;

  // public ObjectMemberNode(Token keyToken, String key, ExpressionNode value) {
  // super(keyToken);
  // this.key = key;
  // this.value = value;
  // }

  // public String getKey() {
  // return key;
  // }

  // public ExpressionNode getValue() {
  // return value;
  // }

  // @Override
  // public String getNodeType() {
  // return "ObjectMember";
  // }

  // @Override
  // public String toString() {
  // return key + ": " + value.getNodeType();
  // }
  // }
}
