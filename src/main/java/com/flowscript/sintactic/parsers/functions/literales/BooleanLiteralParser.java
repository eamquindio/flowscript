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
        // TODO: Implementar este métodoToken token = context.getCurrentToken();
        Token t = context.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba un booleano y ya no hay más tokens");
        }
        Token tokFinal;
        String v = t.getValue();
        if (v.equals("verdadero") || v.equals("falso")) {
            tokFinal = t;
        } else if (v.equals("true")) {
            tokFinal = new Token(t.getType(), "verdadero", t.getLine(), t.getColumn(), t.getPosition());
        } else if (v.equals("false")) {
            tokFinal = new Token(t.getType(), "falso", t.getLine(), t.getColumn(), t.getPosition());
        } else {
            throw new Parser.ParseException("Se esperaba 'verdadero' o 'falso'");
        }

        context.consume();
        BooleanLiteralNode res = new BooleanLiteralNode(tokFinal);
        return res;

    }
}
