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

    private final StatementParser statementParser;

    public TaskElementParser() {
        this.statementParser = new StatementParser();
    }

    @Override
    public TaskElementNode parse(ParserContext context) throws Parser.ParseException {
        Token taskToken = context.getCurrentToken();
        String keyword = taskToken.getValue();

        if (!keyword.equals("tarea") && !keyword.equals("task")) {
            throw new Parser.ParseException(
                "Expected 'task' or 'tarea' but found '" + keyword +
                "' at line " + taskToken.getLine()
            );
        }
        context.consume();

        Token nameToken = context.consume(TokenType.IDENTIFIER);
        String taskName = nameToken.getValue();

        context.consume(TokenType.LEFT_BRACE);

        Token actionKeyword = context.getCurrentToken();
        if (actionKeyword == null ||
            (!actionKeyword.getValue().equals("accion:") && !actionKeyword.getValue().equals("action:"))) {
            throw new Parser.ParseException(
                "Expected 'action:' or 'accion:' in task body at line " +
                (actionKeyword != null ? actionKeyword.getLine() : taskToken.getLine())
            );
        }
        context.consume();

        List<StatementNode> statements = parseStatementList(context);

        context.consume(TokenType.RIGHT_BRACE);

        return new TaskElementNode(taskToken, taskName, statements);
    }

    private List<StatementNode> parseStatementList(ParserContext context) throws Parser.ParseException {
        List<StatementNode> statements = new ArrayList<>();

        while (context.getCurrentToken() != null &&
               context.getCurrentToken().getType() != TokenType.RIGHT_BRACE &&
               context.getCurrentToken().getType() != TokenType.EOF) {

            StatementNode stmt = statementParser.parse(context);
            statements.add(stmt);
        }

        return statements;
    }
}
