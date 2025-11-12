package edu.eam.ingesoft.tlf.flowscript.parser.ast.literals;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class DecimalLiteralNode extends LiteralNode {
    private final double value;
    
    public DecimalLiteralNode(double value) {
        this.value = value;
    }
    
    @Override
    public Double getValue() { return value; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
