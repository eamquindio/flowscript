package edu.eam.ingesoft.flowscript.functions;

public class VariableDeclarationStatement extends Statement {
    private String variableName;
    private Expression expression;

    public VariableDeclarationStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    public String getVariableName() {
        return variableName;
    }

    public Expression getExpression() {
        return expression;
    }
}
