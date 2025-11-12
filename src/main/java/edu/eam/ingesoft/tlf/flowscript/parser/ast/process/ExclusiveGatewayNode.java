package edu.eam.ingesoft.tlf.flowscript.parser.ast.process;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;
import java.util.*;

public class ExclusiveGatewayNode extends ProcessElementNode {
    public static class ConditionalBranch {
        private final ExpressionNode condition;  // null para 'sino'
        private final String targetTaskName;

        public ConditionalBranch(ExpressionNode condition, String targetTaskName) {
            this.condition = condition;
            this.targetTaskName = targetTaskName;
        }

        public ExpressionNode getCondition() { return condition; }
        public String getTargetTaskName() { return targetTaskName; }
        public boolean isElseBranch() { return condition == null; }
    }

    private final String name;
    private final List<ConditionalBranch> branches;

    public ExclusiveGatewayNode(String name, List<ConditionalBranch> branches) {
        this.name = name;
        this.branches = branches != null ? branches : new ArrayList<>();
    }

    public String getName() { return name; }
    public List<ConditionalBranch> getBranches() { return branches; }
    public void addBranch(ConditionalBranch branch) { branches.add(branch); }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
