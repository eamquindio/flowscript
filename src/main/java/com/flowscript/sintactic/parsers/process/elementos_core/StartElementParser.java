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
        Token current = context.getCurrentToken();
        TokenType type = current.getType();


        boolean isStart = type == TokenType.START;
        boolean isInicio = type == TokenType.IDENTIFIER && "inicio".equalsIgnoreCase(current.getValue());

        if (!isStart && !isInicio) {
            throw error("Se esperaba 'start' o 'inicio'", current);
        }


        context.consume();


        Token arrowToken = context.getCurrentToken();
        if (arrowToken == null || arrowToken.getType() != TokenType.ARROW) {
            throw error("Se esperaba '->' después de 'start'/'inicio'",
                    arrowToken != null ? arrowToken : current);
        }
        context.consume(); // Consumir el token '->'


        Token targetToken = context.consume(TokenType.IDENTIFIER);
        if (targetToken == null) {
            throw error("Se esperaba un identificador después de '->'", current);
        }

        return new StartElementNode(current, targetToken.getValue());
    }


    private Parser.ParseException error(String message, Token token) {
        return new Parser.ParseException(
                message + " en la línea " + token.getLine() + ", columna " + token.getColumn()
        );
    }
}
