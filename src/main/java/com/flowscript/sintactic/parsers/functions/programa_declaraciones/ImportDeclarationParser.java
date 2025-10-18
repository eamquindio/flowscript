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
        Token importToken = context.getCurrentToken();

        if (!context.checkValue("import") && 
            !context.checkValue("importar") &&
            !context.checkValue("import_jar") &&
            !context.checkValue("importar_jar")) {
            throw new Parser.ParseException("Expected 'import' or 'importar' at start of import declaration");
        }

        boolean isJarImport = importToken.getValue().contains("jar");
        context.consume();

        Token pathToken = context.consume(TokenType.STRING_LITERAL);
        String modulePath = pathToken.getValue();

        String alias = null;
        if (context.checkValue("as") || context.checkValue("como")) {
            context.consume(); // consumir 'as' o 'como'
            Token aliasToken = context.consume(TokenType.IDENTIFIER);
            alias = aliasToken.getValue();
        }

        return new ImportDeclarationNode(importToken, isJarImport, modulePath, alias);
    }
}
