package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;

/**
 * Parser para literales de lista.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ListLiteral ::= '[' ExpressionList? ']'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 19: Literales - Listas
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer literales de lista como: [1, 2, 3], ["a", "b"], [1, "texto", verdadero]
 * Una lista vacía [] también es válida.
 *
 * @see ListLiteralNode
 * @see com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser
 */
public class ListLiteralParser implements IParser<ListLiteralNode> {

    private final com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser expressionListParser;

    public ListLiteralParser() {
        this.expressionListParser = new com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser();
    }

    @Override
    public ListLiteralNode parse(ParserContext context) throws Parser.ParseException {
        // Consume '['
        com.flowscript.lexer.Token leftBracket = context.consume(com.flowscript.lexer.TokenType.LEFT_BRACKET);

        // Check if empty list
        com.flowscript.lexer.Token next = context.getCurrentToken();
        if (next != null && next.getType() == com.flowscript.lexer.TokenType.RIGHT_BRACKET) {
            context.consume(); // consume ']'
            return new ListLiteralNode(leftBracket); // Empty list
        }

        // Parse expression list
        com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode exprList =
            expressionListParser.parse(context);

        // Consume ']'
        context.consume(com.flowscript.lexer.TokenType.RIGHT_BRACKET);

        return new ListLiteralNode(leftBracket, exprList);
    }
}
