package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.BreakStatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BreakStatementParser.
 *
 * Grammar BNF:
 * BreakStatement ::= 'break'
 */
public class BreakStatementParserTest {

    private BreakStatementParser parser = new BreakStatementParser();

    @Test
    public void testSimpleBreak() throws Exception {
        String input = "break";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        BreakStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("BreakStatement", result.getNodeType());
    }

    @Test
    public void testBreakInWhileLoop() throws Exception {
        String input = "while true { if condition { break } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // Parse the whole while statement which contains break
        WhileStatementParser whileParser = new WhileStatementParser();
        var whileNode = whileParser.parse(context);

        assertNotNull(whileNode);
        assertNotNull(whileNode.getBody());
    }

    @Test
    public void testBreakInForLoop() throws Exception {
        String input = "for each item in items { if item == target { break } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // Parse the whole for statement which contains break
        ForStatementParser forParser = new ForStatementParser();
        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode forNode = forParser.parse(context);

        assertNotNull(forNode);
        assertTrue(forNode instanceof ForStatementNode);
        ForStatementNode forStmt = (ForStatementNode) forNode;
        assertNotNull(forStmt.getBody());
    }

    @Test
    public void testBreakPositionTracking() throws Exception {
        String input = "break";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        BreakStatementNode result = parser.parse(context);

        // Verify position information is captured
        assertTrue(result.getLine() >= 0);
        assertTrue(result.getColumn() >= 0);
    }
}
