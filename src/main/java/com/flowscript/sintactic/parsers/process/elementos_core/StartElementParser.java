package com.flowscript.sintactic.parsers.process.elementos_core;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.StartElementNode;

/**
 * Parser para el elemento de inicio de un proceso.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * StartElement ::= 'start' '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 2: Elementos Core BPMN
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Inicio básico
 * start -> ValidarEntrada
 *
 * // En contexto completo
 * process RegistroUsuario {
 *     start -> ValidarDatos
 *
 *     task ValidarDatos {
 *         action: validar(entrada)
 *         go_to GuardarDB
 *     }
 *
 *     end GuardarDB
 * }
 * </pre>
 *
 * @see StartElementNode
 */
public class StartElementParser implements IParser<StartElementNode> {

    @Override
    public StartElementNode parse(ParserContext context) throws Parser.ParseException {
        // 'start'
        Token startTok = consumeKeyword(context, TokenType.START, "start");

        // '->'
        consumeSymbol(context, TokenType.ARROW, "->");

        // ID de destino
        Token target = consumeIdentifier(context, "nombre del primer nodo (ID)");
        String targetName = target.getValue();

        // Construye el nodo AST (asumiendo ctor StartElementNode(Token, String))
        return new StartElementNode(startTok, targetName);
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

    private static void consumeSymbol(ParserContext ctx, TokenType type, String lexeme) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba '" + lexeme + "', pero no hay más tokens.");
        }
        if (t.getType() != type && !lexeme.equals(t.getValue())) {
            throw error(t, "Se esperaba '" + lexeme + "'");
        }
        ctx.advance();
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
