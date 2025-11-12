package edu.eam.ingesoft.tlf.flowscript.parser.ast.literals;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class IntegerLiteralNode extends LiteralNode {
    private final long value;
    
    public IntegerLiteralNode(long value) {
        this.value = value;
    }
    
    @Override
    public Long getValue() { return value; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
