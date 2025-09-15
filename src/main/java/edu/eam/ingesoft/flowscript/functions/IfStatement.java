package edu.eam.ingesoft.flowscript.functions;

import java.util.List;

public class IfStatement extends Statement {
    private Expression condition;
    private List<Statement> thenStatements;
    private List<Statement> elseStatements;

    public IfStatement(Expression condition, List<Statement> thenStatements, List<Statement> elseStatements) {
        this.condition = condition;
        this.thenStatements = thenStatements;
        this.elseStatements = elseStatements;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getThenStatements() {
        return thenStatements;
    }

    public List<Statement> getElseStatements() {
        return elseStatements;
    }
}
