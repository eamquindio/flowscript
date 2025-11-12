package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;

public class ForStatementNode extends StatementNode {
    private final String iteratorVariable;
    private final ExpressionNode iterable;
    private final BlockNode body;

    public ForStatementNode(String iteratorVariable, ExpressionNode iterable, BlockNode body) {
        this.iteratorVariable = iteratorVariable;
        this.iterable = iterable;
        this.body = body;
    }

    public String getIteratorVariable() { return iteratorVariable; }
    public ExpressionNode getIterable() { return iterable; }
    public BlockNode getBody() { return body; }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
