package edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.ASTNode;

public abstract class ExpressionNode extends ASTNode {
    public ExpressionNode() { super(); }
    public ExpressionNode(int line, int column) { super(line, column); }
}
