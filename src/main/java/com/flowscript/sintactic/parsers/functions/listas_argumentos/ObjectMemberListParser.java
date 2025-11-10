package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;

import java.util.List;

/**
 * Parser para listas de miembros de objeto.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ObjectMemberList ::= ObjectMember ( ',' ObjectMember )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 22: Listas - Miembros de Objeto
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer listas de miembros como: nombre: "Ana", edad: 25, activo: verdadero
 * Nota: Este parser NO implementa IParser porque retorna una List.
 *
 * @see ObjectMemberListNode
 * @see ObjectMemberParser
 */
public class ObjectMemberListParser {

    private final ObjectMemberParser memberParser;

    public ObjectMemberListParser() {
        this.memberParser = new ObjectMemberParser();
    }

    /**
     * Parsea una lista de miembros de objeto.
     *
     * @param context El contexto del parser
     * @return ObjectMemberListNode con todos los miembros
     * @throws Parser.ParseException Si hay un error de sintaxis
     */
    public ObjectMemberListNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first member
        ObjectMemberListNode.ObjectMemberNode firstMember = memberParser.parse(context);
        ObjectMemberListNode memberList = new ObjectMemberListNode(firstMember);

        // Parse remaining members separated by comma
        while (context.getCurrentToken() != null &&
               context.getCurrentToken().getType() == com.flowscript.lexer.TokenType.COMMA) {
            context.consume(); // consume comma
            ObjectMemberListNode.ObjectMemberNode member = memberParser.parse(context);
            memberList.addMember(member);
        }

        return memberList;
    }
}
