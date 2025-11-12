package edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class MemberAccessNode extends ExpressionNode {
    private final ExpressionNode object;
    private final String memberName;
    
    public MemberAccessNode(ExpressionNode object, String memberName) {
        this.object = object;
        this.memberName = memberName;
    }
    
    public ExpressionNode getObject() { return object; }
    public String getMemberName() { return memberName; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
