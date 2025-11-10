package com.flowscript.semantic.functions;

import java.util.*;

public class ModuleSymbol {
    private final String name;
    private final String path;
    private final Map<String, FunctionSymbol> functions;

    public ModuleSymbol(String name, String path) {
        this.name = name;
        this.path = path;
        this.functions = new LinkedHashMap<>();
    }

    public String getName() { return name; }
    public String getPath() { return path; }

    public void addFunction(FunctionSymbol function) {
        functions.put(function.getName(), function);
    }

    public boolean hasFunction(String name) {
        return functions.containsKey(name);
    }

    public FunctionSymbol getFunction(String name) {
        return functions.get(name);
    }

    public Collection<FunctionSymbol> getAllFunctions() {
        return functions.values();
    }
}
