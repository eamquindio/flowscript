package edu.eam.ingesoft.tlf.flowscript.transpiler;

import edu.eam.ingesoft.tlf.flowscript.lexer.*;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.ProgramNode;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.ProgramParser;
import edu.eam.ingesoft.tlf.flowscript.semantic.*;

import java.util.List;

/**
 * Pipeline principal del transpiler que orquesta todas las fases de compilación.
 *
 * Este componente coordina:
 * 1. Análisis Léxico (Lexer)
 * 2. Análisis Sintáctico (Parser)
 * 3. Análisis Semántico (futuro)
 * 4. Generación de Código Intermedio (futuro)
 * 5. Optimización (futuro)
 * 6. Generación de Código Objetivo (futuro)
 */
public class TranspilerPipeline {
    private TranspilerPhase stopAtPhase;
    private boolean verbose;

    public TranspilerPipeline() {
        this.stopAtPhase = TranspilerPhase.CODE_GENERATION;
        this.verbose = false;
    }

    /**
     * Configura en qué fase debe detenerse el transpiler.
     */
    public TranspilerPipeline stopAt(TranspilerPhase phase) {
        this.stopAtPhase = phase;
        return this;
    }

    /**
     * Configura si debe mostrar información detallada.
     */
    public TranspilerPipeline verbose(boolean verbose) {
        this.verbose = verbose;
        return this;
    }

    /**
     * Ejecuta el pipeline completo de transpilación.
     */
    public TranspilerResult transpile(String sourceCode) {
        long startTime = System.currentTimeMillis();
        TranspilerResult.Builder resultBuilder = new TranspilerResult.Builder(sourceCode);

        try {
            // Fase 1: Análisis Léxico
            if (!runLexicalAnalysis(sourceCode, resultBuilder)) {
                return finalize(resultBuilder, startTime);
            }

            if (stopAtPhase == TranspilerPhase.LEXICAL_ANALYSIS) {
                resultBuilder.success(true).completedPhase(TranspilerPhase.LEXICAL_ANALYSIS);
                return finalize(resultBuilder, startTime);
            }

            // Fase 2: Análisis Sintáctico
            if (!runSyntacticAnalysis(resultBuilder)) {
                return finalize(resultBuilder, startTime);
            }

            if (stopAtPhase == TranspilerPhase.SYNTACTIC_ANALYSIS) {
                resultBuilder.success(true).completedPhase(TranspilerPhase.SYNTACTIC_ANALYSIS);
                return finalize(resultBuilder, startTime);
            }

            // Fase 3: Análisis Semántico
            if (!runSemanticAnalysis(resultBuilder)) {
                return finalize(resultBuilder, startTime);
            }

            if (stopAtPhase == TranspilerPhase.SEMANTIC_ANALYSIS) {
                resultBuilder.success(true).completedPhase(TranspilerPhase.SEMANTIC_ANALYSIS);
                return finalize(resultBuilder, startTime);
            }

            // Fase 4: Generación de Código Intermedio (futuro)
            if (stopAtPhase.ordinal() >= TranspilerPhase.INTERMEDIATE_CODE_GENERATION.ordinal()) {
                // TODO: Implementar generación de código intermedio
                log("Generación de código intermedio no implementada aún");
            }

            // Fase 5: Optimización (futuro)
            if (stopAtPhase.ordinal() >= TranspilerPhase.OPTIMIZATION.ordinal()) {
                // TODO: Implementar optimización
                log("Optimización no implementada aún");
            }

            // Fase 6: Generación de Código Objetivo (futuro)
            if (stopAtPhase.ordinal() >= TranspilerPhase.CODE_GENERATION.ordinal()) {
                // TODO: Implementar generación de código
                log("Generación de código no implementada aún");
            }

            // Marcar como exitoso si llegamos aquí
            resultBuilder.success(true)
                .completedPhase(TranspilerPhase.SEMANTIC_ANALYSIS); // Por ahora solo hasta semántico

        } catch (Exception e) {
            resultBuilder.addError(new TranspilerError(
                TranspilerPhase.SYNTACTIC_ANALYSIS,
                "Error inesperado: " + e.getMessage()
            ));
        }

        return finalize(resultBuilder, startTime);
    }

