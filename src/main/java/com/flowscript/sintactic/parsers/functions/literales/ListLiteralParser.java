package com.flowscript.sintactic.parsers.functions.literales;

import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser;

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
        Token leftBracket = context.consume(TokenType.LEFT_BRACKET);

        if (context.check(TokenType.RIGHT_BRACKET)) {
            context.consume(TokenType.RIGHT_BRACKET);
            return new ListLiteralNode(leftBracket); 
        }

        ExpressionListParser exprListParser = new ExpressionListParser();
        List<ExpressionListNode> exprLists = exprListParser.parse(context);

        context.consume(TokenType.RIGHT_BRACKET);

        return new ListLiteralNode(leftBracket, exprLists.get(0));
    }
}
