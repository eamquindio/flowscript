package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;

public class ExpressionStatementNode extends StatementNode {
    private final ExpressionNode expression;
    
    public ExpressionStatementNode(ExpressionNode expression) {
        this.expression = expression;
    }
    
    public ExpressionNode getExpression() { return expression; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
