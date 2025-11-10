package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

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
 * @see IntegerLiteralParser
 * @see DecimalLiteralParser
 * @see BooleanLiteralParser
 * @see StringLiteralParser
 * @see NullLiteralParser
 * @see ObjectLiteralParser
 * @see ListLiteralParser
 */
public class LiteralParser implements IParser<ExpressionNode> {

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
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Expected literal but reached end of input");
        }

        // Determine type and delegate to appropriate parser
        switch (current.getType()) {
            case INTEGER_LITERAL:
                return integerParser.parse(context);
            case DECIMAL_LITERAL:
                return decimalParser.parse(context);
            case TRUE:
            case FALSE:
                return booleanParser.parse(context);
            case STRING_LITERAL:
                return stringParser.parse(context);
            case NULL:
                return nullParser.parse(context);
            case LEFT_BRACE:
                return objectParser.parse(context);
            case LEFT_BRACKET:
                return listParser.parse(context);
            default:
                throw new Parser.ParseException(
                    "Expected literal (integer, decimal, boolean, string, null, object, or list) " +
                    "but found '" + current.getValue() + "' at line " + current.getLine()
                );
        }
    }
}
