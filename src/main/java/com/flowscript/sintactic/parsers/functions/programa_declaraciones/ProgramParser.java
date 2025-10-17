package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;

/**
 * Parser para el símbolo inicial de la gramática (Program).
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * Program ::= Declaration*
 * </pre>
 *
 * <p>
 * Este es el parser de nivel superior que procesa un programa completo de
 * FlowScript.
 * Un programa consiste en cero o más declaraciones (imports, funciones,
 * procesos, variables).
 * </p>
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * // Programa simple
 * import "std/http" as http
 *
 * function main() {
 *     http.get("https://example.com")
 * }
 *
 * // Programa con múltiples declaraciones
 * import "std/http" as http
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
 *     task Validar {
 *         action: go_to Fin
 *     }
 *     end Fin
 * }
 * </pre>
 *
 * @see ProgramNode
 */
public class ProgramParser implements IParser<ProgramNode> {
  private static final DeclarationParser DECLARATION_PARSER = new DeclarationParser();

  @Override
  public ProgramNode parse(ParserContext context) throws ParseException {
    ProgramNode program = new ProgramNode();

    while (context.hasMoreTokens() && !context.check(TokenType.EOF)) {
      program.addDeclaration(DECLARATION_PARSER.parse(context));
    }

    return program;
  }
}