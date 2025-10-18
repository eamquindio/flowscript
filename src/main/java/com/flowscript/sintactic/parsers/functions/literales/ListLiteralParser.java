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

    @Override
    public ListLiteralNode parse(ParserContext context) throws Parser.ParseException {
        // Expect '['
        if (!context.check(com.flowscript.lexer.TokenType.LEFT_BRACKET)) {
            throw new Parser.ParseException("Expected '[' to start list literal");
        }
        com.flowscript.lexer.Token left = context.consume();

        // Empty list
        if (context.check(com.flowscript.lexer.TokenType.RIGHT_BRACKET)) {
            context.consume();
            return new ListLiteralNode(left);
        }

        // Parse first expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstExpr = new com.flowscript.sintactic.parsers.functions.expresiones.PrimaryExpressionParser().parse(context);
        com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode exprList = new com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode(firstExpr);

        // Parse additional elements separated by commas
        while (context.check(com.flowscript.lexer.TokenType.COMMA)) {
            context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode nextExpr = new com.flowscript.sintactic.parsers.functions.expresiones.PrimaryExpressionParser().parse(context);
            exprList.addExpression(nextExpr);
        }

        // Expect closing bracket
        if (!context.check(com.flowscript.lexer.TokenType.RIGHT_BRACKET)) {
            throw new Parser.ParseException("Expected ']' to close list literal");
        }
        context.consume();
        return new ListLiteralNode(left, exprList);
    }
}
