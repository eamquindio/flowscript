package edu.eam.ingesoft.tlf.flowscript.parser.ast.process;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.StatementNode;
import java.util.*;

public class TaskElementNode extends ProcessElementNode {
    private final String name;
    private final List<StatementNode> actions;

    public TaskElementNode(String name, List<StatementNode> actions) {
        this.name = name;
        this.actions = actions != null ? actions : new ArrayList<>();
    }

    public String getName() { return name; }
    public List<StatementNode> getActions() { return actions; }
    public void addAction(StatementNode action) { actions.add(action); }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
