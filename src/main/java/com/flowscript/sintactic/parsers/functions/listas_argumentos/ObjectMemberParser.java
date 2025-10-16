package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * Parser para un miembro de objeto (par clave-valor).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ObjectMember ::= IDENTIFIER ':' Expression | STRING_LITERAL ':' Expression
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 23: Listas - Miembro Individual de Objeto
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer miembros como: nombre: "Ana", edad: 25, activo: verdadero, "first-name": "Ana"
 *
 * @see ObjectMemberNode
 */
public class ObjectMemberParser implements IParser<ObjectMemberNode> {

    private ExpressionParser expressionParser;

    public ObjectMemberParser() {
        // Lazy initialization to avoid circular dependency
    }
    
    private ExpressionParser getExpressionParser() {
        if (expressionParser == null) {
            expressionParser = new ExpressionParser();
        }
        return expressionParser;
    }

    @Override
    public ObjectMemberNode parse(ParserContext context) throws Parser.ParseException {
        Token keyToken = context.getCurrentToken();
        String key;
        boolean keyIsString;
        
        if (context.check(TokenType.IDENTIFIER)) {
            keyToken = context.consume(TokenType.IDENTIFIER);
            key = keyToken.getValue();
            keyIsString = false;
        } else if (context.check(TokenType.STRING_LITERAL)) {
            keyToken = context.consume(TokenType.STRING_LITERAL);
            key = keyToken.getValue();
            keyIsString = true;
        } else {
            throw new Parser.ParseException("Expected IDENTIFIER or STRING_LITERAL for object key but found " + 
                                           (keyToken != null ? keyToken.getType() : "EOF"));
        }
        
        context.consumeValue(":");
        
        ExpressionNode value = getExpressionParser().parse(context);
        
        return new ObjectMemberNode(key, keyIsString, value);
    }
}
