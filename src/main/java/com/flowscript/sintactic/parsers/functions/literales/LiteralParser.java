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
 * 
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

    private final IntegerLiteralParser integerLiteralParser;
    private final DecimalLiteralParser decimalLiteralParser;
    private final BooleanLiteralParser booleanLiteralParser;
    private final StringLiteralParser stringLiteralParser;
    private final NullLiteralParser nullLiteralParser;
    private final ObjectLiteralParser objectLiteralParser;
    private final ListLiteralParser listLiteralParser;

    public LiteralParser() {
        this.integerLiteralParser = new IntegerLiteralParser();
        this.decimalLiteralParser = new DecimalLiteralParser();
        this.booleanLiteralParser = new BooleanLiteralParser();
        this.stringLiteralParser = new StringLiteralParser();
        this.nullLiteralParser = new NullLiteralParser();
        this.objectLiteralParser = new ObjectLiteralParser();
        this.listLiteralParser = new ListLiteralParser();
    }

    @Override
    public LiteralNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token current = context.peek();
        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing literal");
        }

        switch (current.getType()) {
            case INTEGER_LITERAL:
                return new LiteralNode(integerLiteralParser.parse(context).getToken());

            case DECIMAL_LITERAL:
                return new LiteralNode(decimalLiteralParser.parse(context).getToken());

            case TRUE:
            case FALSE:
            case VERDADERO:
            case FALSO:
                return new LiteralNode(booleanLiteralParser.parse(context).getToken());

            case STRING_LITERAL:
                return new LiteralNode(stringLiteralParser.parse(context).getToken());

            case NULL:
            case NULO:
                return new LiteralNode(nullLiteralParser.parse(context).getToken());

            case LEFT_BRACE:
                var objNode = objectLiteralParser.parse(context);
                return new LiteralNode(objNode.getToken());

            case LEFT_BRACKET:
                var listNode = listLiteralParser.parse(context);
                return new LiteralNode(listNode.getToken());

            default:
                throw new Parser.ParseException("Unexpected token in literal: " + current.getType());
        }  

    }
}
