package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import java.util.ArrayList;
import java.util.List;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;

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

    @Override
    public ListLiteralNode parse(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (token == null || token.getType() != TokenType.LEFT_BRACKET) {
            throw new Parser.ParseException("Expected '[' at the beginning of list literal");
        }

        context.consume();

        token = context.getCurrentToken();

        if (token == null) {
            throw new Parser.ParseException("Unexpected end of input after '['");
        }

        if (token.getType() == TokenType.RIGHT_BRACKET) {
            context.consume();
            return new ListLiteralNode(token); // Node with no expressions
        }

        var exprListParser = new com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser();
        var expressionListNode = exprListParser.parse(context);

        token = context.getCurrentToken();
        if (token == null || token.getType() != TokenType.RIGHT_BRACKET) {
            throw new Parser.ParseException("Expected ']' at the end of list literal");
        }


        context.consume();

        return new ListLiteralNode(token);
    }

}
