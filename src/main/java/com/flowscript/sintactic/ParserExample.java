package com.flowscript.sintactic;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;

import java.util.List;

/**
 * Ejemplo de uso del Parser de FlowScript.
 *
 * <p>Esta clase demuestra cómo usar el parser para analizar código FlowScript
 * y construir un árbol de sintaxis abstracta (AST).</p>
 *
 * <h3>Flujo del proceso:</h3>
 * <ol>
 *   <li>Código fuente (String) → Lexer → Tokens (List&lt;Token&gt;)</li>
 *   <li>Tokens → Parser → AST (ProgramNode)</li>
 *   <li>AST → Interpretación/Compilación/Análisis</li>
 * </ol>
 */
public class ParserExample {

    /**
     * Ejemplo básico: parsear un programa simple.
     */
    public static void main(String[] args) {
        // Ejemplo 1: Programa simple con imports y función
        String sourceCode1 = """
            import "std/http" as http

            function saludar(nombre: text) -> text {
                mensaje = "Hola " + nombre
                return mensaje
            }
            """;

        parseAndPrint("Ejemplo 1: Programa simple", sourceCode1);

        // Ejemplo 2: Programa con proceso BPMN
        String sourceCode2 = """
            import "std/json" as json

            API_URL = "https://api.example.com"

            process ProcesarPedido {
                start -> Validar

                task Validar {
                    action:
                        datos = json.parse(entrada.body)
                        go_to Procesar
                }

                task Procesar {
                    action:
                        imprimir(datos)
                        go_to Fin
                }

                end Fin
            }
            """;

        parseAndPrint("Ejemplo 2: Proceso BPMN", sourceCode2);

        // Ejemplo 3: Programa completo
        String sourceCode3 = """
            import "std/http" as http
            import "std/db" as db

            MAX_RETRIES = 3

            function obtener_usuario(id: integer) -> object {
                usuario = db.get("usuarios", id)
                return usuario
            }

            process GestionUsuario {
                start -> CargarUsuario

                task CargarUsuario {
                    action:
                        user = obtener_usuario(entrada.id)
                        go_to ValidarActivo
                }

                task ValidarActivo {
                    action:
                        if user.activo {
                            go_to Exito
                        }
                        go_to Inactivo
                }

                end Exito
                end Inactivo
            }
            """;

        parseAndPrint("Ejemplo 3: Programa completo", sourceCode3);
    }

    /**
     * Parsea código y muestra información sobre el AST generado.
     *
     * @param title Título descriptivo del ejemplo
     * @param sourceCode Código fuente a parsear
     */
    private static void parseAndPrint(String title, String sourceCode) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println(title);
        System.out.println("=".repeat(70));

        try {
            // Paso 1: Lexer - convertir código a tokens
            Lexer lexer = new Lexer(sourceCode);
            List<Token> tokens = lexer.tokenize();
            System.out.println("✓ Lexer: " + tokens.size() + " tokens generados");

            // Paso 2: Parser - convertir tokens a AST
            Parser parser = new Parser();
            ProgramNode program = parser.parse(tokens);
            System.out.println("✓ Parser: AST construido exitosamente");

            // Paso 3: Analizar el AST
            System.out.println("\nEstructura del programa:");
            System.out.println("  - Nodo raíz: " + program.getNodeType());
            System.out.println("  - Declaraciones: " + program.getDeclarations().size());

            // Mostrar cada declaración
            int i = 1;
            for (DeclarationNode declaration : program.getDeclarations()) {
                System.out.println("    " + i + ". " + declaration.getNodeType() +
                                 " '" + declaration.getDeclaredName() + "'");
                i++;
            }

        } catch (Lexer.LexicalException e) {
            System.err.println("✗ Error léxico: " + e.getMessage());
            e.printStackTrace();
        } catch (Parser.ParseException e) {
            System.err.println("✗ Error de parseo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("✗ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejemplo de manejo de errores de sintaxis.
     */
    public static void errorExample() {
        String invalidCode = """
            function sin_cerrar_llave(x: integer) {
                y = x * 2
                return y
            // Falta llave de cierre

            import "std/http"  // Mal ubicado
            """;

        try {
            Lexer lexer = new Lexer(invalidCode);
            List<Token> tokens = lexer.tokenize();

            Parser parser = new Parser();
            parser.parse(tokens);

        } catch (Lexer.LexicalException e) {
            System.err.println("Error léxico detectado:");
            System.err.println("  " + e.getMessage());
        } catch (Parser.ParseException e) {
            System.err.println("Error de sintaxis detectado:");
            System.err.println("  " + e.getMessage());
            // En una implementación real, aquí se podría mostrar
            // la línea y columna exactas del error
        }
    }
}
