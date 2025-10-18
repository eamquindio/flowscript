package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.StringLiteralNode;

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
        Token current = context.getCurrentToken();

        // 1️⃣ Validar que haya un token actual
        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing string literal");
        }

        // 2️⃣ Verificar que sea de tipo STRING_LITERAL
        if (current.getType() != TokenType.STRING_LITERAL) {
            throw new Parser.ParseException(
                "Expected string literal but found '" + current.getValue() +
                "' at line " + current.getLine() + ", column " + current.getColumn()
            );
        }

        // 3️⃣ Consumir el token
        context.consume();

        // 4️⃣ Crear el nodo AST para la cadena
        return new StringLiteralNode(current);
    }
}
