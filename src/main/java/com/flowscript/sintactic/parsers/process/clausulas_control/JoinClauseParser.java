package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.JoinClauseNode;

/**
 * Parser para cláusulas join en gateways paralelos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * JoinClause ::= 'join' '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 5: Cláusulas de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * join -> Consolidar
 * join -> FinalizarProceso
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 *   <li>Consumir 'join' o 'unir'</li>
 *   <li>Consumir '->'</li>
 *   <li>Consumir IDENTIFIER (nodo destino)</li>
 *   <li>Crear y retornar JoinClauseNode</li>
 * </ol>
 *
 * @see JoinClauseNode
 */
public class JoinClauseParser implements IParser<JoinClauseNode> {

    @Override
    public JoinClauseNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();
        if (current == null ||
            !(current.getValue().equalsIgnoreCase("join") )) {
            throw new Parser.ParseException("No habia una palabra clave JOIN");
        }

        Token joinToken = current;
        context.consume();

        Token arrow = context.getCurrentToken();
        if (arrow == null || arrow.getType() != TokenType.ARROW) {
            throw new Parser.ParseException("No hay una -> después del join");
        }
        context.consume();

        Token targetToken = context.getCurrentToken();
        if (targetToken == null || targetToken.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException("No hay un identificado valido");
        }

        String targetTask = targetToken.getValue();
        context.consume();

        return new JoinClauseNode(joinToken, targetTask);
    }
}