package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.expressions.ExpressionNode;
import java.util.List;



/**
 * Parser para listas de argumentos en llamadas a funciones.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ArgumentList ::= Expression ( ',' Expression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 20: Listas - Argumentos de Funciones
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer listas de argumentos como: (x, y, z), (42), ("hola", 3.14, verdadero)
 * Nota: Este parser NO implementa IParser porque retorna una List.
 *
 * @see ArgumentListNode
 */
public class ArgumentListParser {

    /**
     * Parsea una lista de argumentos.
     *
     * @param context El contexto del parser
     * @return Lista de nodos ArgumentListNode
     * @throws Parser.ParseException Si hay un error de sintaxis
     */
    public List<ArgumentListNode> parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
       List<ArgumentListNode> argumentLists = new ArrayList<>();

        if (context.match(TokenType.RIGHT_PAREN)) {
            return argumentLists;
        }

        ExpressionParser exprParser = new ExpressionParser();

        ExpressionNode firstArg = exprParser.parse(context);
        ArgumentListNode argListNode = new ArgumentListNode(firstArg);

        while (context.match(TokenType.COMMA)) {
            context.consume(TokenType.COMMA);

            if (context.match(TokenType.RIGHT_PAREN)) {
                throw new Parser.ParseException("Unexpected ',' before ')' in argument list");
            }

            ExpressionNode nextArg = exprParser.parse(context);
            argListNode.addArgument(nextArg);
        }

        argumentLists.add(argListNode);

        return argumentLists;

    }
}
