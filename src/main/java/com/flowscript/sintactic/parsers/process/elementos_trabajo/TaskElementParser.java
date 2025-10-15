package com.flowscript.sintactic.parsers.process.elementos_trabajo;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.process.estructura_principal.TaskElementNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;

/**
 * Parser para elementos de tarea dentro de un proceso.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * TaskElement ::= 'task' IDENTIFIER '{' TaskBody '}'
 * TaskBody ::= 'action:' StatementList
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 3: Elementos de Trabajo
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Tarea simple
 * task CargarCliente {
 *     action:
 *         cliente = db.get("clientes", entrada.id)
 *         go_to ValidarCliente
 * }
 *
 * // Tarea con lógica condicional
 * task ValidarMonto {
 *     action:
 *         if entrada.monto > 10000 {
 *             go_to RequiereAprobacion
 *         } else {
 *             go_to AprobacionAutomatica
 *         }
 * }
 * </pre>
 *
 * @see TaskElementNode
 */
public class TaskElementParser implements IParser<TaskElementNode> {

    private final StatementParser statementParser;

    public TaskElementParser() {
        this.statementParser = new StatementParser();
    }

    @Override
    public TaskElementNode parse(ParserContext context) throws Parser.ParseException {
        Token taskToken = context.consume(TokenType.TASK);
        Token nameToken = context.consume(TokenType.IDENTIFIER);
        context.consume(TokenType.LEFT_BRACE);
        context.consume(TokenType.ACTION);
        context.consume(TokenType.COLON);
        List<StatementNode> stmts = parseStatementList(context);
        context.consume(TokenType.RIGHT_BRACE);
        return new TaskElementNode(taskToken, nameToken.getValue(), stmts);
    }

    private List<StatementNode> parseStatementList(ParserContext context) throws Parser.ParseException {
        List<StatementNode> stmts = new ArrayList<>();

        while (context.hasMoreTokens() && !context.check(TokenType.RIGHT_BRACE)) {
            stmts.add(statementParser.parse(context));
        }

        if (stmts.isEmpty()) {
            Token curToken = context.getCurrentToken();
            throw new Parser.ParseException("Expected at least one statement in task action at line " + (curToken != null ? curToken.getLine() : -1));
        }

        return stmts;
    }
}
