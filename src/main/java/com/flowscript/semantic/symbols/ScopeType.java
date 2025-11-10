package com.flowscript.semantic.symbols;

public enum ScopeType {
    GLOBAL,           // Top-level program scope
    FUNCTION,         // Function body scope
    BLOCK,            // Block scope (if, while, for, try)
    PROCESS,          // Process context scope
    PARALLEL_BRANCH   // Parallel gateway branch
}
