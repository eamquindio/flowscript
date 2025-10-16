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
        // TODO: Implementar este método
        Token joinToken = context.getCurrentToken();
        if (joinToken == null || joinToken.getType() != TokenType.JOIN) {
            throw new Parser.ParseException("Se esperaba _branch_ al inicio del elemento.");
        }
        if(joinToken.getType() == TokenType.JOIN){
            context.consume(TokenType.JOIN);
        }else if(joinToken.getType() == TokenType.IDENTIFIER && "unir".equals(joinToken.getValue())){
            context.consume();
        }else {
            throw new Parser.ParseException("Se espera la palabra _join_ o _unir_ al inicio del elemento. ");
        }
        context.consume(TokenType.ARROW);
        Token tarName = context.consume(TokenType.IDENTIFIER);
        return new JoinClauseNode(joinToken, tarName.getValue());
    }
}
