// package com.flowscript.sintactic.parsers.functions.listas_argumentos;

// import com.flowscript.lexer.Token;
// import com.flowscript.lexer.TokenType;
// import com.flowscript.sintactic.IParser;
// import com.flowscript.sintactic.Parser;
// import com.flowscript.sintactic.Parser.ParseException;
// import com.flowscript.sintactic.ParserContext;
// import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
// import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
// import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

// /**
//  * Parser para un miembro de objeto (par clave-valor).
//  *
//  * <h3>Gramática BNF:</h3>
//  * 
//  * <pre>
//  * ObjectMember ::= IDENTIFIER ':' Expression
//  * </pre>
//  *
//  * <h3>Categoría:</h3>
//  * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
//  * Nivel 23: Listas - Miembro Individual de Objeto
//  *
//  * <h3>Tarea del Estudiante:</h3>
//  * Implementar el método {@code parse()} siguiendo la gramática BNF.
//  * Debe reconocer miembros como: nombre: "Ana", edad: 25, activo: verdadero
//  *
//  * @see ObjectMemberNode
//  */
// public class ObjectMemberParser implements IParser<ObjectMemberNode> {

//     private final ExpressionParser expressionParser;

//     public ObjectMemberParser() {
//         this.expressionParser = new ExpressionParser();
//     }

//     @Override
//     public ObjectMemberNode parse(ParserContext context) throws ParseException {
//         Token keyToken = context.getCurrentToken();

//         boolean keyIsString;

//         if (keyToken.getType() == TokenType.IDENTIFIER) {
//             keyIsString = false;
//             context.consume(TokenType.IDENTIFIER);
//         } else if (keyToken.getType() == TokenType.STRING_LITERAL) {
//             keyIsString = true;
//             context.consume(TokenType.STRING_LITERAL);
//         } else {
//             throw new Parser.ParseException(
//                     "Se esperaba un IDENTIFICADOR o una CADENA como clave del miembro de objeto, pero se encontró: "
//                             + keyToken);
//         }

//         Token colonToken = context.getCurrentToken();
//         if (colonToken.getType() != TokenType.COLON) {
//             throw new Parser.ParseException(
//                     "Se esperaba ':' después de la clave del miembro de objeto, pero se encontró: " + colonToken);
//         }
//         context.consume(TokenType.COLON);

//         ExpressionNode value = expressionParser.parse(context);

//         return new ObjectMemberNode(keyToken.getValue(), keyIsString, value);
//     }
// }

package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para un miembro de objeto (par clave-valor).
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * ObjectMember ::= ( IDENTIFIER | STRING_LITERAL ) ':' Expression
 * </pre>
 *
 * <p>
 * Reconoce miembros como: nombre: "Ana", edad: 25, activo: verdadero
 * </p>
 *
 * @see ObjectMemberNode
 */
public class ObjectMemberParser implements IParser<ObjectMemberNode> {
    private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();

    @Override
    public ObjectMemberNode parse(ParserContext context) throws ParseException {
        Token keyToken = context.getCurrentToken();
        TokenType keyType = keyToken.getType();

        boolean keyIsString;
        if (keyType == TokenType.IDENTIFIER) {
            keyIsString = false;
            context.advance();
        } else if (keyType == TokenType.STRING_LITERAL) {
            keyIsString = true;
            context.advance();
        } else {
            throw new ParseException("Se esperaba un identificador o cadena");
        }

        context.consume(TokenType.COLON);
        ExpressionNode value = EXPRESSION_PARSER.parse(context);

        return new ObjectMemberNode(keyToken.getValue(), keyIsString, value);
    }
}