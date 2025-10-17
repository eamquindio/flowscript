package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.expressions.ExpressionNode;
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


    private final ExpressionListParser expressionListParser;

    public ListLiteralParser() {
        this.expressionListParser = new ExpressionListParser();
    }

    @Override
    public ListLiteralNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token leftBracket = context.expect(TokenType.LEFT_BRACKET, "Expected '[' to start list literal");

        if (context.match(TokenType.RIGHT_BRACKET)) {
            context.consume(TokenType.RIGHT_BRACKET);
            return new ListLiteralNode(leftBracket);
        }

        ExpressionListNode expressionList = null;
        try {
            var parsedLists = expressionListParser.parse(context);
            if (parsedLists != null && !parsedLists.isEmpty()) {
                expressionList = parsedLists.get(0);
            }
        } catch (Parser.ParseException e) {
            throw new Parser.ParseException("Error parsing expression list inside list literal: " + e.getMessage());
        }

        context.expect(TokenType.RIGHT_BRACKET, "Expected ']' to close list literal");

        return new ListLiteralNode(leftBracket, expressionList);

    }
}
