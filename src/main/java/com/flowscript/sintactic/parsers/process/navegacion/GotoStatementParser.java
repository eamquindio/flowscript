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
        Token curToken = context.getCurrentToken();
        Token gotoToken;
        if (curToken != null && curToken.getType() == TokenType.GOTO) {
            gotoToken = context.consume();
        } else if (curToken != null && curToken.getType() == TokenType.IDENTIFIER && "go_to".equals(curToken.getValue())) {
            gotoToken = context.consume();
        } else {
            throw new Parser.ParseException("Expected 'go_to' at line " + (curToken != null ? curToken.getLine() : -1));
        }

        Token targetToken = context.consume(TokenType.IDENTIFIER);
        return new GotoStatementNode(gotoToken, targetToken.getValue());
    }
}
