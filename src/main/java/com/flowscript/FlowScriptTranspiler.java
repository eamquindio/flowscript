package com.flowscript;

import com.flowscript.codegen.CodeGenerator;
import com.flowscript.codegen.JavaCodeExecutor;
import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.semantic.analyzer.SemanticAnalyzer;
import com.flowscript.semantic.errors.SemanticAnalysisResult;
import com.flowscript.semantic.errors.SemanticError;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Transpilador completo de FlowScript.
 * Orquesta todas las etapas del proceso de compilación:
 * 1. Análisis Léxico (Lexer)
 * 2. Análisis Sintáctico (Parser)
 * 3. Análisis Semántico (Semantic Analyzer)
 * 4. Generación de Código (Code Generator)
 * 5. Compilación y Ejecución (Java Code Executor)
 */
public class FlowScriptTranspiler {

    private boolean enableSemanticAnalysis = true;
    private boolean enableCodeGeneration = true;
    private boolean enableExecution = false;

    /**
     * Transpila código FlowScript a Java y opcionalmente lo ejecuta.
     *
     * @param sourceCode Código fuente FlowScript
     * @return Resultado del proceso de transpilación
     */
    public TranspilationResult transpile(String sourceCode) {
        TranspilationResult result = new TranspilationResult();
        result.sourceCode = sourceCode;

        try {
            // ==================== FASE 1: ANÁLISIS LÉXICO ====================
            long startLexer = System.currentTimeMillis();
            result.phase = TranspilationPhase.LEXICAL_ANALYSIS;

            Lexer lexer = new Lexer(sourceCode);
            List<Token> tokens = lexer.tokenize();

            result.tokens = tokens;
            result.lexerTime = System.currentTimeMillis() - startLexer;
            result.addMessage("✓ Análisis léxico completado: " + tokens.size() + " tokens");

            // ==================== FASE 2: ANÁLISIS SINTÁCTICO ====================
            long startParser = System.currentTimeMillis();
            result.phase = TranspilationPhase.SYNTACTIC_ANALYSIS;

            Parser parser = new Parser();
            ProgramNode ast = parser.parse(tokens);

            result.ast = ast;
            result.parserTime = System.currentTimeMillis() - startParser;
            result.addMessage("✓ Análisis sintáctico completado: AST generado");

            // ==================== FASE 3: ANÁLISIS SEMÁNTICO ====================
            if (enableSemanticAnalysis) {
                long startSemantic = System.currentTimeMillis();
                result.phase = TranspilationPhase.SEMANTIC_ANALYSIS;

                SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
                SemanticAnalysisResult semanticResult = semanticAnalyzer.analyze(ast);

                result.semanticAnalysisResult = semanticResult;
                result.semanticErrors = semanticResult.getErrors().stream()
                    .map(SemanticError::toString)
                    .collect(Collectors.toList());
                result.semanticTime = System.currentTimeMillis() - startSemantic;

                if (result.semanticErrors.isEmpty()) {
                    result.addMessage("✓ Análisis semántico completado: sin errores");

                    // Si solo estamos analizando (sin generar código), marcar como exitoso
                    if (!enableCodeGeneration) {
                        result.success = true;
                        result.phase = TranspilationPhase.SEMANTIC_ANALYSIS;
                        return result;
                    }
                } else {
                    result.addMessage("⚠ Análisis semántico completado: " +
                                    result.semanticErrors.size() + " errores encontrados");
                    for (String error : result.semanticErrors) {
                        result.addMessage("  - " + error);
                    }

                    // Si hay errores semánticos, detener aquí
                    if (!enableCodeGeneration) {
                        result.success = false;
                        result.phase = TranspilationPhase.SEMANTIC_ANALYSIS;
                        return result;
                    }
                }
            }

            // ==================== FASE 4: GENERACIÓN DE CÓDIGO ====================
            if (enableCodeGeneration) {
                long startCodeGen = System.currentTimeMillis();
                result.phase = TranspilationPhase.CODE_GENERATION;

                CodeGenerator codeGenerator = new CodeGenerator();
                String javaCode = codeGenerator.generate(ast);

                result.generatedCode = javaCode;
                result.codeGenTime = System.currentTimeMillis() - startCodeGen;
                result.addMessage("✓ Generación de código completada: " +
                                javaCode.split("\n").length + " líneas de Java");

                // ==================== FASE 5: COMPILACIÓN ====================
                long startCompilation = System.currentTimeMillis();
                result.phase = TranspilationPhase.COMPILATION;

                JavaCodeExecutor executor = new JavaCodeExecutor();
                JavaCodeExecutor.CompilationResult compResult = executor.compile(javaCode);

                result.compilationResult = compResult;
                result.compilationTime = System.currentTimeMillis() - startCompilation;

                if (compResult.isSuccess()) {
                    result.addMessage("✓ Compilación exitosa: " +
                                    compResult.getBytecodeSize() + " bytes de bytecode");

                    // ==================== FASE 6: EJECUCIÓN (OPCIONAL) ====================
                    if (enableExecution) {
                        long startExecution = System.currentTimeMillis();
                        result.phase = TranspilationPhase.EXECUTION;

                        try {
                            JavaCodeExecutor.ExecutionResult execResult =
                                executor.execute(javaCode, "main");

                            result.executionResult = execResult;
                            result.executionTime = System.currentTimeMillis() - startExecution;

                            if (execResult.isSuccess()) {
                                result.addMessage("✓ Ejecución completada");
                                if (execResult.getReturnValue() != null) {
                                    result.addMessage("  Valor retornado: " +
                                                    execResult.getReturnValue());
                                }
                            } else {
                                result.addMessage("✗ Error en ejecución: " +
                                                execResult.getErrorMessage());
                            }
                        } catch (Exception e) {
                            result.addMessage("⚠ Ejecución no disponible: " + e.getMessage());
                        }
                    }

                    result.success = true;
                    result.phase = TranspilationPhase.COMPLETED;

                } else {
                    result.addMessage("✗ Error de compilación:");
                    result.addMessage(compResult.getErrorMessage());
                    result.success = false;
                    result.phase = TranspilationPhase.COMPILATION;
                }
            }

        } catch (Parser.ParseException e) {
            result.success = false;
            result.error = e.getMessage();
            result.addMessage("✗ Error sintáctico: " + e.getMessage());

        } catch (Exception e) {
            result.success = false;
            result.error = e.getMessage();
            result.addMessage("✗ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        // Calcular tiempo total
        result.totalTime = result.lexerTime + result.parserTime + result.semanticTime +
                          result.codeGenTime + result.compilationTime + result.executionTime;

        return result;
    }

    /**
     * Transpila solo hasta la fase de generación de código (sin compilar ni ejecutar).
     */
    public TranspilationResult transpileToJava(String sourceCode) {
        this.enableExecution = false;
        return transpile(sourceCode);
    }

    /**
     * Transpila, compila y ejecuta el código.
     */
    public TranspilationResult transpileAndExecute(String sourceCode) {
        this.enableExecution = true;
        return transpile(sourceCode);
    }

    /**
     * Solo analiza el código (léxico, sintáctico, semántico) sin generar código.
     */
    public TranspilationResult analyzeOnly(String sourceCode) {
        this.enableCodeGeneration = false;
        this.enableExecution = false;
        TranspilationResult result = transpile(sourceCode);
        this.enableCodeGeneration = true;
        return result;
    }

    // ==================== CONFIGURACIÓN ====================

    public void setEnableSemanticAnalysis(boolean enable) {
        this.enableSemanticAnalysis = enable;
    }

    public void setEnableCodeGeneration(boolean enable) {
        this.enableCodeGeneration = enable;
    }

    public void setEnableExecution(boolean enable) {
        this.enableExecution = enable;
    }

    // ==================== CLASES DE RESULTADO ====================

    /**
     * Fase del proceso de transpilación.
     */
    public enum TranspilationPhase {
        LEXICAL_ANALYSIS("Análisis Léxico"),
        SYNTACTIC_ANALYSIS("Análisis Sintáctico"),
        SEMANTIC_ANALYSIS("Análisis Semántico"),
        CODE_GENERATION("Generación de Código"),
        COMPILATION("Compilación"),
        EXECUTION("Ejecución"),
        COMPLETED("Completado");

        private final String displayName;

        TranspilationPhase(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Resultado del proceso de transpilación.
     */
    public static class TranspilationResult {
        // Estado
        public boolean success;
        public TranspilationPhase phase;
        public String error;

        // Entrada
        public String sourceCode;

        // Fase 1: Léxico
        public List<Token> tokens;
        public long lexerTime;

        // Fase 2: Sintáctico
        public ProgramNode ast;
        public long parserTime;

        // Fase 3: Semántico
        public SemanticAnalysisResult semanticAnalysisResult;
        public List<String> semanticErrors;
        public long semanticTime;

        // Fase 4: Generación de código
        public String generatedCode;
        public long codeGenTime;

        // Fase 5: Compilación
        public JavaCodeExecutor.CompilationResult compilationResult;
        public long compilationTime;

        // Fase 6: Ejecución
        public JavaCodeExecutor.ExecutionResult executionResult;
        public long executionTime;

        // Métricas
        public long totalTime;

        // Mensajes
        private List<String> messages = new ArrayList<>();

        public TranspilationResult() {
            this.success = false;
            this.phase = TranspilationPhase.LEXICAL_ANALYSIS;
            this.semanticErrors = new ArrayList<>();
        }

        public void addMessage(String message) {
            messages.add(message);
        }

        public List<String> getMessages() {
            return messages;
        }

        public String getFormattedMessages() {
            return String.join("\n", messages);
        }

        /**
         * Obtiene un reporte detallado de todas las fases.
         */
        public String getDetailedReport() {
            StringBuilder report = new StringBuilder();

            report.append("=".repeat(60)).append("\n");
            report.append("  REPORTE DE TRANSPILACIÓN FLOWSCRIPT\n");
            report.append("=".repeat(60)).append("\n\n");

            // Estado general
            report.append("Estado: ").append(success ? "✓ ÉXITO" : "✗ ERROR").append("\n");
            report.append("Fase alcanzada: ").append(phase.getDisplayName()).append("\n");
            report.append("Tiempo total: ").append(totalTime).append("ms\n\n");

            // Detalles por fase
            report.append("-".repeat(60)).append("\n");
            report.append("DETALLES POR FASE:\n");
            report.append("-".repeat(60)).append("\n\n");

            if (tokens != null) {
                report.append("1. Análisis Léxico (").append(lexerTime).append("ms)\n");
                report.append("   Tokens generados: ").append(tokens.size()).append("\n\n");
            }

            if (ast != null) {
                report.append("2. Análisis Sintáctico (").append(parserTime).append("ms)\n");
                report.append("   AST generado correctamente\n\n");
            }

            if (semanticAnalysisResult != null || semanticErrors != null) {
                report.append("3. Análisis Semántico (").append(semanticTime).append("ms)\n");
                if (semanticErrors.isEmpty()) {
                    report.append("   Sin errores semánticos\n");
                } else {
                    report.append("   Errores encontrados: ").append(semanticErrors.size()).append("\n");
                    for (String error : semanticErrors) {
                        report.append("   - ").append(error).append("\n");
                    }
                }
                report.append("\n");
            }

            if (generatedCode != null) {
                report.append("4. Generación de Código (").append(codeGenTime).append("ms)\n");
                report.append("   Líneas generadas: ")
                      .append(generatedCode.split("\n").length).append("\n\n");
            }

            if (compilationResult != null) {
                report.append("5. Compilación (").append(compilationTime).append("ms)\n");
                if (compilationResult.isSuccess()) {
                    report.append("   Bytecode: ").append(compilationResult.getBytecodeSize())
                          .append(" bytes\n");
                } else {
                    report.append("   Error:\n");
                    report.append("   ").append(compilationResult.getErrorMessage()).append("\n");
                }
                report.append("\n");
            }

            if (executionResult != null) {
                report.append("6. Ejecución (").append(executionTime).append("ms)\n");
                if (executionResult.isSuccess()) {
                    report.append("   Ejecutado correctamente\n");
                    if (executionResult.getReturnValue() != null) {
                        report.append("   Valor retornado: ")
                              .append(executionResult.getReturnValue()).append("\n");
                    }
                } else {
                    report.append("   Error: ").append(executionResult.getErrorMessage()).append("\n");
                }
                report.append("\n");
            }

            // Mensajes
            if (!messages.isEmpty()) {
                report.append("-".repeat(60)).append("\n");
                report.append("MENSAJES:\n");
                report.append("-".repeat(60)).append("\n");
                for (String message : messages) {
                    report.append(message).append("\n");
                }
            }

            report.append("\n").append("=".repeat(60)).append("\n");

            return report.toString();
        }

        @Override
        public String toString() {
            return getFormattedMessages();
        }
    }
}
