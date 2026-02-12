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
        // palabra clave: go_to (o goto)
        Token kw = context.getCurrentToken();
        if (kw == null) {
            throw new Parser.ParseException("Se esperaba 'go_to' pero no hay más tokens.");
        }
        String v = kw.getValue();
        boolean ok =
                kw.getType() == TokenType.GOTO ||
                        "go_to".equalsIgnoreCase(v) ||
                        "goto".equalsIgnoreCase(v);
        if (!ok) {
            throw error(kw, "Se esperaba 'go_to'");
        }
        context.advance();

        // identificador destino
        Token id = context.getCurrentToken();
        if (id == null || id.getType() != TokenType.IDENTIFIER) {
            throw error(id, "Se esperaba un identificador después de 'go_to'");
        }
        String target = id.getValue();
        context.advance();

        return new GotoStatementNode(kw, target);
    }

    private static Parser.ParseException error(Token t, String msg) {
        if (t == null) return new Parser.ParseException(msg + " y se encontró <fin de archivo>");
        return new Parser.ParseException(
                msg + " pero se encontró '" + t.getValue() + "' en línea " + t.getLine() + ", columna " + t.getColumn()
        );
    }
}
