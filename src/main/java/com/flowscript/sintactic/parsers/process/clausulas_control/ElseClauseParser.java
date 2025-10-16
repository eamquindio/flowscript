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
 * 
 * <pre>
 * ElseClause ::= 'else' '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 5: Cláusulas de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * else -> AprobacionAutomatica
 * else -> ProcesoDefault
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 * <li>Consumir 'else' o 'sino'</li>
 * <li>Consumir '->'</li>
 * <li>Consumir IDENTIFIER (nodo destino)</li>
 * <li>Crear y retornar ElseClauseNode</li>
 * </ol>
 *
 * @see ElseClauseNode
 */
public class ElseClauseParser implements IParser<ElseClauseNode> {

    @Override
    public ElseClauseNode parse(ParserContext context) throws Parser.ParseException {
        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba una cláusula 'else', pero se encontró el final del archivo");
        }

        Token elseToken = context.getCurrentToken();

        if (elseToken.getType() != TokenType.ELSE) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba 'else' o 'sino', pero se encontró '%s' en la línea %d, columna %d",
                            elseToken.getValue(),
                            elseToken.getLine(),
                            elseToken.getColumn()));
        }

        context.consume(TokenType.ELSE);

        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba '->' después de 'else' en la línea %d, columna %d, pero se encontró el final del archivo",
                            elseToken.getLine(),
                            elseToken.getColumn()));
        }

        Token currentToken = context.getCurrentToken();
        if (currentToken.getType() != TokenType.ARROW) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba '->' después de 'else', pero se encontró '%s' en la línea %d, columna %d",
                            currentToken.getValue(),
                            currentToken.getLine(),
                            currentToken.getColumn()));
        }


        context.consume(TokenType.ARROW);

 
        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba un identificador de tarea destino después de '->', pero se encontró el final del archivo");
        }

        Token targetToken = context.getCurrentToken();

        if (targetToken.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un identificador de tarea destino después de '->', pero se encontró '%s' en la línea %d, columna %d",
                            targetToken.getValue(),
                            targetToken.getLine(),
                            targetToken.getColumn()));
        }

        String targetTask = targetToken.getValue();

        if (targetTask == null || targetTask.trim().isEmpty()) {
            throw new Parser.ParseException(
                    String.format(
                            "El identificador de tarea destino no puede estar vacío en la línea %d, columna %d",
                            targetToken.getLine(),
                            targetToken.getColumn()));
        }


        context.consume(TokenType.IDENTIFIER);

    
        ElseClauseNode elseClauseNode = new ElseClauseNode(elseToken, targetTask);

 
        return elseClauseNode;
    }
}