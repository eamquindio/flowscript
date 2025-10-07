package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para listas de statements.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * StatementList ::= Statement*
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Lista vacía (bloque vacío)
 * {
 * }
 *
 * // Un solo statement
 * {
 *     x = 10
 * }
 *
 * // Múltiples statements
 * {
 *     x = 10
 *     y = 20
 *     z = x + y
 *     imprimir(z)
 * }
 *
 * // Statements de diferentes tipos
 * {
 *     nombre = "Juan"              // Variable declaration
 *     if nombre != "" {            // If statement
 *         imprimir(nombre)         // Expression statement
 *     }
 *     for each letra in nombre {   // For statement
 *         imprimir(letra)
 *     }
 *     return nombre.length         // Return statement
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * StatementListParser parser = new StatementListParser();
 * List&lt;StatementNode&gt; statements = parser.parse(context);
 * </pre>
 *
 * <h3>Nota:</h3>
 * Este parser no implementa IParser porque retorna una lista, no un nodo AST.
 *
 * @see StatementNode
 * @see StatementParser
 */
public class StatementListParser {

    private final StatementParser statementParser;

    public StatementListParser() {
        this.statementParser = new StatementParser();
    }

    public List<StatementNode> parse(ParserContext context) throws Parser.ParseException {
        return null;
    }
}
