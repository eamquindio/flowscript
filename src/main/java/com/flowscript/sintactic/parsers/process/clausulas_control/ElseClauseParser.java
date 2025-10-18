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
        Token elseKeyword = parseElseKeyword(context);
        parseArrow(context);
        String targetNode = parseTargetNode(context);

        return new ElseClauseNode(elseKeyword, targetNode);
    }

    private Token parseElseKeyword(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (!context.check(TokenType.ELSE)) {
            throw new Parser.ParseException("Se esperaba 'else' o 'sino'");
        }

        context.advance();
        return token;
    }

    private void parseArrow(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.ARROW)) {
            throw new Parser.ParseException("Se esperaba '->' después de 'else'");
        }

        context.advance();
    }

    private String parseTargetNode(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.IDENTIFIER)) {
            throw new Parser.ParseException("Se esperaba un identificador después de '->'");
        }

        String nodeName = context.getCurrentToken().getValue();
        context.advance();

        return nodeName;
    }
}
