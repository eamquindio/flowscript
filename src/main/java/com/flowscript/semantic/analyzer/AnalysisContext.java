package com.flowscript.semantic.analyzer;

/**
 * Enum representing the current context during semantic analysis.
 * Used to track whether we're analyzing global scope, a function, or a process.
 */
public enum AnalysisContext {
    /**
     * Global scope - top-level declarations.
     */
    GLOBAL,

    /**
     * Inside a function body.
     * Restrictions: Cannot use 'entrada' or 'go_to'.
     */
    FUNCTION,

    /**
     * Inside a process.
     * Context: Can use 'entrada' and 'go_to'.
     * Variables persist across tasks in the process context.
     */
    PROCESS
}
