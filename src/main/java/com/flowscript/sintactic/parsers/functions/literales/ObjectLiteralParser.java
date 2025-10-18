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

        if (leftBrace == null || leftBrace.getType() != TokenType.LEFT_BRACE) {
            throw new Parser.ParseException(
                "Se esperaba '{' para iniciar literal de objeto pero se encontro " +
                (leftBrace == null ? "fin de entrada" : "'" + leftBrace.getValue() + "'") +
                " en linea " + (leftBrace == null ? "?" : leftBrace.getLine())
            );
        }
        context.consume(); 

        Token next = context.getCurrentToken();
        if (next != null && next.getType() == TokenType.RIGHT_BRACE) {
            context.consume(); 
            return new ObjectLiteralNode(leftBrace);
        }

        ObjectMemberListParser listParser = new ObjectMemberListParser();
        ObjectMemberListNode memberList = listParser.parse(context);

        Token rightBrace = context.getCurrentToken();
        if (rightBrace == null || rightBrace.getType() != TokenType.RIGHT_BRACE) {
            throw new Parser.ParseException(
                "Se esperaba '}' para cerrar literal de objeto pero se encontro " +
                (rightBrace == null ? "fin de entrada" : "'" + rightBrace.getValue() + "'") +
                " en linea " + (rightBrace == null ? "?" : rightBrace.getLine())
            );
        }
        context.consume(); 

        return new ObjectLiteralNode(leftBrace, memberList);
    }
}
