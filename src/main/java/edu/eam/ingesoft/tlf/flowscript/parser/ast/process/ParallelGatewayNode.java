package edu.eam.ingesoft.tlf.flowscript.parser.ast.process;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import java.util.*;

public class ParallelGatewayNode extends ProcessElementNode {
    public static class ParallelBranch {
        private final String targetTaskName;

        public ParallelBranch(String targetTaskName) {
            this.targetTaskName = targetTaskName;
        }

        public String getTargetTaskName() { return targetTaskName; }
    }

    private final String name;
    private final List<ParallelBranch> branches;
    private final String mergeTargetName;

    public ParallelGatewayNode(String name, List<ParallelBranch> branches, String mergeTargetName) {
        this.name = name;
        this.branches = branches != null ? branches : new ArrayList<>();
        this.mergeTargetName = mergeTargetName;
    }

    public String getName() { return name; }
    public List<ParallelBranch> getBranches() { return branches; }
    public String getMergeTargetName() { return mergeTargetName; }
    public void addBranch(ParallelBranch branch) { branches.add(branch); }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
