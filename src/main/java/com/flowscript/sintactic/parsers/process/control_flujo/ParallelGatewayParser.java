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
        Token gatewayToken = parseGatewayKeyword(context);
        String gatewayName = parseGatewayName(context);
        parseParallelKeyword(context);
        parseOpenBrace(context);

        List<ParallelBranchNode> branches = parseBranches(context);
        JoinClauseNode joinClause = parseJoinClause(context);

        parseCloseBrace(context);

        return new ParallelGatewayNode(gatewayToken, gatewayName, branches, joinClause);
    }

    private Token parseGatewayKeyword(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (!context.check(TokenType.GATEWAY)) {
            throw new Parser.ParseException("Se esperaba 'gateway' o 'compuerta'");
        }
        context.advance();

        return token;
    }

    private String parseGatewayName(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.IDENTIFIER)) {
            throw new Parser.ParseException("Se esperaba un identificador después de 'gateway'");
        }

        String name = context.getCurrentToken().getValue();
        context.advance();

        return name;
    }

    private void parseParallelKeyword(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.PARALLEL)) {
            throw new Parser.ParseException("Se esperaba 'parallel' o 'paralelo'");
        }
        context.advance();
    }

    private void parseOpenBrace(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.LEFT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '{' después de 'parallel'");
        }
        context.advance();
    }

    private List<ParallelBranchNode> parseBranches(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.BRANCH)) {
            throw new Parser.ParseException("Se esperaba al menos una cláusula 'branch' en el gateway paralelo");
        }

        List<ParallelBranchNode> branches = new ArrayList<>();
        while (context.check(TokenType.BRANCH)) {
            ParallelBranchNode branch = branchParser.parse(context);
            branches.add(branch);
        }

        return branches;
    }

    private JoinClauseNode parseJoinClause(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.JOIN)) {
            throw new Parser.ParseException("Se esperaba una cláusula 'join' en el gateway paralelo");
        }

        return joinParser.parse(context);
    }

    private void parseCloseBrace(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.RIGHT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '}' al final del gateway");
        }
        context.advance();
    }
}
