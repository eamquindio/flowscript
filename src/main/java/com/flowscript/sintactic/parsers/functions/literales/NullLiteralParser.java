package com.flowscript.sintactic.parsers.functions.literales;

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
        if (context.check(com.flowscript.lexer.TokenType.NULL)) {
            com.flowscript.lexer.Token t = context.consume();
            return new NullLiteralNode(t);
        }
        throw new Parser.ParseException("Expected null literal at " + (context.getCurrentToken() != null ? context.getCurrentToken().getLine() + ":" + context.getCurrentToken().getColumn() : "end of input"));
    }
}
