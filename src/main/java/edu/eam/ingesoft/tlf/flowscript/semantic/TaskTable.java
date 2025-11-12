package edu.eam.ingesoft.tlf.flowscript.semantic;

import java.util.*;

/**
 * Tabla de tareas dentro de un proceso específico.
 */
public class TaskTable {
    private final Map<String, TaskSymbol> tasks;
    private final String processName;

    public TaskTable(String processName) {
        this.processName = processName;
        this.tasks = new LinkedHashMap<>();
    }

    /**
     * Declara una nueva tarea.
     * @return true si se declaró correctamente, false si ya existía
     */
    public boolean declare(TaskSymbol task) {
        if (tasks.containsKey(task.getName())) {
            return false;
        }
        tasks.put(task.getName(), task);
        return true;
    }

    /**
     * Busca una tarea por nombre.
     */
    public TaskSymbol lookup(String name) {
        return tasks.get(name);
    }

    /**
     * Verifica si una tarea está declarada.
     */
    public boolean isDeclared(String name) {
        return tasks.containsKey(name);
    }

    /**
     * Obtiene todas las tareas declaradas.
     */
    public Collection<TaskSymbol> getTasks() {
        return Collections.unmodifiableCollection(tasks.values());
    }

    /**
     * Marca una tarea como referenciada (algún ir_a apunta a ella).
     */
    public void markAsReferenced(String taskName) {
        TaskSymbol task = lookup(taskName);
        if (task != null) {
            task.setReferenced(true);
        }
    }

    /**
     * Obtiene tareas no referenciadas (posibles tareas inalcanzables).
     */
    public List<TaskSymbol> getUnreferencedTasks() {
        List<TaskSymbol> unreferenced = new ArrayList<>();
        for (TaskSymbol task : tasks.values()) {
            if (!task.isReferenced() && task.getTaskType() != TaskSymbol.TaskType.START) {
                unreferenced.add(task);
            }
        }
        return unreferenced;
    }

    public String getProcessName() {
        return processName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TaskTable[").append(processName).append("]:\n");
        for (TaskSymbol task : tasks.values()) {
            sb.append("  ").append(task).append("\n");
        }
        return sb.toString();
    }
}
