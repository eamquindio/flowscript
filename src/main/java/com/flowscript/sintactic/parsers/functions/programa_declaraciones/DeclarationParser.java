package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.sintactic.parsers.functions.statements_basicos.VariableDeclarationParser;
import com.flowscript.sintactic.parsers.process.estructura_principal.ProcessDeclarationParser;

/**
 * Parser coordinador para declaraciones en FlowScript.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Declaration ::= ImportDeclaration
 *               | FunctionDeclaration
 *               | ProcessDeclaration
 *               | VariableDeclaration
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Importación
 * import "std/http" as http
 * import_jar "libs/crypto.jar" as crypto
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
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * DeclarationParser parser = new DeclarationParser();
 * DeclarationNode declaration = parser.parse(context);
 * </pre>
 *
 * @see DeclarationNode
 */
public class DeclarationParser implements IParser<DeclarationNode> {

    private final ImportDeclarationParser importParser;
    private final FunctionDeclarationParser functionParser;
    private final ProcessDeclarationParser processParser;
    private final VariableDeclarationParser variableParser;

    public DeclarationParser() {
        this.importParser = new ImportDeclarationParser();
        this.functionParser = new FunctionDeclarationParser();
        this.processParser = new ProcessDeclarationParser();
        this.variableParser = new VariableDeclarationParser();
    }

    @Override
    public DeclarationNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing declaration");
        }

        String value = current.getValue();
        TokenType type = current.getType();

        // ImportDeclaration
        if (value.equals("importar") || value.equals("import") ||
            value.equals("importar_jar") || value.equals("import_jar")) {
            return importParser.parse(context);
        }

        // FunctionDeclaration
        if (value.equals("funcion") || value.equals("function") || type == TokenType.FUNCTION) {
            return functionParser.parse(context);
        }

        // ProcessDeclaration
        if (value.equals("proceso") || value.equals("process") || type == TokenType.PROCESS) {
            return processParser.parse(context);
        }

        // VariableDeclaration (top-level)
        if (type == TokenType.IDENTIFIER && context.peek(1) != null &&
            context.peek(1).getValue().equals("=")) {
            return variableParser.parse(context);
        }

        throw new Parser.ParseException(
            "Unexpected token '" + value + "' at line " + current.getLine() +
            ". Expected import, function, process, or variable declaration."
        );
    }
}
