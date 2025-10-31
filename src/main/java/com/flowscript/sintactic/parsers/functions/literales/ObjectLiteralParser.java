package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.expressions.ExpressionNode;
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

    
    private final ObjectMemberListParser memberListParser;

    public ObjectLiteralParser() {
        this.memberListParser = new ObjectMemberListParser();
    }

    @Override
    public ObjectLiteralNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método

        Token leftBrace = context.expect(TokenType.LEFT_BRACE, "Expected '{' to start object literal");

        if (context.match(TokenType.RIGHT_BRACE)) {
            context.consume(TokenType.RIGHT_BRACE);
            return new ObjectLiteralNode(leftBrace);
        }

        ObjectMemberListNode memberList;
        try {
            var members = memberListParser.parse(context);
            if (members == null || members.isEmpty()) {
                memberList = null;
            } else {
                memberList = members.get(0);
            }
        } catch (Parser.ParseException e) {
            throw new Parser.ParseException("Error parsing object member list: " + e.getMessage());
        }

        context.expect(TokenType.RIGHT_BRACE, "Expected '}' to close object literal");

        return new ObjectLiteralNode(leftBrace, memberList);
    }
}
    

