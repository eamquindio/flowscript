package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberListParser;

/**
 * Parser para literales de objeto.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ObjectLiteral ::= '{' ObjectMemberList? '}'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 18: Literales - Objetos
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer literales de objeto como: { nombre: "Ana", edad: 25 }
 * Un objeto vacío {} también es válido.
 *
 * @see ObjectLiteralNode
 * @see com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberListParser
 */
public class ObjectLiteralParser implements IParser<ObjectLiteralNode> {

    @Override
    public ObjectLiteralNode parse(ParserContext context) throws Parser.ParseException {
        Token leftBrace = context.getCurrentToken();

        // 1️⃣ Validar y consumir '{'
        if (leftBrace == null || leftBrace.getType() != TokenType.LEFT_BRACE) {
            throw new Parser.ParseException(
                "Expected '{' to start object literal but found " +
                (leftBrace == null ? "end of input" : "'" + leftBrace.getValue() + "'") +
                " at line " + (leftBrace == null ? "?" : leftBrace.getLine())
            );
        }
        context.consume(); // consumir '{'

        // 2️⃣ Comprobar si el objeto está vacío: {}
        Token next = context.getCurrentToken();
        if (next != null && next.getType() == TokenType.RIGHT_BRACE) {
            context.consume(); // consumir '}'
            return new ObjectLiteralNode(leftBrace);
        }

        // 3️⃣ Si no está vacío, parsear la lista de miembros
        ObjectMemberListParser listParser = new ObjectMemberListParser();
        ObjectMemberListNode memberList = listParser.parse(context);

        // 4️⃣ Esperar y consumir '}'
        Token rightBrace = context.getCurrentToken();
        if (rightBrace == null || rightBrace.getType() != TokenType.RIGHT_BRACE) {
            throw new Parser.ParseException(
                "Expected '}' to close object literal but found " +
                (rightBrace == null ? "end of input" : "'" + rightBrace.getValue() + "'") +
                " at line " + (rightBrace == null ? "?" : rightBrace.getLine())
            );
        }
        context.consume(); // consumir '}'

        // 5️⃣ Crear el nodo del objeto completo
        return new ObjectLiteralNode(leftBrace, memberList);
    }
}
