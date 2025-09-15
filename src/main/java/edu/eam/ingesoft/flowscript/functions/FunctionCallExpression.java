package edu.eam.ingesoft.flowscript.functions;

import java.util.List;

public class FunctionCallExpression extends Expression {
    private String functionName;
    private List<Expression> arguments;

    public FunctionCallExpression(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}
