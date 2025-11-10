package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;

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

    private final com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser expressionParser;

    public ArgumentListParser() {
        this.expressionParser = new com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser();
    }

    /**
     * Parsea una lista de argumentos.
     *
     * @param context El contexto del parser
     * @return ArgumentListNode con todos los argumentos
     * @throws Parser.ParseException Si hay un error de sintaxis
     */
    public ArgumentListNode parse(ParserContext context) throws Parser.ParseException {
        // Parse first argument
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode firstArg = expressionParser.parse(context);
        ArgumentListNode argumentList = new ArgumentListNode(firstArg);

        // Parse remaining arguments separated by comma
        while (context.getCurrentToken() != null &&
               context.getCurrentToken().getType() == com.flowscript.lexer.TokenType.COMMA) {
            context.consume(); // consume comma
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode arg = expressionParser.parse(context);
            argumentList.addArgument(arg);
        }

        return argumentList;
    }
}
