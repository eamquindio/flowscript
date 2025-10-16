package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.DecimalLiteralNode;

/**
 * Parser para literales decimales.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * DecimalLiteral ::= INTEGER_TOKEN '.' INTEGER_TOKEN
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 14: Literales - Decimales
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer literales decimales como: 3.14, 0.5, 1.23e-5
 *
 * @see DecimalLiteralNode
 */
public class DecimalLiteralParser implements IParser<DecimalLiteralNode> {

    @Override
    public DecimalLiteralNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token token = context.consume(com.flowscript.lexer.TokenType.DECIMAL_LITERAL);
        return new DecimalLiteralNode(token);
    }
}
