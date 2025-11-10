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
        // Consume 'start'
        Token startToken = context.consume(TokenType.START);

        // Consume '->'
        context.consume(TokenType.ARROW);

        // Consume target node name (IDENTIFIER)
        Token targetToken = context.consume(TokenType.IDENTIFIER);
        String targetNodeName = targetToken.getValue();

        return new StartElementNode(startToken, targetNodeName);
    }
}
