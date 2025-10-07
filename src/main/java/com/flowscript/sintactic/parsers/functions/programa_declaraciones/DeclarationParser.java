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
        return  null;
    }
}
