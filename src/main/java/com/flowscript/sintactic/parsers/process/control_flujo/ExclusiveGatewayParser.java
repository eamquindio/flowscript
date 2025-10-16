package com.flowscript.sintactic.parsers.process.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.ExclusiveGatewayNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.ast.process.clausulas_control.ElseClauseNode;
import com.flowscript.sintactic.parsers.process.clausulas_control.WhenClauseParser;
import com.flowscript.sintactic.parsers.process.clausulas_control.ElseClauseParser;

import java.util.ArrayList;
import java.util.List;

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
        Token gatewayToken = context.getCurrentToken();
        if (gatewayToken == null || gatewayToken.getType() != TokenType.GATEWAY) {
         throw new Parser.ParseException("Se esperaba _gateway_ al inicio del elemento.");
        }
        context.consume(TokenType.GATEWAY);
        Token nameGate = context.consume(TokenType.IDENTIFIER);
        context.consume(TokenType.LEFT_BRACE);
        List<WhenClauseNode> whenNodes = new ArrayList<>();
        ElseClauseNode elseClause = null;

        while (context.getCurrentToken() != null && context.getCurrentToken().getType() == TokenType.WHEN) {
            WhenClauseNode whenNode = whenParser.parse(context);
            whenNodes.add(whenNode);
        }

        if (context.getCurrentToken() != null && context.getCurrentToken().getType() == TokenType.ELSE) {
            elseClause = elseParser.parse(context);
        }

        if (context.getCurrentToken() != null && context.getCurrentToken().getType() == TokenType.WHEN) {
            throw new Parser.ParseException("No se permiten elementos _when_ después de _else_");
        }
        context.consume(TokenType.RIGHT_BRACE);
        return new ExclusiveGatewayNode(gatewayToken, nameGate.getValue(), whenNodes, elseClause);
    }
}
