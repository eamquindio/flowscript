package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.WhileStatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;

/**
 * Parser for while loop statements.
 *
 * <h3>Grammar BNF:</h3>
 * <pre>
 * WhileStatement ::= 'while' Expression Statement
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 3: Control de Flujo
 *
 * <h3>Descripción:</h3>
 * Parser para bucles while que ejecutan un bloque de código mientras una condición sea verdadera.
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // While básico
 * i = 0
 * while i < 10 {
 *     imprimir(i)
 *     i = i + 1
 * }
 *
 * // While con condición compleja
 * while x > 0 and y < 100 {
 *     x = x - 1
 *     y = y * 2
 * }
 *
 * // While con break (cuando se implemente)
 * while true {
 *     if condition {
 *         break
 *     }
 *     process()
 * }
 *
 * // While en función
 * function factorial(n: integer) -> integer {
 *     result = 1
 *     i = n
 *     while i > 1 {
 *         result = result * i
 *         i = i - 1
 *     }
 *     return result
 * }
 *
 * // While en proceso
 * task ProcessItems {
 *     action:
 *         index = 0
 *         while index < entrada.items.length() {
 *             process_item(entrada.items[index])
 *             index = index + 1
 *         }
 *         go_to Complete
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * WhileStatementParser parser = new WhileStatementParser();
 * WhileStatementNode node = parser.parse(context);
 * </pre>
 *
 * @see WhileStatementNode
 */
public class WhileStatementParser implements IParser<WhileStatementNode> {

    private final ExpressionParser expressionParser;
    private StatementParser statementParser;

    public WhileStatementParser() {
        this.expressionParser = new ExpressionParser();
        // Lazy initialization to avoid circular dependency
    }

    private StatementParser getStatementParser() {
        if (statementParser == null) {
            statementParser = new StatementParser();
        }
        return statementParser;
    }

    @Override
    public WhileStatementNode parse(ParserContext context) throws Parser.ParseException {
        // 1. Consume 'while'
        Token whileToken = context.consume(TokenType.WHILE);

        // 2. Parse condition expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode condition =
            expressionParser.parse(context);

        // 3. Parse body statement
        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode body =
            getStatementParser().parse(context);

        // 4. Create and return WhileStatementNode
        return new WhileStatementNode(whileToken, condition, body);
    }
}
