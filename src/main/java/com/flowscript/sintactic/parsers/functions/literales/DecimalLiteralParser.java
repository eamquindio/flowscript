package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
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
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing decimal literal");
        }

        if (current.getType() == TokenType.DECIMAL_LITERAL) {
            context.advance(); 
            return new DecimalLiteralNode(current);
        }

        throw new Parser.ParseException(
            "Expected decimal literal but found '" + current.getValue() + 
            "' (" + current.getType() + ") at line " + current.getLine() + 
            ", column " + current.getColumn()
        );
    }
}
