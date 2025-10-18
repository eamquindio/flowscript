package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para el símbolo inicial de la gramática (Program).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Program ::= Declaration*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 1: Programa y Declaraciones
 *
 * <h3>Descripción:</h3>
 * Este es el parser de nivel superior que procesa un programa completo de FlowScript.
 * Un programa consiste en cero o más declaraciones (imports, funciones, procesos, variables).
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Programa simple con imports y función
 * import "std/http" as http
 *
 * function main() {
 *     http.get("https://example.com")
 * }
 *
 * // Programa completo con múltiples declaraciones
 * import "std/http" as http
 * import "std/json" as json
 *
 * API_URL = "https://api.example.com"
 *
 * function obtener_datos(id: entero) -> objeto {
 *     respuesta = http.get(API_URL + "/items/" + id)
 *     return json.parse(respuesta.body)
 * }
 *
 * process ProcesarPedido {
 *     start -> Validar
 *
 *     task Validar {
 *         action:
 *             datos = obtener_datos(entrada.id)
 *             go_to Procesar
 *     }
 *
 *     task Procesar {
 *         action:
 *             imprimir(datos)
 *             go_to Fin
 *     }
 *
 *     end Fin
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * List&lt;Token&gt; tokens = lexer.tokenize(sourceCode);
 * ParserContext context = new ParserContext(tokens);
 * ProgramParser parser = new ProgramParser();
 * ProgramNode program = parser.parse(context);
 * </pre>
 *
 * @see ProgramNode
 * @see DeclarationNode
 * @see DeclarationParser
 */
public class ProgramParser implements IParser<ProgramNode> {

    private final DeclarationParser declarationParser;

    public ProgramParser() {
        this.declarationParser = new DeclarationParser();
    }

    @Override
    public ProgramNode parse(ParserContext context) throws Parser.ParseException {
        ProgramNode program = new ProgramNode();

        while (context.getCurrentToken() != null &&
               context.getCurrentToken().getType() != TokenType.EOF) {

            DeclarationNode declaration = declarationParser.parse(context);
            program.addDeclaration(declaration);
        }

        return program;
    }
}
