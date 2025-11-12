package edu.eam.ingesoft.tlf.flowscript.parser.ast.literals;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;
import java.util.*;

public class ListLiteralNode extends LiteralNode {
    private final List<ExpressionNode> elements;
    
    public ListLiteralNode(List<ExpressionNode> elements) {
        this.elements = elements != null ? elements : new ArrayList<>();
    }
    
    public List<ExpressionNode> getElements() { return elements; }
    
    @Override
    public List<Object> getValue() {
        return new ArrayList<>(elements);
    }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
