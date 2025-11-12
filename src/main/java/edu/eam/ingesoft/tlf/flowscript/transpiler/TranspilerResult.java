package edu.eam.ingesoft.tlf.flowscript.transpiler;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.ProgramNode;
import edu.eam.ingesoft.tlf.flowscript.semantic.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resultado completo del proceso de transpilación.
 * Contiene información de todas las fases ejecutadas.
 */
public class TranspilerResult {
    private final String sourceCode;
    private final boolean success;
    private final TranspilerPhase completedPhase;
    private final List<TranspilerError> errors;
    private final List<String> warnings;

    // Resultados de cada fase
    private List<Token> tokens;
    private ProgramNode ast;
    private String intermediateCode;
    private String targetCode;

    // Resultados del análisis semántico
    private SymbolTable symbolTable;
    private FunctionTable functionTable;
    private ProcessTable processTable;
    private List<SemanticError> semanticErrors;

    // Métricas
    private long lexicalAnalysisTime;
    private long syntacticAnalysisTime;
    private long semanticAnalysisTime;
    private long codeGenerationTime;
    private long totalTime;

    private TranspilerResult(Builder builder) {
        this.sourceCode = builder.sourceCode;
        this.success = builder.success;
        this.completedPhase = builder.completedPhase;
        this.errors = Collections.unmodifiableList(new ArrayList<>(builder.errors));
        this.warnings = Collections.unmodifiableList(new ArrayList<>(builder.warnings));
        this.tokens = builder.tokens;
        this.ast = builder.ast;
        this.intermediateCode = builder.intermediateCode;
        this.targetCode = builder.targetCode;
        this.symbolTable = builder.symbolTable;
        this.functionTable = builder.functionTable;
        this.processTable = builder.processTable;
        this.semanticErrors = builder.semanticErrors != null ?
            Collections.unmodifiableList(new ArrayList<>(builder.semanticErrors)) : Collections.emptyList();
        this.lexicalAnalysisTime = builder.lexicalAnalysisTime;
        this.syntacticAnalysisTime = builder.syntacticAnalysisTime;
        this.semanticAnalysisTime = builder.semanticAnalysisTime;
        this.codeGenerationTime = builder.codeGenerationTime;
        this.totalTime = builder.totalTime;
    }

    // Getters
    public String getSourceCode() {
        return sourceCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }

    public TranspilerPhase getCompletedPhase() {
        return completedPhase;
    }

    public List<TranspilerError> getErrors() {
        return errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public ProgramNode getAST() {
        return ast;
    }

    public String getIntermediateCode() {
        return intermediateCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public long getLexicalAnalysisTime() {
        return lexicalAnalysisTime;
    }

    public long getSyntacticAnalysisTime() {
        return syntacticAnalysisTime;
    }

    public long getSemanticAnalysisTime() {
        return semanticAnalysisTime;
    }

    public long getCodeGenerationTime() {
        return codeGenerationTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public FunctionTable getFunctionTable() {
        return functionTable;
    }

    public ProcessTable getProcessTable() {
        return processTable;
    }

    public List<SemanticError> getSemanticErrors() {
        return semanticErrors;
    }

    public boolean hasSemanticErrors() {
        return semanticErrors != null && !semanticErrors.isEmpty();
    }

    /**
     * Verifica si una fase específica fue completada.
     */
    public boolean completedPhase(TranspilerPhase phase) {
        if (completedPhase == null) return false;
        return completedPhase.ordinal() >= phase.ordinal();
    }

    /**
     * Obtiene un resumen del resultado.
     */
    public String getSummary() {
        StringBuilder sb = new StringBuilder();

        if (success) {
            sb.append("✓ Compilación exitosa");
            sb.append("\nFase completada: ").append(completedPhase.getName());
        } else {
            sb.append("✗ Compilación fallida");
            sb.append("\nFase: ").append(completedPhase != null ? completedPhase.getName() : "Ninguna");
            sb.append("\nErrores: ").append(errors.size());
        }

        if (hasWarnings()) {
            sb.append("\nAdvertencias: ").append(warnings.size());
        }

        sb.append("\nTiempo total: ").append(totalTime).append(" ms");

        return sb.toString();
    }

    /**
     * Obtiene todos los errores formateados.
     */
    public String getFormattedErrors() {
        if (!hasErrors()) return "";

        StringBuilder sb = new StringBuilder();
        sb.append("Errores encontrados:\n");
        sb.append("=".repeat(50)).append("\n\n");

        for (int i = 0; i < errors.size(); i++) {
            sb.append(i + 1).append(". ");
            sb.append(errors.get(i).format());
            sb.append("\n\n");
        }

        return sb.toString();
    }

    /**
     * Builder para crear TranspilerResult.
     */
    public static class Builder {
        private String sourceCode;
        private boolean success;
        private TranspilerPhase completedPhase;
        private final List<TranspilerError> errors = new ArrayList<>();
        private final List<String> warnings = new ArrayList<>();

        private List<Token> tokens;
        private ProgramNode ast;
        private String intermediateCode;
        private String targetCode;

        private SymbolTable symbolTable;
        private FunctionTable functionTable;
        private ProcessTable processTable;
        private List<SemanticError> semanticErrors;

        private long lexicalAnalysisTime;
        private long syntacticAnalysisTime;
        private long semanticAnalysisTime;
        private long codeGenerationTime;
        private long totalTime;

        public Builder(String sourceCode) {
            this.sourceCode = sourceCode;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder completedPhase(TranspilerPhase phase) {
            this.completedPhase = phase;
            return this;
        }

        public Builder addError(TranspilerError error) {
            this.errors.add(error);
            this.success = false;
            return this;
        }

        public Builder addWarning(String warning) {
            this.warnings.add(warning);
            return this;
        }

        public Builder tokens(List<Token> tokens) {
            this.tokens = tokens;
            return this;
        }

        public Builder ast(ProgramNode ast) {
            this.ast = ast;
            return this;
        }

        public Builder intermediateCode(String code) {
            this.intermediateCode = code;
            return this;
        }

        public Builder targetCode(String code) {
            this.targetCode = code;
            return this;
        }

        public Builder symbolTable(SymbolTable symbolTable) {
            this.symbolTable = symbolTable;
            return this;
        }

        public Builder functionTable(FunctionTable functionTable) {
            this.functionTable = functionTable;
            return this;
        }

        public Builder processTable(ProcessTable processTable) {
            this.processTable = processTable;
            return this;
        }

        public Builder semanticErrors(List<SemanticError> semanticErrors) {
            this.semanticErrors = semanticErrors;
            return this;
        }

        public Builder lexicalAnalysisTime(long time) {
            this.lexicalAnalysisTime = time;
            return this;
        }

        public Builder syntacticAnalysisTime(long time) {
            this.syntacticAnalysisTime = time;
            return this;
        }

        public Builder semanticAnalysisTime(long time) {
            this.semanticAnalysisTime = time;
            return this;
        }

        public Builder codeGenerationTime(long time) {
            this.codeGenerationTime = time;
            return this;
        }

        public Builder totalTime(long time) {
            this.totalTime = time;
            return this;
        }

        public TranspilerResult build() {
            return new TranspilerResult(this);
        }
    }
}
