package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.BreakStatementNode;

/**
 * Parser for break statements.
 *
 * <h3>Grammar BNF:</h3>
 * <pre>
 * BreakStatement ::= 'break'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 3: Control de Flujo - Transferencia de Control
 *
 * <h3>Descripción:</h3>
 * Parser para la sentencia break que permite salir anticipadamente de bucles (while, for).
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Búsqueda con break
 * function find_item(items: list, target: integer) -> integer {
 *     index = 0
 *     while index < items.length() {
 *         if items[index] == target {
 *             break
 *         }
 *         index = index + 1
 *     }
 *     return index
 * }
 *
 * // For con break
 * for each item in collection {
 *     if item.is_valid() {
 *         process(item)
 *     } else {
 *         break
 *     }
 * }
 *
 * // Break en bucle infinito
 * while true {
 *     data = get_next_data()
 *     if data == null {
 *         break
 *     }
 *     process(data)
 * }
 *
 * // Break en bucle anidado (sale del bucle interno)
 * for each row in matrix {
 *     for each col in row {
 *         if col == 0 {
 *             break  // Solo sale del bucle interno
 *         }
 *     }
 * }
 * </pre>
 *
 * <h3>Validación Semántica:</h3>
 * El SemanticAnalyzer debe verificar que:
 * - break solo se usa dentro de while o for loops
 * - break NO puede usarse en el scope raíz de funciones
 * - break NO puede usarse en process tasks (usar go_to en su lugar)
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * BreakStatementParser parser = new BreakStatementParser();
 * BreakStatementNode node = parser.parse(context);
 * </pre>
 *
 * @see BreakStatementNode
 */
public class BreakStatementParser implements IParser<BreakStatementNode> {

    @Override
    public BreakStatementNode parse(ParserContext context) throws Parser.ParseException {
        // Simply consume 'break' keyword
        Token breakToken = context.consume(TokenType.BREAK);

        // Create and return BreakStatementNode
        return new BreakStatementNode(breakToken);
    }
}
