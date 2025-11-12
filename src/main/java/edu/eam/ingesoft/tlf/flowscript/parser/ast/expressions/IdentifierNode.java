package edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class IdentifierNode extends ExpressionNode {
    private final String name;
    
    public IdentifierNode(String name) {
        this.name = name;
    }
    
    public String getName() { return name; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
