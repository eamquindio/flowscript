package edu.eam.ingesoft.tlf.flowscript.semantic;

import java.util.*;

/**
 * Tabla de símbolos con soporte para scoping jerárquico.
 * Permite crear scopes anidados (bloques, funciones, etc.)
 */
public class SymbolTable {
    private final Map<String, Symbol> symbols;
    private final SymbolTable parent;
    private final String scopeName;
    private final List<SymbolTable> children;

    public SymbolTable(String scopeName, SymbolTable parent) {
        this.scopeName = scopeName;
        this.parent = parent;
        this.symbols = new LinkedHashMap<>();
        this.children = new ArrayList<>();

        if (parent != null) {
            parent.addChild(this);
        }
    }

    public SymbolTable(String scopeName) {
        this(scopeName, null);
    }

    private void addChild(SymbolTable child) {
        this.children.add(child);
    }

    /**
     * Declara un nuevo símbolo en el scope actual.
     * @return true si se declaró correctamente, false si ya existía
     */
    public boolean declare(Symbol symbol) {
        if (symbols.containsKey(symbol.getName())) {
            return false;
        }
        symbols.put(symbol.getName(), symbol);
        return true;
    }

    /**
     * Busca un símbolo en el scope actual.
     * @return el símbolo si existe en este scope, null si no
     */
    public Symbol lookupLocal(String name) {
        return symbols.get(name);
    }

    /**
     * Busca un símbolo en el scope actual y en scopes padres.
     * @return el símbolo si existe, null si no
     */
    public Symbol lookup(String name) {
        Symbol symbol = lookupLocal(name);
        if (symbol != null) {
            return symbol;
        }

        if (parent != null) {
            return parent.lookup(name);
        }

        return null;
    }

    /**
     * Verifica si un símbolo está declarado en el scope actual.
     */
    public boolean isDeclaredLocally(String name) {
        return symbols.containsKey(name);
    }

    /**
     * Verifica si un símbolo está declarado (en cualquier scope).
     */
    public boolean isDeclared(String name) {
        return lookup(name) != null;
    }

    /**
     * Obtiene todos los símbolos del scope actual.
     */
    public Collection<Symbol> getSymbols() {
        return Collections.unmodifiableCollection(symbols.values());
    }

    /**
     * Obtiene todos los símbolos incluyendo scopes padres.
     */
    public Collection<Symbol> getAllSymbols() {
        List<Symbol> allSymbols = new ArrayList<>(symbols.values());
        if (parent != null) {
            allSymbols.addAll(parent.getAllSymbols());
        }
        return allSymbols;
    }

    public String getScopeName() {
        return scopeName;
    }

    public SymbolTable getParent() {
        return parent;
    }

    public List<SymbolTable> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Obtiene la ruta completa del scope.
     */
    public String getFullScopePath() {
        if (parent == null) {
            return scopeName;
        }
        return parent.getFullScopePath() + "." + scopeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SymbolTable[").append(getFullScopePath()).append("]\n");
        for (Symbol symbol : symbols.values()) {
            sb.append("  ").append(symbol).append("\n");
        }
        for (SymbolTable child : children) {
            sb.append(child.toString());
        }
        return sb.toString();
    }
}
