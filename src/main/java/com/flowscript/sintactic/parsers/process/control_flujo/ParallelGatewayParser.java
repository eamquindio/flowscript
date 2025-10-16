package com.flowscript.sintactic.parsers.process.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.ParallelGatewayNode;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;
import com.flowscript.sintactic.ast.process.clausulas_control.JoinClauseNode;
import com.flowscript.sintactic.parsers.process.clausulas_control.ParallelBranchParser;
import com.flowscript.sintactic.parsers.process.clausulas_control.JoinClauseParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para gateways paralelos (AND).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ParallelGateway ::= 'gateway' IDENTIFIER 'parallel' '{' ParallelBranch* JoinClause '}'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 4: Elementos de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Gateway paralelo
 * gateway VerificacionAntecedentes parallel {
 *     branch -> VerificarCredito
 *     branch -> VerificarPenal
 *     join -> Consolidar
 * }
 * </pre>
 *
 * @see ParallelGatewayNode
 */
public class ParallelGatewayParser implements IParser<ParallelGatewayNode> {

    private final ParallelBranchParser branchParser;
    private final JoinClauseParser joinParser;

    public ParallelGatewayParser() {
        this.branchParser = new ParallelBranchParser();
        this.joinParser = new JoinClauseParser();
    }

    @Override
    public ParallelGatewayNode parse(ParserContext context) throws Parser.ParseException {
        Token gatewayToken = context.getCurrentToken();
        if (gatewayToken == null || gatewayToken.getType() != TokenType.GATEWAY) {
            throw new Parser.ParseException("Se esperaba _gateway_ al inicio del gateway paralelo.");
        }
        context.consume(TokenType.GATEWAY);
        Token gateName = context.consume(TokenType.IDENTIFIER);
        context.consume(TokenType.PARALLEL);
        context.consume(TokenType.LEFT_BRACE);
        List<ParallelBranchNode> branchesNodes = new ArrayList<>();
        JoinClauseNode joinClause = null;

        while (context.getCurrentToken() != null && context.getCurrentToken().getType() == TokenType.BRANCH) {
            ParallelBranchNode branchNode = branchParser.parse(context);
            branchesNodes.add(branchNode);
        }

        if (context.getCurrentToken() == null || context.getCurrentToken().getType() != TokenType.JOIN) {
            throw new Parser.ParseException("Se esperaba _join_ antes de cerrar el gateway paralelo.");
        }
        joinClause = joinParser.parse(context);
        context.consume(TokenType.RIGHT_BRACE);
        return new ParallelGatewayNode(gatewayToken, gateName.getValue(), branchesNodes, joinClause);
    }
}
