package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.NullLiteralNode;

/**
 * Parser para literales nulos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * NullLiteral ::= 'null' | 'nulo'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 17: Literales - Nulos
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer el literal nulo en inglés (null) y español (nulo).
 *
 * @see NullLiteralNode
 */
public class NullLiteralParser implements IParser<NullLiteralNode> {

    @Override
    public NullLiteralNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Se esperaba 'null' pero se encontro fin de entrada.");
        }

        String value = current.getValue();

        if (current.getType() == TokenType.NULL ||
            "null".equals(value) ||   
            "nulo".equals(value)) {   
            context.consume();
            return new NullLiteralNode(current);
        }

        throw new Parser.ParseException(
            "Se esperaba literal 'null' pero se encontro: " + current.getValue()
        );
    }
}
