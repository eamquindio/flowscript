package com.flowscript.sintactic.parsers.process.control_flujo;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.JoinClauseNode;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ParallelGatewayNode;
import com.flowscript.sintactic.parsers.process.clausulas_control.JoinClauseParser;
import com.flowscript.sintactic.parsers.process.clausulas_control.ParallelBranchParser;

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
        Token gatewayToken = context.consume(TokenType.GATEWAY);
        Token nameToken = context.consume(TokenType.IDENTIFIER);
        context.consume(TokenType.PARALLEL);
        context.consume(TokenType.LEFT_BRACE);

        List<ParallelBranchNode> branches = new ArrayList<>();
        JoinClauseNode join = null;
        
        while (!context.check(TokenType.RIGHT_BRACE)) {
            if (context.check(TokenType.BRANCH)) {
                branches.add(branchParser.parse(context));
                continue;
            }

            if (context.check(TokenType.JOIN)) {
                if (join != null) {
                    throw new Parser.ParseException("Multiple join clauses in parallel gateway '" + nameToken.getValue() + "'");
                }

                join = joinParser.parse(context);
                continue;
            }
            
            Token curToken = context.getCurrentToken();
            throw new Parser.ParseException("Unexpected token '" + (curToken != null ? curToken.getValue() : "<eof>") + "' in parallel gateway body");
        }

        context.consume(TokenType.RIGHT_BRACE);
        if (branches.isEmpty()) {
            throw new Parser.ParseException("Parallel gateway '" + nameToken.getValue() + "' must contain at least one branch");
        }

        if (join == null) {
            throw new Parser.ParseException("Parallel gateway '" + nameToken.getValue() + "' must contain a join clause");
        }

        return new ParallelGatewayNode(gatewayToken, nameToken.getValue(), branches, join);
    }
}
