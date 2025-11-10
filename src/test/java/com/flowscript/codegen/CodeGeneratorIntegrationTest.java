package com.flowscript.codegen;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the complete code generation pipeline.
 * Tests the full flow: FlowScript source -> Lexer -> Parser -> AST -> Java code
 */
public class CodeGeneratorIntegrationTest {

    @Test
    public void testSimpleFunctionGeneration() throws Exception {
        // FlowScript source code
        String flowScriptCode = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }
            """;

        // Parse FlowScript code
        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        // Generate Java code
        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        // Verify generated code
        assertNotNull(javaCode);
        assertTrue(javaCode.contains("package com.flowscript.generated;"));
        assertTrue(javaCode.contains("public class GeneratedCode"));
        assertTrue(javaCode.contains("public static int suma(int a, int b)"));
        assertTrue(javaCode.contains("return"));
    }

    @Test
    public void testFunctionWithVariablesAndIf() throws Exception {
        String flowScriptCode = """
            funcion maximo(a: entero, b: entero) -> entero {
                resultado = 0
                si (a > b) {
                    resultado = a
                } sino {
                    resultado = b
                }
                retornar resultado
            }
            """;

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        assertNotNull(javaCode);
        assertTrue(javaCode.contains("public static int maximo(int a, int b)"));
        assertTrue(javaCode.contains("if"));
        assertTrue(javaCode.contains("else"));
        assertTrue(javaCode.contains("return"));
    }

    @Test
    public void testFunctionWithLoop() throws Exception {
        String flowScriptCode = """
            funcion sumarHasta(n: entero) -> entero {
                suma = 0
                para i desde 1 hasta n {
                    suma = suma + i
                }
                retornar suma
            }
            """;

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        assertNotNull(javaCode);
        assertTrue(javaCode.contains("public static int sumarHasta(int n)"));
        assertTrue(javaCode.contains("for"));
    }

    @Test
    public void testMultipleFunctions() throws Exception {
        String flowScriptCode = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }

            funcion resta(a: entero, b: entero) -> entero {
                retornar a - b
            }
            """;

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        assertNotNull(javaCode);
        assertTrue(javaCode.contains("public static int suma(int a, int b)"));
        assertTrue(javaCode.contains("public static int resta(int a, int b)"));
    }

    @Test
    public void testFunctionWithBuiltins() throws Exception {
        String flowScriptCode = """
            funcion saludar(nombre: texto) -> vacio {
                imprimir("Hola " + nombre)
            }
            """;

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        assertNotNull(javaCode);
        assertTrue(javaCode.contains("public static void saludar(String nombre)"));
        assertTrue(javaCode.contains("import com.flowscript.runtime.*"));
    }

    @Test
    public void testSimpleProcess() throws Exception {
        String flowScriptCode = """
            proceso ProcesoSimple {
                inicio -> TareaPrincipal

                tarea TareaPrincipal {
                    accion:
                        imprimir("Ejecutando tarea")
                        ir_a Fin1
                }

                fin Fin1
            }
            """;

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        assertNotNull(javaCode);
        assertTrue(javaCode.contains("public static void ProcesoSimple()"));
        assertTrue(javaCode.contains("TAREA"));
    }

    @Test
    public void testProcessWithGateway() throws Exception {
        String flowScriptCode = """
            proceso ProcesoConDecision {
                inicio -> Validar

                tarea Validar {
                    accion:
                        valido = verdadero
                        si (valido) {
                            ir_a TareaOK
                        } sino {
                            ir_a TareaError
                        }
                }

                tarea TareaOK {
                    accion:
                        imprimir("OK")
                        ir_a Fin1
                }

                tarea TareaError {
                    accion:
                        imprimir("Error")
                        ir_a Fin1
                }

                fin Fin1
            }
            """;

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        assertNotNull(javaCode);
        assertTrue(javaCode.contains("public static void ProcesoConDecision()"));
        assertTrue(javaCode.contains("if"));
    }

    @Test
    public void testCustomPackageAndClassName() throws Exception {
        String flowScriptCode = """
            funcion test() -> vacio {
                imprimir("test")
            }
            """;

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program, "com.example.custom", "MyClass");

        assertNotNull(javaCode);
        assertTrue(javaCode.contains("package com.example.custom;"));
        assertTrue(javaCode.contains("public class MyClass"));
    }

    @Test
    public void testEmptyProgram() throws Exception {
        String flowScriptCode = "";

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        assertNotNull(javaCode);
        assertTrue(javaCode.contains("package com.flowscript.generated;"));
        assertTrue(javaCode.contains("public class GeneratedCode"));
    }

    @Test
    public void testCodeStructure() throws Exception {
        String flowScriptCode = """
            funcion calcular(x: entero) -> entero {
                retornar x * 2
            }
            """;

        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator generator = new CodeGenerator();
        String javaCode = generator.generate(program);

        // Verify basic structure
        assertNotNull(javaCode);

        // Should have package declaration
        assertTrue(javaCode.contains("package"));

        // Should have imports
        assertTrue(javaCode.contains("import"));

        // Should have class declaration
        assertTrue(javaCode.contains("public class"));

        // Should have method
        assertTrue(javaCode.contains("public static"));

        // Should be properly closed (same number of { and })
        long openBraces = javaCode.chars().filter(ch -> ch == '{').count();
        long closeBraces = javaCode.chars().filter(ch -> ch == '}').count();
        assertEquals(openBraces, closeBraces, "Braces should be balanced");
    }
}
