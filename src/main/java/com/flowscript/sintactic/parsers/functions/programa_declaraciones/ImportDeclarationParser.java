package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ImportDeclarationNode;
import com.flowscript.sintactic.ast.expressions.ExpressionNode;
/**
 * Parser para declaraciones de importación.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ImportDeclaration ::= ( 'import' | 'importar' ) STRING_LITERAL ( 'as' | 'como' ) IDENTIFIER
 *                     | ( 'import_jar' | 'importar_jar' ) STRING_LITERAL ( 'as' | 'como' ) IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 1: Programa y Declaraciones
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Importar módulo estándar
 * import "std/http" as http
 * importar "std/json" como json
 *
 * // Importar módulo local
 * import "utilidades" as util
 *
 * // Importar JAR de Java
 * import_jar "libs/bouncycastle.jar" as crypto
 * importar_jar "libs/guava.jar" como guava
 *
 * // Sin alias (opcional en gramática extendida)
 * import "std/io"
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 *   <li>Consumir token 'import', 'importar', 'import_jar', o 'importar_jar'</li>
 *   <li>Consumir STRING_LITERAL (ruta del módulo/JAR)</li>
 *   <li>Si hay 'as' o 'como', consumir y luego consumir IDENTIFIER (alias)</li>
 *   <li>Crear y retornar ImportDeclarationNode con la información extraída</li>
 * </ol>
 *
 * @see ImportDeclarationNode
 */
public class ImportDeclarationParser implements IParser<ImportDeclarationNode> {

    @Override
    public ImportDeclarationNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token current = context.getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing import declaration");
        }

        boolean isJarImport = false;
        if (context.match(TokenType.IMPORT) || context.match(TokenType.IDENTIFIER, "importar")) {
            context.consume();
        } else if (context.match(TokenType.IMPORT_JAR) || context.match(TokenType.IDENTIFIER, "importar_jar")) {
            context.consume();
            isJarImport = true;
        } else {
            throw new Parser.ParseException(
                "Expected 'import', 'importar', 'import_jar', or 'importar_jar' at line " + current.getLine());
        }

        Token importToken = current;

        if (!context.match(TokenType.STRING_LITERAL)) {
            throw new Parser.ParseException("Expected module path (STRING_LITERAL) after import statement at line " + importToken.getLine());
        }
        Token pathToken = context.consume(TokenType.STRING_LITERAL);
        String modulePath = pathToken.getValue();

        String alias = null;
        if (context.match(TokenType.AS) || context.match(TokenType.IDENTIFIER, "como")) {
            context.consume(); 
            
            if (!context.match(TokenType.IDENTIFIER)) {
                throw new Parser.ParseException("Expected alias identifier after 'as/como' at line " + pathToken.getLine());
            }

            Token aliasToken = context.consume(TokenType.IDENTIFIER);
            alias = aliasToken.getValue();
        }

        return new ImportDeclarationNode(importToken, isJarImport, modulePath, alias);

    }
}
