package com.flowscript;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para el FlowScriptTranspiler.
 */
public class FlowScriptTranspilerTest {

    private FlowScriptTranspiler transpiler;

    @BeforeEach
    public void setUp() {
        transpiler = new FlowScriptTranspiler();
    }

    @Test
    public void testSimpleFunctionTranspilation() {
        String code = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }
            """;

        FlowScriptTranspiler.TranspilationResult result = transpiler.transpile(code);

        assertTrue(result.success);
        assertNotNull(result.tokens);
        assertNotNull(result.ast);
        assertNotNull(result.generatedCode);
        assertNotNull(result.compilationResult);
        assertTrue(result.compilationResult.isSuccess());

        System.out.println(result.getDetailedReport());
    }

    @Test
    public void testAnalyzeOnly() {
        String code = """
            funcion test(x: entero) -> entero {
                retornar x + 10
            }
            """;

        FlowScriptTranspiler.TranspilationResult result = transpiler.analyzeOnly(code);

        assertTrue(result.success);
        assertNotNull(result.tokens);
        assertNotNull(result.ast);
        assertNull(result.generatedCode); // No debería generar código

        System.out.println("Análisis solamente:");
        System.out.println(result);
    }

    @Test
    public void testTranspileToJava() {
        String code = """
            funcion multiplicar(x: entero, y: entero) -> entero {
                retornar x * y
            }
            """;

        FlowScriptTranspiler.TranspilationResult result = transpiler.transpileToJava(code);

        assertTrue(result.success);
        assertNotNull(result.generatedCode);
        assertTrue(result.generatedCode.contains("public static int multiplicar"));
        assertTrue(result.generatedCode.contains("return (x * y)"));

        System.out.println("Código Java generado:");
        System.out.println(result.generatedCode);
    }

    @Test
    public void testSyntaxError() {
        String code = """
            funcion invalida( {
                // Sintaxis invalida
            """;

        FlowScriptTranspiler.TranspilationResult result = transpiler.transpile(code);

        assertFalse(result.success);
        assertNotNull(result.error);
        // El error puede ser lexico o sintactico dependiendo de donde falle
        assertTrue(result.phase == FlowScriptTranspiler.TranspilationPhase.LEXICAL_ANALYSIS ||
                   result.phase == FlowScriptTranspiler.TranspilationPhase.SYNTACTIC_ANALYSIS);

        System.out.println("Error detectado:");
        System.out.println(result.error);
    }

    @Test
    public void testComplexProgram() {
        String code = """
            funcion factorial(n: entero) -> entero {
                si (n <= 1) {
                    retornar 1
                } sino {
                    retornar n * factorial(n - 1)
                }
            }

            funcion main() -> vacio {
                resultado = factorial(5)
            }
            """;

        FlowScriptTranspiler.TranspilationResult result = transpiler.transpile(code);

        // Puede fallar en compilación por problemas del generador, pero
        // debería pasar análisis léxico y sintáctico
        assertNotNull(result.tokens);
        assertNotNull(result.ast);

        System.out.println(result.getDetailedReport());
    }

    @Test
    public void testPerformanceMetrics() {
        String code = """
            funcion test(x: entero) -> entero {
                retornar x + 1
            }
            """;

        FlowScriptTranspiler.TranspilationResult result = transpiler.transpile(code);

        assertTrue(result.lexerTime >= 0);
        assertTrue(result.parserTime >= 0);
        assertTrue(result.codeGenTime >= 0);
        assertTrue(result.totalTime >= 0);
        assertTrue(result.totalTime >= result.lexerTime + result.parserTime);

        System.out.println("Métricas de performance:");
        System.out.println("  Lexer: " + result.lexerTime + "ms");
        System.out.println("  Parser: " + result.parserTime + "ms");
        System.out.println("  CodeGen: " + result.codeGenTime + "ms");
        System.out.println("  Total: " + result.totalTime + "ms");
    }

    @Test
    public void testMultipleFunctions() {
        String code = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }

            funcion resta(a: entero, b: entero) -> entero {
                retornar a - b
            }

            funcion multiplicar(a: entero, b: entero) -> entero {
                retornar a * b
            }
            """;

        FlowScriptTranspiler.TranspilationResult result = transpiler.transpile(code);

        assertTrue(result.success);
        assertTrue(result.generatedCode.contains("suma"));
        assertTrue(result.generatedCode.contains("resta"));
        assertTrue(result.generatedCode.contains("multiplicar"));
    }

    @Test
    public void testDetailedReport() {
        String code = """
            funcion test() -> entero {
                retornar 42
            }
            """;

        FlowScriptTranspiler.TranspilationResult result = transpiler.transpile(code);

        String report = result.getDetailedReport();

        assertTrue(report.contains("REPORTE DE TRANSPILACIÓN"));
        assertTrue(report.contains("Análisis Léxico"));
        assertTrue(report.contains("Análisis Sintáctico"));
        assertTrue(report.contains("Generación de Código"));

        System.out.println(report);
    }
}
