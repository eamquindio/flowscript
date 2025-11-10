package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ContinueStatementNode;

/**
 * Parser for continue statements.
 *
 * <h3>Grammar BNF:</h3>
 * <pre>
 * ContinueStatement ::= 'continue'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 3: Control de Flujo - Transferencia de Control
 *
 * <h3>Descripción:</h3>
 * Parser para la sentencia continue que permite saltar a la siguiente iteración de un bucle.
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Saltar elementos inválidos
 * function process_valid_items(items: list) {
 *     for each item in items {
 *         if not is_valid(item) {
 *             continue  // Salta a la siguiente iteración
 *         }
 *         process(item)
 *     }
 * }
 *
 * // Continue en while
 * i = 0
 * while i < 100 {
 *     i = i + 1
 *     if i % 2 == 0 {
 *         continue  // Salta números pares
 *     }
 *     imprimir(i)  // Solo imprime impares
 * }
 *
 * // Continue con condiciones múltiples
 * for each user in users {
 *     if user.inactive {
 *         continue
 *     }
 *     if user.blocked {
 *         continue
 *     }
 *     send_notification(user)
 * }
 *
 * // Continue en bucle anidado (afecta solo al bucle interno)
 * for each row in matrix {
 *     for each col in row {
 *         if col < 0 {
 *             continue  // Siguiente columna
 *         }
 *         process(col)
 *     }
 * }
 * </pre>
 *
 * <h3>Validación Semántica:</h3>
 * El SemanticAnalyzer debe verificar que:
 * - continue solo se usa dentro de while o for loops
 * - continue NO puede usarse en el scope raíz de funciones
 * - continue NO puede usarse en process tasks
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ContinueStatementParser parser = new ContinueStatementParser();
 * ContinueStatementNode node = parser.parse(context);
 * </pre>
 *
 * @see ContinueStatementNode
 */
public class ContinueStatementParser implements IParser<ContinueStatementNode> {

    @Override
    public ContinueStatementNode parse(ParserContext context) throws Parser.ParseException {
        // Simply consume 'continue' keyword
        Token continueToken = context.consume(TokenType.CONTINUE);

        // Create and return ContinueStatementNode
        return new ContinueStatementNode(continueToken);
    }
}
