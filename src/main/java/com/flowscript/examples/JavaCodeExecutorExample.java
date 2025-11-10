package com.flowscript.examples;

import com.flowscript.codegen.CodeGenerator;
import com.flowscript.codegen.JavaCodeExecutor;
import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;

import java.util.List;

/**
 * Ejemplo de uso del JavaCodeExecutor.
 * Demuestra cómo compilar y ejecutar código Java generado desde FlowScript.
 */
public class JavaCodeExecutorExample {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  Ejemplo: JavaCodeExecutor - FlowScript to Java");
        System.out.println("=================================================\n");

        // Ejemplo 1: Compilar código Java directamente
        example1_DirectJavaCompilation();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Ejemplo 2: Generar desde FlowScript y compilar
        example2_FlowScriptToJava();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Ejemplo 3: Ejecutar código Java con argumentos
        example3_ExecuteWithArguments();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Ejemplo 4: FlowScript complejo
        example4_ComplexFlowScript();
    }

    /**
     * Ejemplo 1: Compilar código Java directamente.
     */
    private static void example1_DirectJavaCompilation() {
        System.out.println("EJEMPLO 1: Compilación directa de código Java");
        System.out.println("-".repeat(50));

        String javaCode = """
            package com.example;

            public class Calculator {
                public static int add(int a, int b) {
                    return a + b;
                }

                public static int multiply(int a, int b) {
                    return a * b;
                }
            }
            """;

        System.out.println("Código Java:");
        System.out.println(javaCode);

        try {
            JavaCodeExecutor executor = new JavaCodeExecutor();
            JavaCodeExecutor.CompilationResult result = executor.compile(javaCode);

            if (result.isSuccess()) {
                System.out.println("✓ Compilación exitosa!");
                System.out.println("  Tamaño del bytecode: " + result.getBytecodeSize() + " bytes");

                // Ejecutar el método
                JavaCodeExecutor.ExecutionResult execResult = executor.execute(javaCode, "add", 10, 5);
                System.out.println("  Resultado de add(10, 5): " + execResult.getReturnValue());
                System.out.println("  Tiempo de ejecución: " + execResult.getExecutionTimeMs() + "ms");
            } else {
                System.out.println("✗ Error de compilación:");
                System.out.println(result.getErrorMessage());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Ejemplo 2: Generar código Java desde FlowScript.
     */
    private static void example2_FlowScriptToJava() {
        System.out.println("EJEMPLO 2: FlowScript -> Java -> Compilación");
        System.out.println("-".repeat(50));

        String flowScriptCode = """
            funcion suma(a: entero, b: entero) -> entero {
                retornar a + b
            }

            funcion resta(a: entero, b: entero) -> entero {
                retornar a - b
            }
            """;

        System.out.println("Código FlowScript:");
        System.out.println(flowScriptCode);

        try {
            // 1. Tokenizar
            Lexer lexer = new Lexer(flowScriptCode);
            List<Token> tokens = lexer.tokenize();
            System.out.println("✓ Tokenización completada (" + tokens.size() + " tokens)");

            // 2. Parsear
            Parser parser = new Parser();
            ProgramNode program = parser.parse(tokens);
            System.out.println("✓ Parsing completado");

            // 3. Generar código Java
            CodeGenerator generator = new CodeGenerator();
            String javaCode = generator.generate(program);
            System.out.println("✓ Código Java generado");

            System.out.println("\nCódigo Java generado:");
            System.out.println("-".repeat(30));
            System.out.println(javaCode);
            System.out.println("-".repeat(30));

            // 4. Compilar el código generado
            JavaCodeExecutor executor = new JavaCodeExecutor();
            JavaCodeExecutor.CompilationResult result = executor.compile(javaCode);

            if (result.isSuccess()) {
                System.out.println("\n✓ Compilación exitosa!");
                System.out.println("  Tamaño del bytecode: " + result.getBytecodeSize() + " bytes");
            } else {
                System.out.println("\n✗ Error de compilación:");
                System.out.println(result.getErrorMessage());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejemplo 3: Ejecutar código Java con diferentes argumentos.
     */
    private static void example3_ExecuteWithArguments() {
        System.out.println("EJEMPLO 3: Ejecución con argumentos");
        System.out.println("-".repeat(50));

        String javaCode = """
            package com.example;

            public class Math {
                public static double potencia(double base, double exponente) {
                    return java.lang.Math.pow(base, exponente);
                }

                public static double raizCuadrada(double numero) {
                    return java.lang.Math.sqrt(numero);
                }
            }
            """;

        try {
            JavaCodeExecutor executor = new JavaCodeExecutor();

            // Ejecutar potencia
            JavaCodeExecutor.ExecutionResult result1 = executor.execute(javaCode, "potencia", 2.0, 8.0);
            System.out.println("potencia(2, 8) = " + result1.getReturnValue() +
                             " (ejecutado en " + result1.getExecutionTimeMs() + "ms)");

            // Ejecutar raíz cuadrada
            JavaCodeExecutor.ExecutionResult result2 = executor.execute(javaCode, "raizCuadrada", 144.0);
            System.out.println("raizCuadrada(144) = " + result2.getReturnValue() +
                             " (ejecutado en " + result2.getExecutionTimeMs() + "ms)");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Ejemplo 4: FlowScript más complejo con estructuras de control.
     */
    private static void example4_ComplexFlowScript() {
        System.out.println("EJEMPLO 4: FlowScript complejo");
        System.out.println("-".repeat(50));

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

        System.out.println("Código FlowScript:");
        System.out.println(flowScriptCode);

        try {
            // Procesar FlowScript
            Lexer lexer = new Lexer(flowScriptCode);
            List<Token> tokens = lexer.tokenize();
            Parser parser = new Parser();
            ProgramNode program = parser.parse(tokens);
            CodeGenerator generator = new CodeGenerator();
            String javaCode = generator.generate(program);

            System.out.println("\nCódigo Java generado:");
            System.out.println("-".repeat(30));
            // Mostrar solo las primeras líneas para no saturar
            String[] lines = javaCode.split("\n");
            for (int i = 0; i < Math.min(20, lines.length); i++) {
                System.out.println(lines[i]);
            }
            if (lines.length > 20) {
                System.out.println("... (" + (lines.length - 20) + " líneas más)");
            }
            System.out.println("-".repeat(30));

            // Compilar
            JavaCodeExecutor executor = new JavaCodeExecutor();
            JavaCodeExecutor.CompilationResult result = executor.compile(javaCode);

            System.out.println("\nResultado: " + result);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
