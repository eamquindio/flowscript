package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
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
public class LiteralParser implements IParser<ExpressionNode> {

    private final IntegerLiteralParser integerParser = new IntegerLiteralParser();
    private final DecimalLiteralParser decimalParser = new DecimalLiteralParser();
    private final BooleanLiteralParser booleanParser = new BooleanLiteralParser();
    private final StringLiteralParser stringParser = new StringLiteralParser();
    private final NullLiteralParser nullParser = new NullLiteralParser();
    private final ObjectLiteralParser objectParser = new ObjectLiteralParser();
    private final ListLiteralParser listParser = new ListLiteralParser();

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        Token t = context.getCurrentToken();

        if (t == null) {
            throw new Parser.ParseException("Literal esperado pero se acabaron los tokens");
        }

        TokenType tp = t.getType();

        if (tp == TokenType.INTEGER_LITERAL) return integerParser.parse(context);
        if (tp == TokenType.DECIMAL_LITERAL) return decimalParser.parse(context);
        if (tp == TokenType.TRUE || tp == TokenType.FALSE) return booleanParser.parse(context);
        if (tp == TokenType.STRING_LITERAL) return stringParser.parse(context);
        if (tp == TokenType.NULL) return nullParser.parse(context);
        if (tp == TokenType.LEFT_BRACE) return objectParser.parse(context);
        if (tp == TokenType.LEFT_BRACKET) return listParser.parse(context);

        throw new Parser.ParseException("Literal esperado, pero salió " + tp);
    }
}