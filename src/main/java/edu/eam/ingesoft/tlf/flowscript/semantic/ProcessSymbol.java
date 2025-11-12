package edu.eam.ingesoft.tlf.flowscript.semantic;

/**
 * Representa un proceso en la tabla de procesos.
 */
public class ProcessSymbol {
    private final String name;
    private final int line;
    private final int column;
    private final TaskTable taskTable;
    private boolean hasStart;
    private boolean hasEnd;

    public ProcessSymbol(String name, int line, int column) {
        this.name = name;
        this.line = line;
        this.column = column;
        this.taskTable = new TaskTable(name);
        this.hasStart = false;
        this.hasEnd = false;
    }

    public String getName() {
        return name;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public TaskTable getTaskTable() {
        return taskTable;
    }

    public boolean hasStart() {
        return hasStart;
    }

    public void setHasStart(boolean hasStart) {
        this.hasStart = hasStart;
    }

    public boolean hasEnd() {
        return hasEnd;
    }

    public void setHasEnd(boolean hasEnd) {
        this.hasEnd = hasEnd;
    }

    @Override
    public String toString() {
        return String.format("Process{name='%s', line=%d, col=%d, hasStart=%b, hasEnd=%b}",
            name, line, column, hasStart, hasEnd);
    }
}
