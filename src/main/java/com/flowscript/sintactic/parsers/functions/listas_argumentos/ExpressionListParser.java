package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;

import java.util.List;

/**
 * Parser para listas de expresiones.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ExpressionList ::= Expression ( ',' Expression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 21: Listas - Expresiones
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer listas de expresiones como las usadas en literales de lista: [1, 2, 3]
 * Nota: Este parser NO implementa IParser porque retorna una List.
 *
 * @see ExpressionListNode
 */
public class ExpressionListParser {

    private final com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser expressionParser;

    public ExpressionListParser() {
        this.expressionParser = new com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser();
    }

    /**
     * Parsea una lista de expresiones.
     *
     * @param context El contexto del parser
     * @return UN SOLO ExpressionListNode que contiene todas las expresiones
     * @throws Parser.ParseException Si hay un error de sintaxis
     */
    public ExpressionListNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstExpr = expressionParser.parse(context);
        ExpressionListNode exprList = new ExpressionListNode(firstExpr);

        // Parse remaining expressions separated by comma
        while (context.getCurrentToken() != null &&
               context.getCurrentToken().getType() == com.flowscript.lexer.TokenType.COMMA) {
            context.consume(); // consume comma
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode expr = expressionParser.parse(context);
            exprList.addExpression(expr);
        }

        return exprList;
    }
}
