package edu.eam.ingesoft.tlf.flowscript.parser.ast.literals;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class NullLiteralNode extends LiteralNode {
    @Override
    public Object getValue() { return null; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
