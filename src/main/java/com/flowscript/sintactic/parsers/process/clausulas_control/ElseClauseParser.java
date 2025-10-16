package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ElseClauseNode;

/**
 * Parser para cláusulas else en gateways exclusivos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ElseClause ::= 'else' '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 5: Cláusulas de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * else -> AprobacionAutomatica
 * else -> ProcesoDefault
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 *   <li>Consumir 'else' o 'sino'</li>
 *   <li>Consumir '->'</li>
 *   <li>Consumir IDENTIFIER (nodo destino)</li>
 *   <li>Crear y retornar ElseClauseNode</li>
 * </ol>
 *
 * @see ElseClauseNode
 */
public class ElseClauseParser implements IParser<ElseClauseNode> {

    @Override
    public ElseClauseNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token elseToken = context.getCurrentToken();
        if  (elseToken == null || elseToken.getType() != TokenType.ELSE) {
            throw new Parser.ParseException("Se espera la palabra _else_ al final del elemento. ");
        }
        if (elseToken.getType() == TokenType.ELSE) {
            context.consume(TokenType.ELSE);
        } else if (elseToken.getType() == TokenType.IDENTIFIER && "sino".equals(elseToken.getValue())){
            context.consume();
        } else {
            throw new Parser.ParseException("Se espera la palabra _else_ o _sino_ al final del elemento. ");
        }
        context.consume(TokenType.ARROW);
        Token tarName = context.consume(TokenType.IDENTIFIER); 
        return new ElseClauseNode(elseToken, tarName.getValue());
    }
}
