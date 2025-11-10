package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;

/**
 * Parser para un miembro de objeto (par clave-valor).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ObjectMember ::= IDENTIFIER ':' Expression
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 23: Listas - Miembro Individual de Objeto
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer miembros como: nombre: "Ana", edad: 25, activo: verdadero
 *
 * @see ObjectMemberListNode.ObjectMemberNode
 */
public class ObjectMemberParser implements IParser<ObjectMemberListNode.ObjectMemberNode> {

    private final com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser expressionParser;

    public ObjectMemberParser() {
        this.expressionParser = new com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser();
    }

    @Override
    public ObjectMemberListNode.ObjectMemberNode parse(ParserContext context) throws Parser.ParseException {
        // Parse IDENTIFIER (key)
        com.flowscript.lexer.Token keyToken = context.consume(com.flowscript.lexer.TokenType.IDENTIFIER);
        String key = keyToken.getValue();

        // Consume ':'
        context.consume(com.flowscript.lexer.TokenType.COLON);

        // Parse Expression (value)
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode value = expressionParser.parse(context);

        return new ObjectMemberListNode.ObjectMemberNode(keyToken, key, value);
    }
}
