package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

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
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing ObjectMember");
        }

        String key;
        boolean keyIsString = false;

        if (current.getType() == TokenType.IDENTIFIER) {
            key = current.getValue();
            context.consume();
        } else if (current.getType() == TokenType.STRING_LITERAL) {
            key = current.getValue();
            keyIsString = true;
            context.consume();
        } else {
            throw new Parser.ParseException(
                "Expected identifier or string for object key but found '" + current.getValue() +
                "' at line " + current.getLine() + ", column " + current.getColumn()
            );
        }

        Token colon = context.getCurrentToken();
        if (colon == null || colon.getType() != TokenType.COLON) {
            throw new Parser.ParseException(
                "Expected ':' after key '" + key + "' but found " +
                (colon == null ? "end of input" : "'" + colon.getValue() + "'") +
                " at line " + (colon == null ? current.getLine() : colon.getLine())
            );
        }
        context.consume();
        ExpressionParser exprParser = new ExpressionParser();
        ExpressionNode valueNode = exprParser.parse(context);

        return new ObjectMemberNode(key, keyIsString, valueNode);
    }
}
