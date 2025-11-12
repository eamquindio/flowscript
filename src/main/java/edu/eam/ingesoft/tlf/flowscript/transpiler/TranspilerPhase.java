package edu.eam.ingesoft.tlf.flowscript.transpiler;

/**
 * Enumeración de las fases del transpiler.
 * Cada fase representa una etapa en el proceso de compilación.
 */
public enum TranspilerPhase {
    /**
     * Análisis léxico - Conversión de texto a tokens.
     */
    LEXICAL_ANALYSIS("Análisis Léxico", "Tokenización del código fuente"),

    /**
     * Análisis sintáctico - Construcción del AST.
     */
    SYNTACTIC_ANALYSIS("Análisis Sintáctico", "Construcción del árbol de sintaxis abstracta"),

    /**
     * Análisis semántico - Verificación de tipos y contexto.
     */
    SEMANTIC_ANALYSIS("Análisis Semántico", "Verificación de tipos y alcance de variables"),

    /**
     * Generación de código intermedio.
     */
    INTERMEDIATE_CODE_GENERATION("Generación de Código Intermedio", "Generación de representación intermedia"),

    /**
     * Optimización de código.
     */
    OPTIMIZATION("Optimización", "Optimización del código generado"),

    /**
     * Generación de código objetivo.
     */
    CODE_GENERATION("Generación de Código", "Generación del código objetivo final");

    private final String name;
    private final String description;

    TranspilerPhase(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
