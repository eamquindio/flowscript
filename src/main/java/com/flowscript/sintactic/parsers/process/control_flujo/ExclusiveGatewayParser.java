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
        if (!gatewayToken.getValue().equals("gateway")) {
            throw new Parser.ParseException(
                    "Se esperaba 'gateway' pero se encontró '" + gatewayToken.getValue() +
                            "' en la línea " + gatewayToken.getLine()
            );
        }
        context.consume();


        Token nameToken = context.consume(TokenType.IDENTIFIER);
        String gatewayName = nameToken.getValue();


        context.consume(TokenType.LEFT_BRACE);


        List<WhenClauseNode> whenClauses = new ArrayList<>();
        while (context.checkValue("when") || context.checkValue("cuando")) {
            WhenClauseNode whenClause = whenParser.parse(context);
            whenClauses.add(whenClause);
        }


        ElseClauseNode elseClause = null;
        if (context.checkValue("else") || context.checkValue("sino")) {
            elseClause = elseParser.parse(context);
        }


        context.consume(TokenType.RIGHT_BRACE);

        return new ExclusiveGatewayNode(gatewayToken, gatewayName, whenClauses, elseClause);
    }
}