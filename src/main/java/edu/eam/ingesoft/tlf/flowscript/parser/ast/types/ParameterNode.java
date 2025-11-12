package edu.eam.ingesoft.tlf.flowscript.parser.ast.types;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class ParameterNode extends ASTNode {
    private final String name;
    private final TypeNode type;
    
    public ParameterNode(String name, TypeNode type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() { return name; }
    public TypeNode getType() { return type; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
