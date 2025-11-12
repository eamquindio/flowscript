package edu.eam.ingesoft.tlf.flowscript.transpiler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para TranspilerPipeline.
 */
public class TranspilerPipelineTest {

    @Test
    public void testLexicalAnalysisSuccess() {
        String sourceCode = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }
            """;

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.LEXICAL_ANALYSIS)
            .transpile(sourceCode);

        assertTrue(result.isSuccess(), "Lexical analysis should succeed");
        assertNotNull(result.getTokens(), "Tokens should not be null");
        assertFalse(result.getTokens().isEmpty(), "Should have tokens");
        assertEquals(TranspilerPhase.LEXICAL_ANALYSIS, result.getCompletedPhase());
    }

    @Test
    public void testLexicalAnalysisFailure() {
        String sourceCode = "funcion test() { @ }"; // @ is invalid character

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.LEXICAL_ANALYSIS)
            .transpile(sourceCode);

        assertFalse(result.isSuccess(), "Lexical analysis should fail");
        assertTrue(result.hasErrors(), "Should have errors");
        assertEquals(1, result.getErrors().size(), "Should have 1 error");
        assertEquals(TranspilerPhase.LEXICAL_ANALYSIS, result.getErrors().get(0).getPhase());
    }

    @Test
    public void testSyntacticAnalysisSuccess() {
        String sourceCode = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }
            """;

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        assertTrue(result.isSuccess(), "Syntactic analysis should succeed");
        assertNotNull(result.getTokens(), "Tokens should not be null");
        assertNotNull(result.getAST(), "AST should not be null");
        assertEquals(TranspilerPhase.SYNTACTIC_ANALYSIS, result.getCompletedPhase());
    }

    @Test
    public void testSyntacticAnalysisFailure() {
        String sourceCode = "funcion test() {"; // Missing closing brace

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        assertFalse(result.isSuccess(), "Syntactic analysis should fail");
        assertTrue(result.hasErrors(), "Should have errors");
        assertEquals(TranspilerPhase.SYNTACTIC_ANALYSIS, result.getErrors().get(0).getPhase());
    }

    @Test
    public void testIfStatementParsing() {
        String sourceCode = """
            funcion validar(valor: entero) -> booleano {
                si (valor > 0) {
                    retornar verdadero
                } sino {
                    retornar falso
                }
            }
            """;

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        if (!result.isSuccess()) {
            System.out.println("If statement parsing failed:");
            System.out.println(result.getFormattedErrors());
        }

        assertTrue(result.isSuccess(), "If statement parsing should succeed");
        assertNotNull(result.getAST(), "AST should not be null");
        assertEquals(1, result.getAST().getDeclarations().size(), "Should have 1 declaration");
    }

    @Test
    public void testTimingMetrics() {
        String sourceCode = """
            funcion test() -> entero {
                retornar 42
            }
            """;

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        assertTrue(result.isSuccess());
        assertTrue(result.getLexicalAnalysisTime() >= 0, "Lexical time should be non-negative");
        assertTrue(result.getSyntacticAnalysisTime() >= 0, "Syntactic time should be non-negative");
        assertTrue(result.getTotalTime() >= 0, "Total time should be non-negative");
        assertTrue(result.getTotalTime() >= result.getLexicalAnalysisTime() + result.getSyntacticAnalysisTime(),
            "Total time should be at least the sum of phase times");
    }

    @Test
    public void testErrorFormatting() {
        String sourceCode = "funcion test() { @ }";

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.LEXICAL_ANALYSIS)
            .transpile(sourceCode);

        assertFalse(result.isSuccess());
        String formattedErrors = result.getFormattedErrors();
        assertNotNull(formattedErrors);
        assertFalse(formattedErrors.isEmpty());
        assertTrue(formattedErrors.contains("Análisis Léxico"), "Should contain phase name");
    }

    @Test
    public void testResultSummary() {
        String sourceCode = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }
            """;

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        String summary = result.getSummary();
        assertNotNull(summary);
        assertFalse(summary.isEmpty());
        assertTrue(summary.contains("exitosa") || summary.contains("fallida"));
        assertTrue(summary.contains("ms"), "Should contain timing information");
    }

    @Test
    public void testMultipleDeclarations() {
        String sourceCode = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }

            funcion resta(a: entero, b: entero) -> entero {
                retornar a - b
            }
            """;

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        assertTrue(result.isSuccess());
        assertEquals(2, result.getAST().getDeclarations().size(), "Should have 2 declarations");
    }

    @Test
    public void testVerboseMode() {
        String sourceCode = """
            funcion test() -> entero {
                retornar 1
            }
            """;

        // Verbose mode just prints to stdout, we verify it doesn't crash
        TranspilerPipeline pipeline = TranspilerPipeline.createVerbose();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testCompletedPhaseCheck() {
        String sourceCode = """
            funcion test() -> entero {
                retornar 1
            }
            """;

        TranspilerPipeline pipeline = TranspilerPipeline.create();
        TranspilerResult result = pipeline
            .stopAt(TranspilerPhase.SYNTACTIC_ANALYSIS)
            .transpile(sourceCode);

        assertTrue(result.completedPhase(TranspilerPhase.LEXICAL_ANALYSIS));
        assertTrue(result.completedPhase(TranspilerPhase.SYNTACTIC_ANALYSIS));
        assertFalse(result.completedPhase(TranspilerPhase.SEMANTIC_ANALYSIS));
    }
}
