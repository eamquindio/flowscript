package edu.eam.ingesoft.tlf.flowscript.parser.ast.literals;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class StringLiteralNode extends LiteralNode {
    private final String value;
    
    public StringLiteralNode(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() { return value; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
