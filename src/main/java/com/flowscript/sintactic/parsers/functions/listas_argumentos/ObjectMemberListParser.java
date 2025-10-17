package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;

/**
 * Parser para listas de miembros de objeto.
 *
 * <h3>Gramática BNF:</h3>
 * 
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
 * Debe reconocer listas de miembros como: nombre: "Ana", edad: 25, activo:
 * verdadero
 * Nota: Este parser NO implementa IParser porque retorna una List.
 *
 * @see ObjectMemberListNode
 * @see ObjectMemberParser
 */
public class ObjectMemberListParser {
    private static final ObjectMemberParser MEMBER_PARSER = new ObjectMemberParser();

    /**
     * Parsea una lista de miembros de objeto.
     *
     * @param context El contexto del parser
     * @return Lista de nodos ObjectMemberListNode
     * @throws Parser.ParseException Si hay un error de sintaxis
     */
    public List<ObjectMemberListNode> parse(ParserContext context) throws ParseException {
        List<ObjectMemberListNode> members = new ArrayList<>();
        members.add(new ObjectMemberListNode(MEMBER_PARSER.parse(context)));

        while (context.check(TokenType.COMMA)) {
            context.consume(TokenType.COMMA);
            members.add(new ObjectMemberListNode(MEMBER_PARSER.parse(context)));
        }

        return members;
    }
}