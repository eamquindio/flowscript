package com.flowscript.sintactic.parsers.process.control_flujo;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ElseClauseNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ExclusiveGatewayNode;
import com.flowscript.sintactic.parsers.process.clausulas_control.ElseClauseParser;
import com.flowscript.sintactic.parsers.process.clausulas_control.WhenClauseParser;

/**
 * Parser para gateways exclusivos (XOR).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ExclusiveGateway ::= 'gateway' IDENTIFIER '{' WhenClause* ElseClause? '}'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 4: Elementos de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Gateway exclusivo básico
 * gateway DecisionMonto {
 *     when entrada.monto > 10000 -> RequiereAprobacionGerente
 *     when entrada.monto > 1000 -> RequiereAprobacionSupervisor
 *     else -> AprobacionAutomatica
 * }
 * </pre>
 *
 * @see ExclusiveGatewayNode
 */
public class ExclusiveGatewayParser implements IParser<ExclusiveGatewayNode> {

    private final WhenClauseParser whenParser;
    private final ElseClauseParser elseParser;

    public ExclusiveGatewayParser() {
        this.whenParser = new WhenClauseParser();
        this.elseParser = new ElseClauseParser();
    }

    @Override
    public ExclusiveGatewayNode parse(ParserContext context) throws Parser.ParseException {
        Token gatewayToken = context.consume(TokenType.GATEWAY);
        Token nameToken = context.consume(TokenType.IDENTIFIER);
        context.consume(TokenType.LEFT_BRACE);

        List<WhenClauseNode> whens = new ArrayList<>();
        ElseClauseNode elseClause = null;
        
        while (!context.check(TokenType.RIGHT_BRACE)) {
            if (context.check(TokenType.WHEN)) {
                whens.add(whenParser.parse(context));
                continue;
            }

            if (context.check(TokenType.ELSE)) {
                if (elseClause != null) {
                    throw new Parser.ParseException("Multiple else clauses in gateway '" + nameToken.getValue() + "'");
                }

                elseClause = elseParser.parse(context);
                continue;
            }

            Token curToken = context.getCurrentToken();
            throw new Parser.ParseException("Unexpected token '" + (curToken != null ? curToken.getValue() : "<eof>") + "' in gateway body");
        }
        
        context.consume(TokenType.RIGHT_BRACE);

        if (whens.isEmpty() && elseClause == null) {
            throw new Parser.ParseException("Gateway '" + nameToken.getValue() + "' must contain at least one when or else clause");
        }

        return new ExclusiveGatewayNode(gatewayToken, nameToken.getValue(), whens, elseClause);
    }
}
