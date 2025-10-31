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

    @Override
    public ObjectLiteralNode parse(ParserContext context) throws Parser.ParseException {
        if (!context.check(com.flowscript.lexer.TokenType.LEFT_BRACE)) {
            throw new Parser.ParseException("Expected '{' to start object literal");
        }
        com.flowscript.lexer.Token left = context.consume();

        if (context.check(com.flowscript.lexer.TokenType.RIGHT_BRACE)) {
            context.consume();
            return new ObjectLiteralNode(left);
        }

        // Parse first member
        com.flowscript.lexer.Token keyToken = context.consume();
        String key = keyToken.getValue();
        // expect ':'
        context.consume(com.flowscript.lexer.TokenType.COLON);
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode value = new com.flowscript.sintactic.parsers.functions.expresiones.PrimaryExpressionParser().parse(context);
        com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode.ObjectMemberNode firstMember =
                new com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode.ObjectMemberNode(keyToken, key, value);
        com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode memberList = new com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode(firstMember);

        while (context.check(com.flowscript.lexer.TokenType.COMMA)) {
            context.consume();
            com.flowscript.lexer.Token kt = context.consume();
            String k = kt.getValue();
            context.consume(com.flowscript.lexer.TokenType.COLON);
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode val = new com.flowscript.sintactic.parsers.functions.expresiones.PrimaryExpressionParser().parse(context);
            com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode.ObjectMemberNode m =
                    new com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode.ObjectMemberNode(kt, k, val);
            memberList.addMember(m);
        }

        if (!context.check(com.flowscript.lexer.TokenType.RIGHT_BRACE)) {
            throw new Parser.ParseException("Expected '}' to close object literal");
        }
        context.consume();
        return new ObjectLiteralNode(left, memberList);
    }
}
