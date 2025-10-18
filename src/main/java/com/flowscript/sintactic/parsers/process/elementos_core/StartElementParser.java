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
        Token startToken = parseStartKeyword(context);
        parseArrow(context);
        String targetNode = parseTargetNode(context);

        return new StartElementNode(startToken, targetNode);
    }

    private Token parseStartKeyword(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (!context.check(TokenType.START)) {
            throw new Parser.ParseException("Se esperaba 'start' o 'inicio'");
        }
        context.advance();

        return token;
    }

    private void parseArrow(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.ARROW)) {
            throw new Parser.ParseException("Se esperaba '->' después de 'start'");
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
