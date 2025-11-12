package edu.eam.ingesoft.tlf.flowscript.semantic;

import java.util.*;

/**
 * Tabla de funciones declaradas en el programa.
 */
public class FunctionTable {
    private final Map<String, FunctionSymbol> functions;

    public FunctionTable() {
        this.functions = new LinkedHashMap<>();
    }

    /**
     * Declara una nueva función.
     * @return true si se declaró correctamente, false si ya existía
     */
    public boolean declare(FunctionSymbol function) {
        if (functions.containsKey(function.getName())) {
            return false;
        }
        functions.put(function.getName(), function);
        return true;
    }

    /**
     * Busca una función por nombre.
     */
    public FunctionSymbol lookup(String name) {
        return functions.get(name);
    }

    /**
     * Verifica si una función está declarada.
     */
    public boolean isDeclared(String name) {
        return functions.containsKey(name);
    }

    /**
     * Obtiene todas las funciones declaradas.
     */
    public Collection<FunctionSymbol> getFunctions() {
        return Collections.unmodifiableCollection(functions.values());
    }

    /**
     * Verifica si una llamada a función es válida.
     * @param name nombre de la función
     * @param argumentCount número de argumentos en la llamada
     * @return true si la llamada es válida
     */
    public boolean isValidCall(String name, int argumentCount) {
        FunctionSymbol function = lookup(name);
        if (function == null) {
            return false;
        }
        return function.getParameterCount() == argumentCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionTable:\n");
        for (FunctionSymbol function : functions.values()) {
            sb.append("  ").append(function).append("\n");
        }
        return sb.toString();
    }
}
