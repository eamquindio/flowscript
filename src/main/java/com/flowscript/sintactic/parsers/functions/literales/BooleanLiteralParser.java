package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.BooleanLiteralNode;

/**
 * Parser para literales booleanos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * BooleanLiteral ::= 'true' | 'false' | 'verdadero' | 'falso'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 15: Literales - Booleanos
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer literales booleanos en inglés (true/false) y español (verdadero/falso).
 *
 * @see BooleanLiteralNode
 */
public class BooleanLiteralParser implements IParser<BooleanLiteralNode> {

    @Override
    public BooleanLiteralNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token token = context.getCurrentToken();
        
        if (token == null) {
            throw new Parser.ParseException("Expected boolean literal but reached end of input");
        }
        
        com.flowscript.lexer.TokenType type = token.getType();
        if (type != com.flowscript.lexer.TokenType.TRUE && 
            type != com.flowscript.lexer.TokenType.FALSE) {
            throw new Parser.ParseException("Expected boolean literal but found " + type);
        }
        
        context.advance();
        return new BooleanLiteralNode(token);
    }
}
