package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;
import java.util.*;

public class IfStatementNode extends StatementNode {
    public static class ConditionalBranch {
        private final ExpressionNode condition;  // null para 'sino'
        private final BlockNode thenBlock;

        public ConditionalBranch(ExpressionNode condition, BlockNode thenBlock) {
            this.condition = condition;
            this.thenBlock = thenBlock;
        }

        public ExpressionNode getCondition() { return condition; }
        public BlockNode getThenBlock() { return thenBlock; }
        public boolean isElseBranch() { return condition == null; }
    }

    private final List<ConditionalBranch> branches;

    public IfStatementNode(List<ConditionalBranch> branches) {
        this.branches = branches != null ? branches : new ArrayList<>();
    }

    public List<ConditionalBranch> getBranches() { return branches; }
    public void addBranch(ConditionalBranch branch) { branches.add(branch); }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
