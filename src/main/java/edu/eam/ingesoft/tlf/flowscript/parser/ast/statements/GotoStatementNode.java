package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class GotoStatementNode extends StatementNode {
    private final String targetName;

    public GotoStatementNode(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetName() { return targetName; }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
