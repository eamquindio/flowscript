package com.flowscript.semantic.processes;

import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import java.util.*;

public class ProcessTable {
    private final Map<String, ProcessSymbol> processes;

    public ProcessTable() {
        this.processes = new LinkedHashMap<>();
    }

    public void declareProcess(String name, ProcessDeclarationNode node) {
        if (processes.containsKey(name)) {
            throw new IllegalArgumentException(
                "Process '" + name + "' already declared"
            );
        }
        processes.put(name, new ProcessSymbol(name, node));
    }

    public ProcessSymbol lookupProcess(String name) {
        ProcessSymbol process = processes.get(name);
        if (process != null) {
            process.markUsed();
        }
        return process;
    }

    public boolean isProcessDeclared(String name) {
        return processes.containsKey(name);
    }

    public Collection<ProcessSymbol> getAllProcesses() {
        return processes.values();
    }
}
