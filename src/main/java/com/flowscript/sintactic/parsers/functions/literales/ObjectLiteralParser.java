package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;

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

    private final com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberListParser memberListParser;

    public ObjectLiteralParser() {
        this.memberListParser = new com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberListParser();
    }

    @Override
    public ObjectLiteralNode parse(ParserContext context) throws Parser.ParseException {
        // Consume '{'
        com.flowscript.lexer.Token leftBrace = context.consume(com.flowscript.lexer.TokenType.LEFT_BRACE);

        // Check if empty object
        com.flowscript.lexer.Token next = context.getCurrentToken();
        if (next != null && next.getType() == com.flowscript.lexer.TokenType.RIGHT_BRACE) {
            context.consume(); // consume '}'
            return new ObjectLiteralNode(leftBrace); // Empty object
        }

        // Parse object members
        com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode memberList =
            memberListParser.parse(context);

        // Consume '}'
        context.consume(com.flowscript.lexer.TokenType.RIGHT_BRACE);

        return new ObjectLiteralNode(leftBrace, memberList);
    }
}
