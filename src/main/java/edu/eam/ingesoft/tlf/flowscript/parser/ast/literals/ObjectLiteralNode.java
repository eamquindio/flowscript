package edu.eam.ingesoft.tlf.flowscript.parser.ast.literals;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;
import java.util.*;

public class ObjectLiteralNode extends LiteralNode {
    public static class ObjectMember {
        private final String key;
        private final ExpressionNode value;
        
        public ObjectMember(String key, ExpressionNode value) {
            this.key = key;
            this.value = value;
        }
        
        public String getKey() { return key; }
        public ExpressionNode getValue() { return value; }
    }
    
    private final List<ObjectMember> members;
    
    public ObjectLiteralNode(List<ObjectMember> members) {
        this.members = members != null ? members : new ArrayList<>();
    }
    
    public List<ObjectMember> getMembers() { return members; }
    
    @Override
    public Map<String, Object> getValue() {
        return new HashMap<>();
    }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
