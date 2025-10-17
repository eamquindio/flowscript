package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.sintactic.parsers.functions.statements_basicos.VariableDeclarationParser;
import com.flowscript.sintactic.parsers.process.estructura_principal.ProcessDeclarationParser;

/**
 * Parser coordinador para declaraciones en FlowScript.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * Declaration ::= ImportDeclaration | FunctionDeclaration | ProcessDeclaration | VariableDeclaration
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * // Importación
 * import "std/http" as http
 *
 * // Función
 * function calcular(x: entero) -> entero {
 *     return x * 2
 * }
 *
 * // Proceso
 * process MiProceso {
 *     start -> Inicio
 *     end Inicio
 * }
 *
 * // Variable global
 * API_KEY = "secret123"
 * </pre>
 *
 * @see DeclarationNode
 */
public class DeclarationParser implements IParser<DeclarationNode> {
  private static final ImportDeclarationParser IMPORT_PARSER = new ImportDeclarationParser();
  private static final FunctionDeclarationParser FUNCTION_PARSER = new FunctionDeclarationParser();
  private static final ProcessDeclarationParser PROCESS_PARSER = new ProcessDeclarationParser();
  private static final VariableDeclarationParser VARIABLE_PARSER = new VariableDeclarationParser();

  @Override
  public DeclarationNode parse(ParserContext context) throws ParseException {
    TokenType type = context.getCurrentToken().getType();

    if (type == TokenType.IMPORT || type == TokenType.IMPORT_JAR) {
      return IMPORT_PARSER.parse(context);
    }
    if (type == TokenType.FUNCTION)
      return FUNCTION_PARSER.parse(context);
    if (type == TokenType.PROCESS)
      return PROCESS_PARSER.parse(context);

    if (type == TokenType.IDENTIFIER && context.peek(1).getType() == TokenType.ASSIGN) {
      return VARIABLE_PARSER.parse(context);
    }

    throw new ParseException("Se esperaba una declaración (import, function, process, variable)");
  }
}