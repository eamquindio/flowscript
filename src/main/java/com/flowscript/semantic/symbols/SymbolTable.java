package com.flowscript.semantic.symbols;

import com.flowscript.semantic.types.Type;
import com.flowscript.sintactic.ast.ASTNode;
import java.util.*;

public class SymbolTable {
    private final Deque<Scope> scopeStack;

    public SymbolTable() {
        this.scopeStack = new ArrayDeque<>();
        // Start with global scope
        scopeStack.push(new Scope(ScopeType.GLOBAL, null));
    }

    public void enterScope(ScopeType type) {
        Scope parent = scopeStack.peek();
        scopeStack.push(new Scope(type, parent));
    }

    public void exitScope() {
        if (scopeStack.size() <= 1) {
            throw new IllegalStateException("Cannot exit global scope");
        }
        scopeStack.pop();
    }

    public Scope getCurrentScope() {
        return scopeStack.peek();
    }

    public void declareVariable(String name, Type type, ASTNode location) {
        declareVariable(name, type, location, false, false);
    }

    public void declareVariable(String name, Type type, ASTNode location,
                               boolean isConstant, boolean isParameter) {
        Scope current = scopeStack.peek();
        VariableSymbol symbol = new VariableSymbol(name, type, location, isConstant, isParameter);
        current.addSymbol(symbol);
    }

    public Symbol lookupVariable(String name) {
        // Search from innermost to outermost scope
        for (Scope scope : scopeStack) {
            if (scope.hasSymbol(name)) {
                Symbol symbol = scope.getSymbol(name);
                symbol.markUsed();
                return symbol;
            }
        }
        return null;
    }

    public boolean isVariableDeclaredInCurrentScope(String name) {
        return scopeStack.peek().hasSymbol(name);
    }

    public List<Symbol> getUnusedSymbols() {
        List<Symbol> unused = new ArrayList<>();
        for (Scope scope : scopeStack) {
            for (Symbol symbol : scope.getAllSymbols()) {
                if (!symbol.isUsed() && symbol instanceof VariableSymbol) {
                    unused.add(symbol);
                }
            }
        }
        return unused;
    }
}
