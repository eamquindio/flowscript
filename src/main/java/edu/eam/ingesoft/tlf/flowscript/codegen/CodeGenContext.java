package edu.eam.ingesoft.tlf.flowscript.codegen;

import java.util.*;

/**
 * Contexto de generación de código que mantiene el estado durante la traducción.
 */
public class CodeGenContext {
    private String currentClassName;
    private String currentMethodName;
    private boolean inProcess;
    private String currentProcessName;
    private Set<String> usedVariables;
    private Stack<String> loopLabels;

    public CodeGenContext() {
        this.usedVariables = new HashSet<>();
        this.loopLabels = new Stack<>();
        this.inProcess = false;
    }

    // ========== Getters y Setters ==========

    public String getCurrentClassName() {
        return currentClassName;
    }

    public void setCurrentClassName(String currentClassName) {
        this.currentClassName = currentClassName;
    }

    public String getCurrentMethodName() {
        return currentMethodName;
    }

    public void setCurrentMethodName(String currentMethodName) {
        this.currentMethodName = currentMethodName;
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }

    public String getCurrentProcessName() {
        return currentProcessName;
    }

    public void setCurrentProcessName(String currentProcessName) {
        this.currentProcessName = currentProcessName;
    }

    public Set<String> getUsedVariables() {
        return usedVariables;
    }

    public void setUsedVariables(Set<String> usedVariables) {
        this.usedVariables = usedVariables;
    }

    public void addUsedVariable(String varName) {
        this.usedVariables.add(varName);
    }

    public boolean isVariableDeclared(String varName) {
        return usedVariables.contains(varName);
    }

    public Stack<String> getLoopLabels() {
        return loopLabels;
    }

    public void pushLoopLabel(String label) {
        loopLabels.push(label);
    }

    public String popLoopLabel() {
        return loopLabels.isEmpty() ? null : loopLabels.pop();
    }

    public String peekLoopLabel() {
        return loopLabels.isEmpty() ? null : loopLabels.peek();
    }
}
