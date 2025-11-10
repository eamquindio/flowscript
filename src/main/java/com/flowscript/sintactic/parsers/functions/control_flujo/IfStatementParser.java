package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.IfStatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;

/**
 * Parser para statements if/else_if/else.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * IfStatement ::= 'if' Expression Statement ( 'else_if' Expression Statement )* ( 'else' Statement )?
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // If simple
 * if x > 10 {
 *     imprimir("Mayor que 10")
 * }
 *
 * // If-else
 * if x > 10 {
 *     imprimir("Mayor")
 * } else {
 *     imprimir("Menor o igual")
 * }
 *
 * // If-else_if-else
 * if x > 10 {
 *     imprimir("Mayor que 10")
 * } else_if x > 5 {
 *     imprimir("Entre 6 y 10")
 * } else {
 *     imprimir("5 o menor")
 * }
 *
 * // If anidado
 * if x > 0 {
 *     if x < 10 {
 *         imprimir("Entre 1 y 9")
 *     } else {
 *         imprimir("10 o más")
 *     }
 * }
 *
 * // If con expresiones complejas
 * if usuario.edad >= 18 and usuario.activo == verdadero {
 *     imprimir("Usuario adulto activo")
 * } else_if usuario.edad >= 18 {
 *     imprimir("Usuario adulto inactivo")
 * } else {
 *     imprimir("Usuario menor de edad")
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * IfStatementParser parser = new IfStatementParser();
 * IfStatementNode node = parser.parse(context);
 * </pre>
 *
 * @see IfStatementNode
 */
public class IfStatementParser implements IParser<IfStatementNode> {

    private final ExpressionParser expressionParser;
    private StatementParser statementParser;

    public IfStatementParser() {
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
    public IfStatementNode parse(ParserContext context) throws Parser.ParseException {
        // 1. Consume 'if'
        Token ifToken = context.consume(com.flowscript.lexer.TokenType.IF);

        // 2. Parse condition
        ExpressionNode condition = expressionParser.parse(context);

        // 3. Parse then statement
        StatementNode thenStatement = getStatementParser().parse(context);

        // 4. Create IfStatementNode
        IfStatementNode ifNode = new IfStatementNode(ifToken, condition, thenStatement);

        // 5. Parse else_if clauses
        while (context.getCurrentToken() != null &&
               context.getCurrentToken().getType() == com.flowscript.lexer.TokenType.ELSE_IF) {
            context.consume(); // consume 'else_if'
            ExpressionNode elseIfCondition = expressionParser.parse(context);
            StatementNode elseIfStatement = getStatementParser().parse(context);
            ifNode.addElseIfClause(elseIfCondition, elseIfStatement);
        }

        // 6. Parse optional else clause
        if (context.getCurrentToken() != null &&
            context.getCurrentToken().getType() == com.flowscript.lexer.TokenType.ELSE) {
            context.consume(); // consume 'else'
            StatementNode elseStatement = getStatementParser().parse(context);
            ifNode.setElseStatement(elseStatement);
        }

        return ifNode;
    }
}
