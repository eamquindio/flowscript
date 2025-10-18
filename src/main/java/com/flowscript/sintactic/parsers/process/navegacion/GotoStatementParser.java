package com.flowscript.sintactic.parsers.process.navegacion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.navegacion.GotoStatementNode;

/**
 * Parser para statements goto (navegación entre nodos de proceso).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * GotoStatement ::= 'go_to' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 6: Navegación entre Elementos
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Goto simple
 * go_to SiguienteTarea
 *
 * // En contexto
 * task ProcesarDatos {
 *     action:
 *         datos = procesar(entrada)
 *         go_to FinExitoso
 * }
 * </pre>
 *
 * @see GotoStatementNode
 */
public class GotoStatementParser implements IParser<GotoStatementNode> {

    @Override
    public GotoStatementNode parse(ParserContext context) throws Parser.ParseException {
        Token gotoToken = parseGotoKeyword(context);
        String targetNode = parseTargetNode(context);

        return new GotoStatementNode(gotoToken, targetNode);
    }

    private Token parseGotoKeyword(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (!context.check(TokenType.GOTO)) {
            throw new Parser.ParseException("Se esperaba 'goto' o ''go_to");
        }
        context.advance();

        return token;
    }

    private String parseTargetNode(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.IDENTIFIER)) {
            throw new Parser.ParseException("Se esperaba un identificador después de 'go_to'");
        }

        String nodeName = context.getCurrentToken().getValue();
        context.advance();

        return nodeName;
    }
}
