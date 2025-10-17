package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;

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
 * @see ObjectMemberNode
 */
public class ObjectMemberParser implements IParser<ObjectMemberNode> {

    @Override
    public ObjectMemberNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token keyToken;
        boolean keyIsString = false;
        String keyValue;

        if (context.match(TokenType.IDENTIFIER)) {
            keyToken = context.consume(TokenType.IDENTIFIER);
            keyValue = keyToken.getValue();
        } else if (context.match(TokenType.STRING_LITERAL)) {
            keyToken = context.consume(TokenType.STRING_LITERAL);
            keyIsString = true;
            keyValue = keyToken.getValue().replaceAll("^\"|\"$", "");
        } else {
            throw new Parser.ParseException("Expected object key (identifier or string) at line " 
                    + context.getCurrentLine());
        }

        context.expect(TokenType.COLON, "Expected ':' after object key");

        ExpressionParser exprParser = new ExpressionParser();
        ExpressionNode valueNode = exprParser.parse(context);

        return new ObjectMemberNode(keyValue, keyIsString, valueNode);

    }
}
