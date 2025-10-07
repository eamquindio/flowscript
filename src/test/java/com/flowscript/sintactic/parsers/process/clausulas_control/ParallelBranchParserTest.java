package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ParallelBranchParser.
 *
 * Gramática BNF:
 * ParallelBranch ::= 'branch' '->' IDENTIFIER
 */
public class ParallelBranchParserTest {

    private ParallelBranchParser parser = new ParallelBranchParser();

    @Test
    public void testSimpleParallelBranch() throws Exception {
        String input = "branch -> Task1";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ParallelBranchNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("Task1", result.getTargetTask());
    }

    @Test
    public void testParallelBranchWithComplexName() throws Exception {
        String input = "branch -> VerifyCustomerCredentials";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ParallelBranchNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("VerifyCustomerCredentials", result.getTargetTask());
    }

    @Test
    public void testInvalidParallelBranch_MissingArrow() throws Exception {
        String input = "branch Task1";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidParallelBranch_MissingTarget() throws Exception {
        String input = "branch ->";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
