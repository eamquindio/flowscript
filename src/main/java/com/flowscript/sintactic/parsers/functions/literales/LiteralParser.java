package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
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
        Token current = context.getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing Literal");
        }

        TokenType type = current.getType();

        switch (type) {
            case INTEGER_LITERAL:
                return new IntegerLiteralParser().parse(context);
            case DECIMAL_LITERAL:
                return new DecimalLiteralParser().parse(context);
            case STRING_LITERAL:
                return new StringLiteralParser().parse(context);
            case TRUE:
            case FALSE:
                return new BooleanLiteralParser().parse(context);
            case NULL:
                return new NullLiteralParser().parse(context);
            case LEFT_BRACE:
                return new ObjectLiteralParser().parse(context);
            case LEFT_BRACKET:
                return new ListLiteralParser().parse(context);
            default:
                throw new Parser.ParseException(
                    "Unexpected token in Literal: " + current.getValue() +
                    " (" + type + ") at line " + current.getLine() + ", column " + current.getColumn()
                );
        }
    }

}