package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para WhenClauseParser.
 *
 * Gramática BNF:
 * WhenClause ::= 'when' Expression '->' IDENTIFIER
 */
public class WhenClauseParserTest {

    private WhenClauseParser parser = new WhenClauseParser();

    @Test
    public void testSimpleWhenClause() throws Exception {
        String input = "when x > 0 -> PositiveCase";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhenClauseNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getCondition());
        assertEquals("PositiveCase", result.getTargetTask());
    }

    @Test
    public void testWhenClauseWithComplexCondition() throws Exception {
        String input = "when amount > 1000 and status == \"active\" -> ApprovalRequired";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhenClauseNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getCondition());
        assertEquals("ApprovalRequired", result.getTargetTask());
    }

    @Test
    public void testWhenClauseWithFunctionCall() throws Exception {
        String input = "when validate(order) -> ProcessOrder";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhenClauseNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("ProcessOrder", result.getTargetTask());
    }

    @Test
    public void testWhenClauseWithBoolean() throws Exception {
        String input = "when isValid -> NextStep";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhenClauseNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("NextStep", result.getTargetTask());
    }

    @Test
    public void testInvalidWhenClause_MissingArrow() throws Exception {
        String input = "when x > 0 NextTask";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidWhenClause_MissingCondition() throws Exception {
        String input = "when -> NextTask";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
