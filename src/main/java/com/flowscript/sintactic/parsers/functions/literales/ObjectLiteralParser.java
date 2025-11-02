package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberParser;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import java.util.ArrayList;
import java.util.List;

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

    private ObjectMemberParser memberParser;

    public ObjectLiteralParser() {
        // Lazy initialization to avoid circular dependency
    }
    
    private ObjectMemberParser getMemberParser() {
        if (memberParser == null) {
            memberParser = new ObjectMemberParser();
        }
        return memberParser;
    }

    @Override
    public ObjectLiteralNode parse(ParserContext context) throws Parser.ParseException {
        Token leftBrace = context.consume(TokenType.LEFT_BRACE);
        
        if (context.check(TokenType.RIGHT_BRACE)) {
            context.consume(TokenType.RIGHT_BRACE);
            return new ObjectLiteralNode(leftBrace);
        }
        
        List<com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode.ObjectMemberNode> listMembers = new ArrayList<>();
        
        ObjectMemberNode firstMember = getMemberParser().parse(context);
        Token firstToken = leftBrace;
        listMembers.add(new com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode.ObjectMemberNode(
            firstToken, firstMember.getKey(), firstMember.getValue()
        ));
        
        while (context.checkValue(",")) {
            context.consume();
            ObjectMemberNode member = getMemberParser().parse(context);
            listMembers.add(new com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode.ObjectMemberNode(
                firstToken, member.getKey(), member.getValue()
            ));
        }
        
        context.consume(TokenType.RIGHT_BRACE);
        
        com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode memberList = 
            new com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode(leftBrace, listMembers);
        return new ObjectLiteralNode(leftBrace, memberList);
    }
}
