package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;


import java.util.ArrayList;
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

    private final ObjectMemberParser objectMemberParser;

    public ObjectMemberListParser() {
        this.objectMemberParser = new ObjectMemberParser();
    }
    
    public ObjectMemberListNode parse(ParserContext context) throws Parser.ParseException {
        List<ObjectMemberNode> members = new ArrayList<>();

        ObjectMemberNode firstMember = objectMemberParser.parse(context);
        members.add(firstMember);

        while (context.hasMoreTokens()) {
            Token current = context.getCurrentToken();
            if (current == null || current.getType() != TokenType.COMMA) break;
            context.consume();
            if (!context.hasMoreTokens() || context.getCurrentToken().getType() == TokenType.RIGHT_BRACE) {
                throw new Parser.ParseException("Unexpected ',' before '}' in object literal");
            }
            ObjectMemberNode nextMember = objectMemberParser.parse(context);
            members.add(nextMember);
        }

        return new ObjectMemberListNode(members);
    }
}
