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
            throw new Parser.ParseException("Expected 'null' but found end of input.");
        }

        String value = current.getValue();

        if (current.getType() == TokenType.NULL ||
            "null".equals(value) ||   // exact match, case-sensitive
            "nulo".equals(value)) {   // exact match, case-sensitive
            context.consume();
            return new NullLiteralNode(current);
        }

        // Si llega aquí, el token no es un literal nulo válido
        throw new Parser.ParseException(
            "Expected 'null' literal but found: " + current.getValue()
        );
    }
}
