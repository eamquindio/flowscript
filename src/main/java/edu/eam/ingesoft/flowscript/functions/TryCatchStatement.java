package edu.eam.ingesoft.flowscript.functions;

import java.util.List;

public class TryCatchStatement extends Statement {
    private List<Statement> tryStatements;
    private String errorVariable;
    private List<Statement> catchStatements;

    public TryCatchStatement(List<Statement> tryStatements, String errorVariable, List<Statement> catchStatements) {
        this.tryStatements = tryStatements;
        this.errorVariable = errorVariable;
        this.catchStatements = catchStatements;
    }

    public List<Statement> getTryStatements() {
        return tryStatements;
    }

    public String getErrorVariable() {
        return errorVariable;
    }

    public List<Statement> getCatchStatements() {
        return catchStatements;
    }
}
