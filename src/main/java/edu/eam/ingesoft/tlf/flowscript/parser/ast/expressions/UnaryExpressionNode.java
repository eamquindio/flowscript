package edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class UnaryExpressionNode extends ExpressionNode {
    public enum Operator { LOGICAL_NOT, NEGATE }
    
    private final Operator operator;
    private final ExpressionNode operand;
    
    public UnaryExpressionNode(Operator operator, ExpressionNode operand) {
        this.operator = operator;
        this.operand = operand;
    }
    
    public Operator getOperator() { return operator; }
    public ExpressionNode getOperand() { return operand; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
