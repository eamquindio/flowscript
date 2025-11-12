package edu.eam.ingesoft.tlf.flowscript.parser.ast.process;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class StartElementNode extends ProcessElementNode {
    private final String targetTaskName;

    public StartElementNode(String targetTaskName) {
        this.targetTaskName = targetTaskName;
    }

    public String getTargetTaskName() { return targetTaskName; }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
