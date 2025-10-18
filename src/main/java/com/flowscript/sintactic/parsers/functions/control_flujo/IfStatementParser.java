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
    private final StatementParser statementParser;

    public IfStatementParser() {
        this.expressionParser = new ExpressionParser();
        this.statementParser = new StatementParser();
    }

    @Override
    public IfStatementNode parse(ParserContext context) throws Parser.ParseException {
        Token ifToken = context.getCurrentToken();
        context.consume(); // consume 'if' or 'si'

        // Parse condición
        ExpressionNode condition = expressionParser.parse(context);

        // Parse then statement
        StatementNode thenStatement = statementParser.parse(context);

        IfStatementNode ifStmt = new IfStatementNode(ifToken, condition, thenStatement);

        // Parse else_if clauses
        while (context.checkValue("sino_si") || context.checkValue("else_if")) {
            context.consume(); // consume 'else_if' or 'sino_si'

            ExpressionNode elseIfCondition = expressionParser.parse(context);
            StatementNode elseIfStatement = statementParser.parse(context);

            ifStmt.addElseIfClause(elseIfCondition, elseIfStatement);
        }

        // Parse else clause opcional
        if (context.checkValue("sino") || context.checkValue("else")) {
            context.consume(); // consume 'else' or 'sino'
            StatementNode elseStatement = statementParser.parse(context);
            ifStmt.setElseStatement(elseStatement);
        }

        return ifStmt;
    }
}
