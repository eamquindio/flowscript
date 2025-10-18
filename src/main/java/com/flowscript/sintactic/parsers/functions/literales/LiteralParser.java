package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;

/**
 * Parser para literales (valores constantes).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Literal ::= IntegerLiteral
 *           | DecimalLiteral
 *           | BooleanLiteral
 *           | StringLiteral
 *           | NullLiteral
 *           | ObjectLiteral
 *           | ListLiteral
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 12: Literales - Dispatcher
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Este parser debe determinar qué tipo de literal es y delegar al parser específico.
 *
 * @see LiteralNode
 * @see IntegerLiteralParser
 * @see DecimalLiteralParser
 * @see BooleanLiteralParser
 * @see StringLiteralParser
 * @see NullLiteralParser
 * @see ObjectLiteralParser
 * @see ListLiteralParser
 */
public class LiteralParser implements IParser<LiteralNode> {

    @Override
    public LiteralNode parse(ParserContext context) throws Parser.ParseException {
        // Consume a literal token and return a generic LiteralNode
        if (context.check(com.flowscript.lexer.TokenType.INTEGER_LITERAL)
                || context.check(com.flowscript.lexer.TokenType.DECIMAL_LITERAL)
                || context.check(com.flowscript.lexer.TokenType.STRING_LITERAL)
                || context.check(com.flowscript.lexer.TokenType.TRUE)
                || context.check(com.flowscript.lexer.TokenType.FALSE)
                || context.check(com.flowscript.lexer.TokenType.NULL)) {
            com.flowscript.lexer.Token t = context.consume();
            return new LiteralNode(t);
        }

        throw new Parser.ParseException("Expected literal at " + (context.getCurrentToken() != null ? context.getCurrentToken().getLine() + ":" + context.getCurrentToken().getColumn() : "end of input"));
    }
}
