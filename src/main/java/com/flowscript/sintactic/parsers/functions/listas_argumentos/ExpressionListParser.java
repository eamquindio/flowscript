package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.TokenType;
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
        ArrayList<ExpressionNode> exprs = new ArrayList<>();


        ExpressionNode primero = new ExpressionParser().parse(context);
        exprs.add(primero);
        while (context.check(TokenType.COMMA)) {
            context.consume(TokenType.COMMA);
            ExpressionNode tmp = new ExpressionParser().parse(context);
            exprs.add(tmp);
        }

        ExpressionListNode nodo = new ExpressionListNode(primero.getToken(), exprs);

        ArrayList<ExpressionListNode> listaFinal = new ArrayList<>();
        listaFinal.add(nodo);
        return listaFinal;
    }
}
