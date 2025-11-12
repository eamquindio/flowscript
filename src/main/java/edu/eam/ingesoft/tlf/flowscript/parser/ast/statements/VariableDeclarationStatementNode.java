package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;

public class VariableDeclarationStatementNode extends StatementNode {
    private final String name;
    private final ExpressionNode value;
    
    public VariableDeclarationStatementNode(String name, ExpressionNode value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() { return name; }
    public ExpressionNode getValue() { return value; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
