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
        if (!gatewayToken.getValue().equals("gateway")) {
            throw new Parser.ParseException(
                "Expected 'gateway' but found '" + gatewayToken.getValue() +
                "' at line " + gatewayToken.getLine()
            );
        }
        context.consume();

        Token nameToken = context.consume(TokenType.IDENTIFIER);
        String gatewayName = nameToken.getValue();

        Token parallelToken = context.getCurrentToken();
        if (!parallelToken.getValue().equals("parallel") && !parallelToken.getValue().equals("paralelo")) {
            throw new Parser.ParseException(
                "Expected 'parallel' after gateway name but found '" + parallelToken.getValue() +
                "' at line " + parallelToken.getLine()
            );
        }
        context.consume();

        context.consume(TokenType.LEFT_BRACE);

        List<ParallelBranchNode> branches = new ArrayList<>();
        while (context.checkValue("rama") || context.checkValue("branch")) {
            ParallelBranchNode branch = branchParser.parse(context);
            branches.add(branch);
        }

        if (branches.isEmpty()) {
            throw new Parser.ParseException(
                "Parallel gateway must have at least one branch at line " + gatewayToken.getLine()
            );
        }

        if (!context.checkValue("unir") && !context.checkValue("join")) {
            throw new Parser.ParseException(
                "Parallel gateway must have a join clause at line " + context.getCurrentToken().getLine()
            );
        }
        JoinClauseNode joinClause = joinParser.parse(context);

        context.consume(TokenType.RIGHT_BRACE);

        return new ParallelGatewayNode(gatewayToken, gatewayName, branches, joinClause);
    }
}
