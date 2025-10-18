package com.flowscript.sintactic.parsers.process.elementos_core;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.EndElementNode;

/**
 * Parser para el elemento de fin de un proceso.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * EndElement ::= 'end' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 2: Elementos Core BPMN
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Fin simple
 * end FinExitoso
 *
 * // Múltiples fines
 * process AprobacionCredito {
 *     start -> VerificarMonto
 *
 *     task VerificarMonto {
 *         action:
 *             if entrada.monto > 10000 {
 *                 go_to FinAprobado
 *             } else {
 *                 go_to FinRechazado
 *             }
 *     }
 *
 *     end FinAprobado
 *     end FinRechazado
 * }
 * </pre>
 *
 * @see EndElementNode
 */
public class EndElementParser implements IParser<EndElementNode> {

    @Override
    public EndElementNode parse(ParserContext context) throws Parser.ParseException {
        // 'end'
        Token endTok = consumeKeyword(context, TokenType.END, "end");

        // ID del fin
        Token name = consumeIdentifier(context, "nombre del nodo end (ID)");
        String endName = name.getValue();

        // Construye el nodo AST (asumiendo ctor EndElementNode(Token, String))
        return new EndElementNode(endTok, endName);
    }

    // ----------------------
    // Utilidades locales
    // ----------------------

    private static Token consumeKeyword(ParserContext ctx, TokenType type, String lexeme) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba '" + lexeme + "', pero no hay más tokens.");
        }
        if (t.getType() != type && !lexeme.equals(t.getValue())) {
            throw error(t, "Se esperaba '" + lexeme + "'");
        }
        ctx.advance();
        return t;
    }

    private static Token consumeIdentifier(ParserContext ctx, String what) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba " + what + ", pero no hay más tokens.");
        }
        if (t.getType() != TokenType.IDENTIFIER) {
            throw error(t, "Se esperaba un identificador para " + what);
        }
        ctx.advance();
        return t;
    }

    private static Parser.ParseException error(Token t, String msg) {
        return new Parser.ParseException(msg + " pero se encontró '" + t.getValue()
                + "' en línea " + t.getLine() + ", columna " + t.getColumn());
    }
}
