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
        Token gotoToken = context.getCurrentToken();

        // Verificar y consumir 'go_to', 'goto' o 'ir_a'
        if (!context.check(TokenType.GOTO)) {
            throw new Parser.ParseException("Se esperaba 'go_to', 'goto' o 'ir_a'");
        }
        context.advance();

        // Obtener el nombre del nodo destino
        if (!context.check(TokenType.IDENTIFIER)) {
            throw new Parser.ParseException("Se esperaba un identificador después de 'go_to'");
        }

        String targetNodeName = context.getCurrentToken().getValue();
        context.advance();

        return new GotoStatementNode(gotoToken, targetNodeName);
    }
}
