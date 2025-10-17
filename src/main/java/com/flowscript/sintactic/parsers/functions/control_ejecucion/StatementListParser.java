package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
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
        List<StatementNode> statements = new ArrayList<>();

        while (context.hasMoreTokens()) {
            if (context.check(TokenType.RIGHT_BRACE)) {
                break;
            }

            StatementNode stmt = statementParser.parse(context);

            if (stmt != null) {
                statements.add(stmt);
            } else {
                Token current = context.getCurrentToken();
                if (current == null || current.getType() == TokenType.RIGHT_BRACE) break;

                throw new Parser.ParseException(
                        "token inesperado en la lista de sentencias en la linea " +
                    (current != null ? current.getLine() : -1) +
                            ", value: " + (current != null ? current.getValue() : "null")
                );
            }
        }
        return statements;
    }
}
