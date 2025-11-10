package com.flowscript.sintactic.parsers.process.elementos_trabajo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.TaskElementNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;

import java.util.ArrayList;
import java.util.List;

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

    private StatementParser statementParser;

    public TaskElementParser() {
        // Lazy initialization to avoid circular dependency
    }

    private StatementParser getStatementParser() {
        if (statementParser == null) {
            statementParser = new StatementParser();
        }
        return statementParser;
    }

    @Override
    public TaskElementNode parse(ParserContext context) throws Parser.ParseException {
        // Consume 'task'
        Token taskToken = context.consume(TokenType.TASK);

        // Consume task name (IDENTIFIER)
        Token nameToken = context.consume(TokenType.IDENTIFIER);
        String taskName = nameToken.getValue();

        // Consume '{'
        context.consume(TokenType.LEFT_BRACE);

        // Consume 'action:'
        context.consume(TokenType.ACTION);
        context.consume(TokenType.COLON);

        // Parse statement list
        List<StatementNode> actionStatements = parseStatementList(context);

        // Consume '}'
        context.consume(TokenType.RIGHT_BRACE);

        return new TaskElementNode(taskToken, taskName, actionStatements);
    }

    private List<StatementNode> parseStatementList(ParserContext context) throws Parser.ParseException {
        List<StatementNode> statements = new ArrayList<>();

        // Parse statements until we hit closing brace
        while (context.getCurrentToken() != null &&
               context.getCurrentToken().getType() != TokenType.RIGHT_BRACE) {
            StatementNode statement = getStatementParser().parse(context);
            statements.add(statement);
        }

        return statements;
    }
}
