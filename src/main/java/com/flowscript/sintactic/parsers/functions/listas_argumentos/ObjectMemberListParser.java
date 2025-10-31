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

    /**
     * Parsea una lista de miembros de objeto.
     *
     * @param context El contexto del parser
     * @return Lista de nodos ObjectMemberListNode
     * @throws Parser.ParseException Si hay un error de sintaxis
     */
    public List<ObjectMemberListNode> parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        List<ObjectMemberListNode> memberLists = new ArrayList<>();

        if (context.match(TokenType.RIGHT_BRACE)) {
            return memberLists;
        }

        ObjectMemberParser memberParser = new ObjectMemberParser();

        ObjectMemberNode firstMember = memberParser.parse(context);
        ObjectMemberListNode listNode = new ObjectMemberListNode(firstMember);

        while (context.match(TokenType.COMMA)) {
            context.consume(TokenType.COMMA);

            if (context.match(TokenType.RIGHT_BRACE)) {
                throw new Parser.ParseException("Unexpected ',' before '}' in object member list");
            }

            ObjectMemberNode nextMember = memberParser.parse(context);
            listNode.addMember(nextMember);
        }

        memberLists.add(listNode);

        return memberLists;

    }
}
