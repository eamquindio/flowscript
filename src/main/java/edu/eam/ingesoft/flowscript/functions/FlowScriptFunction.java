package edu.eam.ingesoft.flowscript.functions;

import java.util.List;

public class FlowScriptFunction {
    private String name;
    private List<Parameter> parameters;
    private String returnType;
    private List<Statement> body;

    public FlowScriptFunction(String name, List<Parameter> parameters, String returnType, List<Statement> body) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.body = body;
    }

    public String getName() { return name; }
    public List<Parameter> getParameters() { return parameters; }
    public String getReturnType() { return returnType; }
    public List<Statement> getBody() { return body; }
}
