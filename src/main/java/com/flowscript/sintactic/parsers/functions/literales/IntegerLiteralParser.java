package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.IntegerLiteralNode;

/**
 * Parser para literales enteros.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * IntegerLiteral ::= INTEGER_TOKEN
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 13: Literales - Enteros
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer literales enteros como: 42, 1_000_000, 0, -123
 *
 * @see IntegerLiteralNode
 */
public class IntegerLiteralParser implements IParser<IntegerLiteralNode> {

    @Override
    public IntegerLiteralNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Fin de entrada inesperado mientras se analizaba literal entero");
        }

        if (current.getType() == TokenType.INTEGER_LITERAL) {
            context.consume(); 
            return new IntegerLiteralNode(current);
        }

        throw new Parser.ParseException(
            "Se esperaba literal entero pero se encontro '" + current.getValue() +
            "' (" + current.getType() + ") en linea " + current.getLine() +
            ", columna " + current.getColumn()
        );
    }
}
