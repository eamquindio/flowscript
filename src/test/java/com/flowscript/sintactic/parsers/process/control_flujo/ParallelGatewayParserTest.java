package com.flowscript.sintactic.parsers.process.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.ParallelGatewayNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ParallelGatewayParser.
 *
 * Gramática BNF:
 * ParallelGateway ::= 'gateway' IDENTIFIER 'parallel' '{' ParallelBranch* JoinClause '}'
 * ParallelBranch ::= 'branch' '->' IDENTIFIER
 * JoinClause ::= 'join' '->' IDENTIFIER
 */
public class ParallelGatewayParserTest {

    @Test
    public void testSimpleParallelGateway() throws Exception {
        String input = "gateway VerifyBackground parallel {\n" +
                      "    branch -> VerifyCredit\n" +
                      "    branch -> VerifyPenal\n" +
                      "    join -> Consolidate\n" +
                      "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // Note: You'll need to create the actual parser
        // ParallelGatewayParser parser = new ParallelGatewayParser();
        // ParallelGatewayNode result = parser.parse(context);

        // assertNotNull(result);
        // assertEquals("VerifyBackground", result.getName());
        // assertEquals(2, result.getBranches().size());
    }

    @Test
    public void testParallelGatewayWithMultipleBranches() throws Exception {
        String input = "gateway ProcessMultiple parallel {\n" +
                      "    branch -> Task1\n" +
                      "    branch -> Task2\n" +
                      "    branch -> Task3\n" +
                      "    branch -> Task4\n" +
                      "    join -> Merge\n" +
                      "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // ParallelGatewayParser parser = new ParallelGatewayParser();
        // ParallelGatewayNode result = parser.parse(context);

        // assertNotNull(result);
        // assertEquals(4, result.getBranches().size());
    }
}
