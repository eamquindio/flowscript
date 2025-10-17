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

        // Consumir 'parallel' o 'paralelo'
        if (!context.check(TokenType.PARALLEL)) {
            throw new Parser.ParseException("Se esperaba 'parallel' o 'paralelo'");
        }
        context.advance();

        // Consumir '{'
        if (!context.check(TokenType.LEFT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '{' después de 'parallel'");
        }
        context.advance();

        // Parsear ramas paralelas (al menos una)
        List<ParallelBranchNode> branches = new ArrayList<>();
        if (!context.check(TokenType.BRANCH)) {
            throw new Parser.ParseException("Se esperaba al menos una cláusula 'branch' en el gateway paralelo");
        }

        while (context.check(TokenType.BRANCH)) {
            ParallelBranchNode branch = branchParser.parse(context);
            branches.add(branch);
        }

        // Parsear cláusula join (obligatoria)
        if (!context.check(TokenType.JOIN)) {
            throw new Parser.ParseException("Se esperaba una cláusula 'join' en el gateway paralelo");
        }
        JoinClauseNode joinClause = joinParser.parse(context);

        // Consumir '}'
        if (!context.check(TokenType.RIGHT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '}' al final del gateway");
        }
        context.advance();

        return new ParallelGatewayNode(gatewayToken, gatewayName, branches, joinClause);
    }
}
