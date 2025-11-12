package edu.eam.ingesoft.tlf.flowscript.semantic;

/**
 * Representa un símbolo (variable) en la tabla de símbolos.
 */
public class Symbol {
    private final String name;
    private final String type;
    private final int line;
    private final int column;
    private final String scope;
    private boolean initialized;
    private boolean used;

    public Symbol(String name, String type, int line, int column, String scope) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.column = column;
        this.scope = scope;
        this.initialized = false;
        this.used = false;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getScope() {
        return scope;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return String.format("Symbol{name='%s', type='%s', scope='%s', line=%d, col=%d}",
            name, type, scope, line, column);
    }
}
