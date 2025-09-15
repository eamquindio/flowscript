package edu.eam.ingesoft.flowscript.functions;

public class VariableReferenceExpression extends Expression {
    private String variableName;

    public VariableReferenceExpression(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}
