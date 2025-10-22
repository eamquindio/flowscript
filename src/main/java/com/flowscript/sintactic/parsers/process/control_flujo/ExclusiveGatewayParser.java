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

        // Consumir 'gateway' o 'compuerta'
        if (!context.check(TokenType.GATEWAY)) {
            throw new Parser.ParseException("Se esperaba 'gateway' o 'compuerta'");
        }
        context.advance();

        // Consumir IDENTIFIER (nombre del gateway)
        if (!context.check(TokenType.IDENTIFIER)) {
            throw new Parser.ParseException("Se esperaba un identificador después de 'gateway'");
        }
        String gatewayName = context.getCurrentToken().getValue();
        context.advance();

        // Consumir '{'
        if (!context.check(TokenType.LEFT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '{' después del nombre del gateway");
        }
        context.advance();

        // Parsear cláusulas when (al menos una)
        List<WhenClauseNode> whenClauses = new ArrayList<>();
        if (!context.check(TokenType.WHEN)) {
            throw new Parser.ParseException("Se esperaba al menos una cláusula 'when' en el gateway exclusivo");
        }

        while (context.check(TokenType.WHEN)) {
            WhenClauseNode whenClause = whenParser.parse(context);
            whenClauses.add(whenClause);
        }

        // Parsear cláusula else (opcional)
        ElseClauseNode elseClause = null;
        if (context.check(TokenType.ELSE)) {
            elseClause = elseParser.parse(context);
        }

        // Consumir '}'
        if (!context.check(TokenType.RIGHT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '}' al final del gateway");
        }
        context.advance();

        return new ExclusiveGatewayNode(gatewayToken, gatewayName, whenClauses, elseClause);
    }
}
