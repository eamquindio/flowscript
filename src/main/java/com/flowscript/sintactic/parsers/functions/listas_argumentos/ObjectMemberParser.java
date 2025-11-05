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
        Token t = context.getCurrentToken();

        if (t == null) {
            throw new Parser.ParseException("No hay token para la clave del objeto");
        }
        if (t.getType() != TokenType.IDENTIFIER && t.getType() != TokenType.STRING_LITERAL) {
            throw new Parser.ParseException("Esperaba un nombre o una cadena, no eso");
        }

        context.consume();
        if (!context.check(TokenType.COLON)) {
            throw new Parser.ParseException("Faltan los dos puntos ':' después de la clave");
        }
        context.consume(TokenType.COLON);

        ExpressionNode valor;
        valor = new ExpressionParser().parse(context);
        boolean esCadena = (t.getType() == TokenType.STRING_LITERAL);

        ObjectMemberNode nodo = new ObjectMemberNode(t.getValue(), esCadena, valor);
        return nodo;
    }
}
