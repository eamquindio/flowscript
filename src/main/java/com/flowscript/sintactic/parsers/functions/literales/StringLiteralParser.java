package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.StringLiteralNode;
import com.flowscript.lexer.Token;

/**
 * Parser para literales de cadena de texto.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * StringLiteral ::= STRING_LITERAL
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 16: Literales - Cadenas
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer literales de cadena como: "Hola mundo", "Línea 1\nLínea 2"
 *
 * @see StringLiteralNode
 */
public class StringLiteralParser implements IParser<StringLiteralNode> {

    @Override
    public StringLiteralNode parse(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (token == null) {
            throw new Parser.ParseException("Expected string literal but reached end of input");
        }

        if (token.getType() != com.flowscript.lexer.TokenType.STRING_LITERAL) {
            throw new Parser.ParseException(
                "Expected string literal but found '" + token.getValue() +
                "' at line " + token.getLine()
            );
        }

        context.consume(); // Consume the string literal
        return new StringLiteralNode(token);
    }
}
