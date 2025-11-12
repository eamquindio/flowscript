package edu.eam.ingesoft.tlf.flowscript.gui.services;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.ProgramNode;
import edu.eam.ingesoft.tlf.flowscript.semantic.*;
import edu.eam.ingesoft.tlf.flowscript.transpiler.*;

import java.util.List;

/**
 * Servicio que actúa como fachada entre la GUI y el backend del transpiler.
 * Orquesta todas las fases de compilación a través del TranspilerPipeline.
 */
public class CompilerService {
    private final TranspilerPipeline transpiler;
    private TranspilerResult lastResult;

    public CompilerService() {
        this.transpiler = TranspilerPipeline.create();
        this.lastResult = null;
    }

    /**
     * Compila el código fuente completo (todas las fases implementadas).
     */
    public boolean compile(String sourceCode) {
        this.lastResult = transpiler
            .stopAt(TranspilerPhase.SEMANTIC_ANALYSIS) // Incluye análisis semántico
            .transpile(sourceCode);

        return lastResult.isSuccess();
    }

    /**
     * Realiza solo el análisis léxico.
     */
    public boolean tokenize(String sourceCode) {
        this.lastResult = transpiler
            .stopAt(TranspilerPhase.LEXICAL_ANALYSIS)
            .transpile(sourceCode);

        return lastResult.isSuccess();
    }

    /**
     * Realiza análisis léxico y sintáctico.
     */
    public boolean parse(String sourceCode) {
        this.lastResult = transpiler
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        return lastResult.isSuccess();
    }

    /**
     * Obtiene los tokens del último resultado.
     */
    public List<Token> getTokens() {
        return lastResult != null ? lastResult.getTokens() : null;
    }

    /**
     * Obtiene el AST del último resultado.
     */
    public ProgramNode getAST() {
        return lastResult != null ? lastResult.getAST() : null;
    }

    /**
     * Obtiene el último error formateado.
     */
    public String getLastError() {
        if (lastResult == null || !lastResult.hasErrors()) {
            return null;
        }

        return lastResult.getFormattedErrors();
    }

    /**
     * Obtiene el último resultado completo del transpiler.
     */
    public TranspilerResult getLastResult() {
        return lastResult;
    }

    /**
     * Verifica si hay tokens disponibles.
     */
    public boolean hasTokens() {
        return lastResult != null &&
               lastResult.getTokens() != null &&
               !lastResult.getTokens().isEmpty();
    }

    /**
     * Verifica si hay AST disponible.
     */
    public boolean hasAST() {
        return lastResult != null && lastResult.getAST() != null;
    }

    /**
     * Obtiene un resumen del último resultado.
     */
    public String getSummary() {
        return lastResult != null ? lastResult.getSummary() : "Sin resultados";
    }

    /**
     * Obtiene las métricas de tiempo del último resultado.
     */
    public String getTimeMetrics() {
        if (lastResult == null) return "Sin métricas";

        StringBuilder sb = new StringBuilder();
        sb.append("Tiempos de ejecución:\n");
        sb.append("- Análisis léxico: ").append(lastResult.getLexicalAnalysisTime()).append(" ms\n");
        sb.append("- Análisis sintáctico: ").append(lastResult.getSyntacticAnalysisTime()).append(" ms\n");
        sb.append("- Total: ").append(lastResult.getTotalTime()).append(" ms");

        return sb.toString();
    }

    /**
     * Verifica si la última compilación fue exitosa.
     */
    public boolean isLastCompilationSuccessful() {
        return lastResult != null && lastResult.isSuccess();
    }

    /**
     * Obtiene advertencias del último resultado.
     */
    public List<String> getWarnings() {
        return lastResult != null ? lastResult.getWarnings() : List.of();
    }

    /**
     * Verifica si hay advertencias.
     */
    public boolean hasWarnings() {
        return lastResult != null && lastResult.hasWarnings();
    }

    /**
     * Limpia todos los resultados.
     */
    public void clear() {
        this.lastResult = null;
    }

    // ========== Métodos para Análisis Semántico ==========

    /**
     * Obtiene la tabla de símbolos global del último resultado.
     */
    public SymbolTable getSymbolTable() {
        return lastResult != null ? lastResult.getSymbolTable() : null;
    }

    /**
     * Obtiene la tabla de funciones del último resultado.
     */
    public FunctionTable getFunctionTable() {
        return lastResult != null ? lastResult.getFunctionTable() : null;
    }

    /**
     * Obtiene la tabla de procesos del último resultado.
     */
    public ProcessTable getProcessTable() {
        return lastResult != null ? lastResult.getProcessTable() : null;
    }

    /**
     * Obtiene los errores semánticos del último resultado.
     */
    public List<SemanticError> getSemanticErrors() {
        return lastResult != null ? lastResult.getSemanticErrors() : null;
    }

    /**
     * Verifica si hay errores semánticos.
     */
    public boolean hasSemanticErrors() {
        return lastResult != null && lastResult.hasSemanticErrors();
    }

    /**
     * Verifica si hay tablas semánticas disponibles.
     */
    public boolean hasSemanticTables() {
        return lastResult != null &&
               (lastResult.getSymbolTable() != null ||
                lastResult.getFunctionTable() != null ||
                lastResult.getProcessTable() != null);
    }
}
