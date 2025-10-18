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
        // 🔹 1. Consumimos el '[' inicial
        Token leftBracket = context.consume(TokenType.LEFT_BRACKET);

        // 🔹 2. Si el siguiente token es ']', es una lista vacía
        if (context.check(TokenType.RIGHT_BRACKET)) {
            context.consume(TokenType.RIGHT_BRACKET);
            return new ListLiteralNode(leftBracket); // lista vacía
        }

        // 🔹 3. Si no está vacía, parseamos la lista de expresiones
        ExpressionListParser exprListParser = new ExpressionListParser();
        List<ExpressionListNode> exprLists = exprListParser.parse(context);

        // 🔹 4. Consumimos el ']' de cierre obligatorio
        context.consume(TokenType.RIGHT_BRACKET);

        // 🔹 5. Creamos y devolvemos el nodo del literal de lista
        return new ListLiteralNode(leftBracket, exprLists.get(0));
    }
}
