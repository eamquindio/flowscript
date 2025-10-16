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
        if(taskToken == null || taskToken.getType() != TokenType.TASK){
            throw new Parser.ParseException("Se esperaba _task_ al inicio del elemento.");
        }
        context.consume(TokenType.TASK);
        Token desName = context.consume(TokenType.IDENTIFIER);
        context.consume(TokenType.LEFT_BRACE);
        context.consume(TokenType.ACTION);
        context.consume(TokenType.COLON);
        List<StatementNode> body = parseStatementList(context);
        context.consume(TokenType.RIGHT_BRACE);
        return new TaskElementNode(taskToken, desName.getValue(), body);
    }

    private List<StatementNode> parseStatementList(ParserContext context) throws Parser.ParseException {
        List<StatementNode> statements = new ArrayList<>();
        while(true){
            Token token = context.getCurrentToken();
            if (token.getType() == TokenType.RIGHT_BRACE || token.getType() == TokenType.EOF) {
                break;
            }
            if(context.checkAny(TokenType.SINGLE_LINE_COMMENT, TokenType.MULTI_LINE_COMMENT, TokenType.COLON)){
                context.consume();
                continue;
            }
            StatementNode statement = statementParser.parse(context);
            statements.add(statement);
        }
        return statements;
    }
}
