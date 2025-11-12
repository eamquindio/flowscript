package edu.eam.ingesoft.tlf.flowscript.semantic;

/**
 * Representa una tarea dentro de un proceso.
 */
public class TaskSymbol {
    private final String name;
    private final String processName;
    private final int line;
    private final int column;
    private final TaskType taskType;
    private boolean referenced; // true si algún ir_a apunta a esta tarea

    public enum TaskType {
        TASK,           // tarea normal
        GATEWAY,        // gateway exclusivo (condicional)
        PARALLEL_GATEWAY, // gateway paralelo
        START,          // elemento inicio
        END             // elemento fin
    }

    public TaskSymbol(String name, String processName, TaskType taskType, int line, int column) {
        this.name = name;
        this.processName = processName;
        this.taskType = taskType;
        this.line = line;
        this.column = column;
        this.referenced = false;
    }

    public String getName() {
        return name;
    }

    public String getProcessName() {
        return processName;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public boolean isReferenced() {
        return referenced;
    }

    public void setReferenced(boolean referenced) {
        this.referenced = referenced;
    }

    /**
     * Obtiene el nombre completo de la tarea (proceso.tarea).
     */
    public String getFullName() {
        return processName + "." + name;
    }

    @Override
    public String toString() {
        return String.format("Task{name='%s', process='%s', type=%s, line=%d, col=%d}",
            name, processName, taskType, line, column);
    }
}
