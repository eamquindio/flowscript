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
        // Consume 'gateway'
        Token gatewayToken = context.consume(TokenType.GATEWAY);

        // Consume gateway name (IDENTIFIER)
        Token nameToken = context.consume(TokenType.IDENTIFIER);
        String gatewayName = nameToken.getValue();

        // Consume 'parallel'
        context.consume(TokenType.PARALLEL);

        // Consume '{'
        context.consume(TokenType.LEFT_BRACE);

        // Parse branch clauses
        List<ParallelBranchNode> branches = new ArrayList<>();
        while (context.getCurrentToken() != null &&
               context.getCurrentToken().getType() == TokenType.BRANCH) {
            branches.add(branchParser.parse(context));
        }

        // Parse join clause (required)
        JoinClauseNode joinClause = joinParser.parse(context);

        // Consume '}'
        context.consume(TokenType.RIGHT_BRACE);

        return new ParallelGatewayNode(gatewayToken, gatewayName, branches, joinClause);
    }
}
