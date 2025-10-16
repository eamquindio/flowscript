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
 * 
 * <pre>
 * JoinClause ::= 'join' '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 5: Cláusulas de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * join -> Consolidar
 * join -> FinalizarProceso
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 * <li>Consumir 'join' o 'unir'</li>
 * <li>Consumir '->'</li>
 * <li>Consumir IDENTIFIER (nodo destino)</li>
 * <li>Crear y retornar JoinClauseNode</li>
 * </ol>
 *
 * @see JoinClauseNode
 */
public class JoinClauseParser implements IParser<JoinClauseNode> {

    @Override
    public JoinClauseNode parse(ParserContext context) throws Parser.ParseException {
        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba una cláusula 'join'");
        }


        Token joinToken = context.getCurrentToken();


        if (joinToken.getType() != TokenType.JOIN) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba 'join'",
                            joinToken.getValue(),
                            joinToken.getLine(),
                            joinToken.getColumn()));
        }


        context.consume(TokenType.JOIN);


        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba después de 'join' ",
                            joinToken.getLine(),
                            joinToken.getColumn()));
        }

        Token currentToken = context.getCurrentToken();
        if (currentToken.getType() != TokenType.ARROW) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba después de 'join'",
                            currentToken.getValue(),
                            currentToken.getLine(),
                            currentToken.getColumn()));
        }

        context.consume(TokenType.ARROW);


        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba un 'join'");
        }

        Token targetToken = context.getCurrentToken();

        if (targetToken.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un 'join'",
                            targetToken.getValue(),
                            targetToken.getLine(),
                            targetToken.getColumn()));
        }

        String targetTask = targetToken.getValue();

        if (targetTask == null || targetTask.trim().isEmpty()) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un 'join', no puede estar vacio",
                            targetToken.getLine(),
                            targetToken.getColumn()));
        }


        context.consume(TokenType.IDENTIFIER);


        JoinClauseNode joinClauseNode = new JoinClauseNode(joinToken, targetTask);

        return joinClauseNode;
    }
}