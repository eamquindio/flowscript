package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
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
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Fin de entrada inesperado en LiteralBooleano");
        }

        String value = current.getValue();

        if (value.equals("true") || value.equals("verdadero")
                || value.equals("false") || value.equals("falso")) {

            context.advance(); 
            return new BooleanLiteralNode(current);
        }

        throw new Parser.ParseException(
                "Se esperaba literal booleano ('true', 'false', 'verdadero', 'falso') pero se encontro '" +
                        value + "' en linea " + current.getLine() + ", columna " + current.getColumn()
        );
    }
}
