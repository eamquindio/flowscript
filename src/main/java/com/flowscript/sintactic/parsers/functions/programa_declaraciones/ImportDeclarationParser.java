package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ImportDeclarationNode;

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
        // 1. Consume import token and determine if it's JAR import
        Token importToken = context.getCurrentToken();
        boolean isJarImport = false;

        if (importToken.getType() == TokenType.IMPORT) {
            context.consume();
            isJarImport = false;
        } else if (importToken.getType() == TokenType.IMPORT_JAR) {
            context.consume();
            isJarImport = true;
        } else {
            throw new Parser.ParseException("Expected 'import' or 'import_jar' but found " +
                                          importToken.getValue() + " at line " + importToken.getLine());
        }

        // 2. Consume STRING_LITERAL (module path)
        Token pathToken = context.consume(TokenType.STRING_LITERAL);
        String modulePath = pathToken.getValue();
        // Remove quotes from string literal
        if (modulePath.startsWith("\"") && modulePath.endsWith("\"")) {
            modulePath = modulePath.substring(1, modulePath.length() - 1);
        }

        // 3. Check for optional 'as' clause
        String alias = null;
        if (context.getCurrentToken() != null &&
            context.getCurrentToken().getType() == TokenType.AS) {
            context.consume(); // consume 'as'
            Token aliasToken = context.consume(TokenType.IDENTIFIER);
            alias = aliasToken.getValue();
        }

        // 4. Create and return ImportDeclarationNode
        return new ImportDeclarationNode(importToken, isJarImport, modulePath, alias);
    }
}
