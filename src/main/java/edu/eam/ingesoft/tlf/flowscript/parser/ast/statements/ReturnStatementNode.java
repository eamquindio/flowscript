package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;

public class ReturnStatementNode extends StatementNode {
    private final ExpressionNode value;
    
    public ReturnStatementNode(ExpressionNode value) {
        this.value = value;
    }
    
    public ExpressionNode getValue() { return value; }
    public boolean hasValue() { return value != null; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
