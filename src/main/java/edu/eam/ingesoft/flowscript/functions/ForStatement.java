package edu.eam.ingesoft.flowscript.functions;

import java.util.List;

public class ForStatement extends Statement {
    private String variableName;
    private Expression iterable;
    private List<Statement> body;

    public ForStatement(String variableName, Expression iterable, List<Statement> body) {
        this.variableName = variableName;
        this.iterable = iterable;
        this.body = body;
    }

    public String getVariableName() {
        return variableName;
    }

    public Expression getIterable() {
        return iterable;
    }

    public List<Statement> getBody() {
        return body;
    }
}
