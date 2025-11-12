package edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class BinaryExpressionNode extends ExpressionNode {
    public enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO,
        LESS_THAN, GREATER_THAN, LESS_EQUAL, GREATER_EQUAL,
        EQUAL, NOT_EQUAL, AND, OR
    }
    
    private final Operator operator;
    private final ExpressionNode left;
    private final ExpressionNode right;
    
    public BinaryExpressionNode(Operator operator, ExpressionNode left, ExpressionNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    
    public Operator getOperator() { return operator; }
    public ExpressionNode getLeft() { return left; }
    public ExpressionNode getRight() { return right; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
