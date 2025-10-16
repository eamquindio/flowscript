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
        Token endToken = context.getCurrentToken();
        if(endToken == null || endToken.getType() != TokenType.END){
            throw new Parser.ParseException("Se espera la palabra _end_ al final del elemento. ");
        }
        context.consume(TokenType.END);
        Token endName = context.consume(TokenType.IDENTIFIER);
        return new EndElementNode(endToken, endName.getValue());
    }
}
