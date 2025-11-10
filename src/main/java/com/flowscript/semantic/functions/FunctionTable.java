package com.flowscript.semantic.functions;

import com.flowscript.semantic.types.Type;
import com.flowscript.sintactic.ast.ASTNode;
import java.util.*;

public class FunctionTable {
    private final Map<String, FunctionSymbol> functions;
    private final Map<String, ModuleSymbol> importedModules;

    public FunctionTable() {
        this.functions = new LinkedHashMap<>();
        this.importedModules = new LinkedHashMap<>();
    }

    public void declareFunction(String name, List<Parameter> params,
                               Type returnType, ASTNode location) {
        if (functions.containsKey(name)) {
            throw new IllegalArgumentException(
                "Function '" + name + "' already declared"
            );
        }
        functions.put(name, new FunctionSymbol(name, params, returnType, location));
    }

    public void addModule(ModuleSymbol module) {
        importedModules.put(module.getName(), module);
    }

    public FunctionSymbol lookupFunction(String name) {
        // Check local functions first
        if (functions.containsKey(name)) {
            FunctionSymbol func = functions.get(name);
            func.markUsed();
            return func;
        }

        // Check imported modules
        for (ModuleSymbol module : importedModules.values()) {
            if (module.hasFunction(name)) {
                return module.getFunction(name);
            }
        }

        return null;
    }

    public FunctionSymbol lookupModuleFunction(String moduleName, String functionName) {
        ModuleSymbol module = importedModules.get(moduleName);
        if (module == null) {
            return null;
        }
        return module.getFunction(functionName);
    }

    public boolean isFunctionDeclared(String name) {
        return functions.containsKey(name);
    }

    public Collection<FunctionSymbol> getAllFunctions() {
        return functions.values();
    }

    public Collection<ModuleSymbol> getAllModules() {
        return importedModules.values();
    }
}
