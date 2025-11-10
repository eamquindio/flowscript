package com.flowscript.semantic.functions;

import com.flowscript.semantic.symbols.Symbol;
import com.flowscript.semantic.types.Type;
import com.flowscript.sintactic.ast.ASTNode;
import java.util.List;
import java.util.ArrayList;

public class FunctionSymbol extends Symbol {
    private final List<Parameter> parameters;
    private final Type returnType;

    public FunctionSymbol(String name, List<Parameter> parameters,
                         Type returnType, ASTNode declarationNode) {
        super(name, returnType, declarationNode);
        this.parameters = new ArrayList<>(parameters);
        this.returnType = returnType;
    }

    public List<Parameter> getParameters() {
        return new ArrayList<>(parameters);
    }

    public Type getReturnType() {
        return returnType;
    }

    public int getParameterCount() {
        return parameters.size();
    }

    @Override
    public String getKind() {
        return "function";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("(");
        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(parameters.get(i));
        }
        sb.append(") -> ").append(returnType);
        return sb.toString();
    }
}