    /**
     * Ejecuta solo el análisis léxico.
     */
    public TranspilerResult tokenize(String sourceCode) {
        return new TranspilerPipeline()
            .stopAt(TranspilerPhase.LEXICAL_ANALYSIS)
            .verbose(this.verbose)
            .transpile(sourceCode);
    }

    /**
     * Ejecuta solo análisis léxico y sintáctico.
     */
    public TranspilerResult parse(String sourceCode) {
        return new TranspilerPipeline()
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .verbose(this.verbose)
            .transpile(sourceCode);
    }

    /**
     * Fase 1: Análisis Léxico
     */
    private boolean runLexicalAnalysis(String sourceCode, TranspilerResult.Builder resultBuilder) {
        log("Iniciando análisis léxico...");
        long startTime = System.currentTimeMillis();

        try {
            Lexer lexer = new Lexer(sourceCode);
            List<Token> tokens = lexer.tokenize();

            long elapsedTime = System.currentTimeMillis() - startTime;
            resultBuilder.tokens(tokens)
                .lexicalAnalysisTime(elapsedTime);

            log("Análisis léxico completado: " + tokens.size() + " tokens en " + elapsedTime + "ms");
            return true;

        } catch (LexerException e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            resultBuilder.lexicalAnalysisTime(elapsedTime);

            TranspilerError error = new TranspilerError(
                TranspilerPhase.LEXICAL_ANALYSIS,
                e.getMessage(),
                e.getLine(),
                e.getColumn(),
                extractSourceLine(sourceCode, e.getLine())
            );

            resultBuilder.addError(error)
                .completedPhase(TranspilerPhase.LEXICAL_ANALYSIS);

            log("Error léxico: " + e.getMessage());
            return false;

        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            resultBuilder.lexicalAnalysisTime(elapsedTime);

            resultBuilder.addError(new TranspilerError(
                TranspilerPhase.LEXICAL_ANALYSIS,
                "Error inesperado en análisis léxico: " + e.getMessage()
            ));

            log("Error inesperado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Fase 2: Análisis Sintáctico
     */
    private boolean runSyntacticAnalysis(TranspilerResult.Builder resultBuilder) {
        log("Iniciando análisis sintáctico...");
        long startTime = System.currentTimeMillis();

        try {
            // Filtrar tokens ignorables para el parser
            List<Token> allTokens = resultBuilder.build().getTokens();
            List<Token> filteredTokens = allTokens.stream()
                .filter(t -> !t.getType().isIgnorable())
                .toList();

            ParserContext context = new ParserContext(filteredTokens);
            ProgramParser parser = new ProgramParser();
            ProgramNode ast = parser.parse(context);

            long elapsedTime = System.currentTimeMillis() - startTime;
            resultBuilder.ast(ast)
                .syntacticAnalysisTime(elapsedTime);

            log("Análisis sintáctico completado en " + elapsedTime + "ms");
            log("AST construido: " + ast.getDeclarations().size() + " declaraciones");

            return true;

        } catch (ParserException e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            resultBuilder.syntacticAnalysisTime(elapsedTime);

            TranspilerError error = new TranspilerError(
                TranspilerPhase.SYNTACTIC_ANALYSIS,
                e.getMessage(),
                e.getToken() != null ? e.getToken().getLine() : -1,
                e.getToken() != null ? e.getToken().getColumn() : -1,
                extractSourceLine(resultBuilder.build().getSourceCode(),
                    e.getToken() != null ? e.getToken().getLine() : -1)
            );

            resultBuilder.addError(error)
                .completedPhase(TranspilerPhase.SYNTACTIC_ANALYSIS);

            log("Error sintáctico: " + e.getMessage());
            return false;

        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            resultBuilder.syntacticAnalysisTime(elapsedTime);

            resultBuilder.addError(new TranspilerError(
                TranspilerPhase.SYNTACTIC_ANALYSIS,
                "Error inesperado en análisis sintáctico: " + e.getMessage()
            ));

            log("Error inesperado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Fase 3: Análisis Semántico
     */
    private boolean runSemanticAnalysis(TranspilerResult.Builder resultBuilder) {
        log("Iniciando análisis semántico...");
        long startTime = System.currentTimeMillis();

        try {
            // Obtener el AST del resultado
            ProgramNode ast = resultBuilder.build().getAST();
            if (ast == null) {
                resultBuilder.addError(new TranspilerError(
                    TranspilerPhase.SEMANTIC_ANALYSIS,
                    "No hay AST disponible para análisis semántico"
                ));
                return false;
            }

            // Crear el analizador semántico
            SemanticAnalyzer analyzer = new SemanticAnalyzer();

            // Realizar el análisis
            analyzer.analyze(ast, resultBuilder.build().getSourceCode());

            long elapsedTime = System.currentTimeMillis() - startTime;
            resultBuilder.semanticAnalysisTime(elapsedTime);

            // Guardar las tablas de símbolos
            resultBuilder.symbolTable(analyzer.getGlobalSymbolTable())
                .functionTable(analyzer.getFunctionTable())
                .processTable(analyzer.getProcessTable())
                .semanticErrors(analyzer.getErrors());

            // Verificar si hay errores semánticos
            if (analyzer.hasErrors()) {
                // Convertir errores semánticos a errores de transpilador
                for (SemanticError semError : analyzer.getErrors()) {
                    TranspilerError transpilerError = new TranspilerError(
                        TranspilerPhase.SEMANTIC_ANALYSIS,
                        semError.getMessage(),
                        semError.getLine(),
                        semError.getColumn(),
                        semError.getSourceSnippet()
                    );
                    resultBuilder.addError(transpilerError);
                }

                resultBuilder.completedPhase(TranspilerPhase.SEMANTIC_ANALYSIS);
                log("Análisis semántico completado con " + analyzer.getErrors().size() + " errores en " + elapsedTime + "ms");
                return false;
            }

            log("Análisis semántico completado exitosamente en " + elapsedTime + "ms");
            return true;

        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            resultBuilder.semanticAnalysisTime(elapsedTime);

            resultBuilder.addError(new TranspilerError(
                TranspilerPhase.SEMANTIC_ANALYSIS,
                "Error inesperado en análisis semántico: " + e.getMessage()
            ));

            log("Error inesperado en análisis semántico: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Finaliza el resultado calculando tiempo total.
     */
    private TranspilerResult finalize(TranspilerResult.Builder resultBuilder, long startTime) {
        long totalTime = System.currentTimeMillis() - startTime;
        resultBuilder.totalTime(totalTime);

        TranspilerResult result = resultBuilder.build();
        log("Transpilación finalizada en " + totalTime + "ms");
        log("Resultado: " + (result.isSuccess() ? "ÉXITO" : "FALLO"));

        return result;
    }

    /**
     * Extrae una línea del código fuente.
     */
    private String extractSourceLine(String sourceCode, int lineNumber) {
        if (sourceCode == null || lineNumber < 1) return null;

        String[] lines = sourceCode.split("\n");
        if (lineNumber <= lines.length) {
            return lines[lineNumber - 1];
        }

        return null;
    }

    /**
     * Imprime un mensaje de log si verbose está activado.
     */
    private void log(String message) {
        if (verbose) {
            System.out.println("[Transpiler] " + message);
        }
    }

    /**
     * Crea una instancia del pipeline con configuración por defecto.
     */
    public static TranspilerPipeline create() {
        return new TranspilerPipeline();
    }

    /**
     * Crea una instancia del pipeline en modo verbose.
     */
    public static TranspilerPipeline createVerbose() {
        return new TranspilerPipeline().verbose(true);
    }
}
