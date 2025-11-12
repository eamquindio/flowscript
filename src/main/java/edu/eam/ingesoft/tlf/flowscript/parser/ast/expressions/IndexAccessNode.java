package edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class IndexAccessNode extends ExpressionNode {
    private final ExpressionNode array;
    private final ExpressionNode index;
    
    public IndexAccessNode(ExpressionNode array, ExpressionNode index) {
        this.array = array;
        this.index = index;
    }
    
    public ExpressionNode getArray() { return array; }
    public ExpressionNode getIndex() { return index; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
