package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

import java.util.ArrayList;
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

    /**
     * Parsea una lista de expresiones.
     *
     * @param context El contexto del parser
     * @return Lista de nodos ExpressionListNode
     * @throws Parser.ParseException Si hay un error de sintaxis
     */
    public List<ExpressionListNode> parse(ParserContext context) throws Parser.ParseException {
        List<ExpressionListNode> result = new ArrayList<>();

        ExpressionParser expressionParser = new ExpressionParser();

        if (context.getCurrentToken() == null) {
            return result;
        }

        ExpressionNode firstExpr = expressionParser.parse(context);
        ExpressionListNode exprList = new ExpressionListNode(firstExpr);

        while (context.getCurrentToken() != null && context.getCurrentToken().getType() == com.flowscript.lexer.TokenType.COMMA) {
            context.consume();
            ExpressionNode nextExpr = expressionParser.parse(context);
            exprList.addExpression(nextExpr);
        }

        result.add(exprList);
        return result;
    }
}
