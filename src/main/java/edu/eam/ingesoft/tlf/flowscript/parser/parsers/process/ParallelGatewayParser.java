package edu.eam.ingesoft.tlf.flowscript.parser.parsers.process;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.IParser;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserContext;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserException;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ParallelGatewayNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para gateway paralelo (AND).
 *
 * Gramática:
 * ParallelGateway ::= 'gateway' IDENTIFIER 'paralelo' '{' ParallelBranch+ ParallelMerge '}'
 * ParallelBranch ::= 'rama' '->' IDENTIFIER
 * ParallelMerge ::= 'unir' '->' IDENTIFIER
 *
 * Ejemplo:
 * gateway ValidacionParalela paralelo {
 *     rama -> ValidarStock
 *     rama -> ValidarPago
 *     unir -> ContinuarProceso
 * }
 */
public class ParallelGatewayParser implements IParser<ParallelGatewayNode> {

    @Override
    public ParallelGatewayNode parse(ParserContext context) throws ParserException {
        int startLine = context.current().getLine();
        int startColumn = context.current().getColumn();

        // Parse 'gateway'
        context.expect(TokenType.GATEWAY);

        // Parse gateway name
        Token nameToken = context.expect(TokenType.IDENTIFIER);
        String name = nameToken.getLexeme();

        // Parse 'paralelo'
        context.expect(TokenType.PARALELO);

        // Parse '{'
        context.expect(TokenType.LEFT_BRACE);

        // Parse parallel branches (at least one required)
        List<ParallelGatewayNode.ParallelBranch> branches = new ArrayList<>();

        while (context.check(TokenType.RAMA)) {
            ParallelGatewayNode.ParallelBranch branch = parseParallelBranch(context);
            branches.add(branch);
        }

        // Validate at least one branch
        if (branches.isEmpty()) {
            throw new ParserException(
                "Gateway paralelo '" + name + "' debe contener al menos una rama",
                context.current()
            );
        }

        // Parse merge point (required)
        if (!context.check(TokenType.UNIR)) {
            throw new ParserException(
                "Gateway paralelo '" + name + "' debe tener un punto de unión (unir)",
                context.current()
            );
        }

        String mergeTargetName = parseParallelMerge(context);

        // Parse '}'
        context.expect(TokenType.RIGHT_BRACE);

        ParallelGatewayNode node = new ParallelGatewayNode(name, branches, mergeTargetName);
        return node;
    }

    /**
     * Parsea una rama paralela.
     * ParallelBranch ::= 'rama' '->' IDENTIFIER
     */
    private ParallelGatewayNode.ParallelBranch parseParallelBranch(ParserContext context)
            throws ParserException {
        // Parse 'rama'
        context.expect(TokenType.RAMA);

        // Parse '->'
        context.expect(TokenType.ARROW);

        // Parse target task name
        Token targetToken = context.expect(TokenType.IDENTIFIER);
        String targetTaskName = targetToken.getLexeme();

        return new ParallelGatewayNode.ParallelBranch(targetTaskName);
    }

    /**
     * Parsea el punto de unión de las ramas paralelas.
     * ParallelMerge ::= 'unir' '->' IDENTIFIER
     */
    private String parseParallelMerge(ParserContext context) throws ParserException {
        // Parse 'unir'
        context.expect(TokenType.UNIR);

        // Parse '->'
        context.expect(TokenType.ARROW);

        // Parse merge target name
        Token targetToken = context.expect(TokenType.IDENTIFIER);
        return targetToken.getLexeme();
    }
}
