package com.flowscript.sintactic;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;

import java.util.List;

/**
 * Analizador sintáctico principal de FlowScript.
 *
 * <p>Esta clase es el punto de entrada para el análisis sintáctico. Coordina
 * el proceso de convertir una secuencia de tokens en un árbol de sintaxis abstracta (AST).</p>
 *
 * <h3>Arquitectura del Parser:</h3>
 * <ul>
 *   <li><b>Parser principal</b>: Punto de entrada que invoca al ProgramParser</li>
 *   <li><b>ProgramParser</b>: Procesa el símbolo inicial de la gramática (Program)</li>
 *   <li><b>Parsers especializados</b>: Cada regla gramatical tiene su propio parser (patrón Visitor)</li>
 *   <li><b>ParserContext</b>: Mantiene el estado compartido (tokens, índice actual)</li>
 *   <li><b>IParser&lt;T&gt;</b>: Interface común que implementan todos los parsers</li>
 * </ul>
 *
 * <h3>Gramática de FlowScript:</h3>
 * La gramática completa está definida en: {@code src/main/tlf/flowscript-grammar.bnf}
 * <pre>
 * Program ::= Declaration*
 * Declaration ::= ImportDeclaration | FunctionDeclaration | ProcessDeclaration | VariableDeclaration
 * </pre>
 *
 * <h3>Uso típico:</h3>
 * <pre>
 * // 1. Obtener tokens del lexer
 * Lexer lexer = new Lexer(sourceCode);
 * List&lt;Token&gt; tokens = lexer.tokenize();
 *
 * // 2. Crear parser y parsear
 * Parser parser = new Parser();
 * ProgramNode ast = parser.parse(tokens);
 *
 * // 3. Usar el AST para interpretación, compilación, etc.
 * ast.getDeclarations().forEach(declaration -&gt; {
 *     // Procesar cada declaración
 * });
 * </pre>
 *
 * <h3>Manejo de errores:</h3>
 * El parser lanza {@link ParseException} cuando encuentra errores de sintaxis.
 * La excepción incluye información sobre la ubicación del error (línea, columna).
 *
 * @see ProgramNode
 * @see ProgramParser
 * @see ParserContext
 * @see IParser
 */
public class Parser {

    private final ProgramParser programParser;

    /**
     * Constructor por defecto.
     * Inicializa el parser con el ProgramParser como punto de entrada.
     */
    public Parser() {
        this.programParser = new ProgramParser();
    }

    /**
     * Analiza una lista de tokens y construye el árbol de sintaxis abstracta (AST).
     *
     * @param tokens Lista de tokens producidos por el lexer
     * @return El nodo raíz del AST (ProgramNode)
     * @throws ParseException Si se encuentra un error de sintaxis
     * @throws IllegalArgumentException Si la lista de tokens es null o vacía
     */
    public ProgramNode parse(List<Token> tokens) throws ParseException {
        if (tokens == null) {
            throw new IllegalArgumentException("La lista de tokens no puede ser null");
        }

        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("La lista de tokens no puede estar vacía");
        }

        // Crear contexto del parser
        ParserContext context = new ParserContext(tokens);

        // Invocar al parser del símbolo inicial de la gramática
        return programParser.parse(context);
    }

    /**
     * Excepción lanzada cuando ocurre un error durante el análisis sintáctico.
     *
     * <p>Esta excepción incluye información sobre el error y su ubicación en el código fuente.</p>
     */
    public static class ParseException extends Exception {

        /**
         * Crea una nueva excepción de parseo con un mensaje descriptivo.
         *
         * @param message Mensaje que describe el error
         */
        public ParseException(String message) {
            super(message);
        }

        /**
         * Crea una nueva excepción de parseo con un mensaje y una causa.
         *
         * @param message Mensaje que describe el error
         * @param cause La causa original del error
         */
        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
