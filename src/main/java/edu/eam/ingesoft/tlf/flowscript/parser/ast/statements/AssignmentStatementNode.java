package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;

public class AssignmentStatementNode extends StatementNode {
    private final ExpressionNode target;
    private final ExpressionNode value;
    
    public AssignmentStatementNode(ExpressionNode target, ExpressionNode value) {
        this.target = target;
        this.value = value;
    }
    
    public ExpressionNode getTarget() { return target; }
    public ExpressionNode getValue() { return value; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
