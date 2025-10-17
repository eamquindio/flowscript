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
public class LiteralParser implements IParser<com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode> {

    private final IntegerLiteralParser integerParser;
    private final DecimalLiteralParser decimalParser;
    private final BooleanLiteralParser booleanParser;
    private final StringLiteralParser stringParser;
    private final NullLiteralParser nullParser;
    private final ObjectLiteralParser objectParser;
    private final ListLiteralParser listParser;

    public LiteralParser() {
        this.integerParser = new IntegerLiteralParser();
        this.decimalParser = new DecimalLiteralParser();
        this.booleanParser = new BooleanLiteralParser();
        this.stringParser = new StringLiteralParser();
        this.nullParser = new NullLiteralParser();
        this.objectParser = new ObjectLiteralParser();
        this.listParser = new ListLiteralParser();
    }

    @Override
    public com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token token = context.getCurrentToken();
        
        if (token == null) {
            throw new Parser.ParseException("Expected literal but reached end of input");
        }
        
        com.flowscript.lexer.TokenType type = token.getType();
        
        if (type == com.flowscript.lexer.TokenType.INTEGER_LITERAL) {
            return integerParser.parse(context);
        } else if (type == com.flowscript.lexer.TokenType.DECIMAL_LITERAL) {
            return decimalParser.parse(context);
        } else if (type == com.flowscript.lexer.TokenType.TRUE || 
                   type == com.flowscript.lexer.TokenType.FALSE) {
            return booleanParser.parse(context);
        } else if (type == com.flowscript.lexer.TokenType.STRING_LITERAL) {
            return stringParser.parse(context);
        } else if (type == com.flowscript.lexer.TokenType.NULL) {
            return nullParser.parse(context);
        } else if (type == com.flowscript.lexer.TokenType.LEFT_BRACE) {
            return objectParser.parse(context);
        } else if (type == com.flowscript.lexer.TokenType.LEFT_BRACKET) {
            return listParser.parse(context);
        } else {
            throw new Parser.ParseException("Expected literal but found " + type);
        }
    }
}
