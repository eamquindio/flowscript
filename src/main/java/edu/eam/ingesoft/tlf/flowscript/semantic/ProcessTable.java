package edu.eam.ingesoft.tlf.flowscript.semantic;

import java.util.*;

/**
 * Tabla de procesos declarados en el programa.
 */
public class ProcessTable {
    private final Map<String, ProcessSymbol> processes;

    public ProcessTable() {
        this.processes = new LinkedHashMap<>();
    }

    /**
     * Declara un nuevo proceso.
     * @return true si se declaró correctamente, false si ya existía
     */
    public boolean declare(ProcessSymbol process) {
        if (processes.containsKey(process.getName())) {
            return false;
        }
        processes.put(process.getName(), process);
        return true;
    }

    /**
     * Busca un proceso por nombre.
     */
    public ProcessSymbol lookup(String name) {
        return processes.get(name);
    }

    /**
     * Verifica si un proceso está declarado.
     */
    public boolean isDeclared(String name) {
        return processes.containsKey(name);
    }

    /**
     * Obtiene todos los procesos declarados.
     */
    public Collection<ProcessSymbol> getProcesses() {
        return Collections.unmodifiableCollection(processes.values());
    }

    /**
     * Busca una tarea dentro de un proceso específico.
     */
    public TaskSymbol lookupTask(String processName, String taskName) {
        ProcessSymbol process = lookup(processName);
        if (process == null) {
            return null;
        }
        return process.getTaskTable().lookup(taskName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProcessTable:\n");
        for (ProcessSymbol process : processes.values()) {
            sb.append("  ").append(process).append("\n");
            sb.append(process.getTaskTable().toString());
        }
        return sb.toString();
    }
}
