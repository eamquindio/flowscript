package com.flowscript.semantic.symbols;

import java.util.*;

public class Scope {
    private final ScopeType type;
    private final Scope parent;
    private final Map<String, Symbol> symbols;

    public Scope(ScopeType type, Scope parent) {
        this.type = type;
        this.parent = parent;
        this.symbols = new LinkedHashMap<>();
    }

    public ScopeType getType() { return type; }
    public Scope getParent() { return parent; }

    public void addSymbol(Symbol symbol) throws IllegalArgumentException {
        if (symbols.containsKey(symbol.getName())) {
            throw new IllegalArgumentException(
                "Symbol '" + symbol.getName() + "' already declared in this scope"
            );
        }
        symbols.put(symbol.getName(), symbol);
    }

    public boolean hasSymbol(String name) {
        return symbols.containsKey(name);
    }

    public Symbol getSymbol(String name) {
        return symbols.get(name);
    }

    public Collection<Symbol> getAllSymbols() {
        return symbols.values();
    }
}
