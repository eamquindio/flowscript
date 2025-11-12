package edu.eam.ingesoft.tlf.flowscript.parser.ast.literals;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class BooleanLiteralNode extends LiteralNode {
    private final boolean value;
    
    public BooleanLiteralNode(boolean value) {
        this.value = value;
    }
    
    @Override
    public Boolean getValue() { return value; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
