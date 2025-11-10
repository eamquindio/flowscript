package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ContinueStatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ContinueStatementParser.
 *
 * Grammar BNF:
 * ContinueStatement ::= 'continue'
 */
public class ContinueStatementParserTest {

    private ContinueStatementParser parser = new ContinueStatementParser();

    @Test
    public void testSimpleContinue() throws Exception {
        String input = "continue";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ContinueStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("ContinueStatement", result.getNodeType());
    }

    @Test
    public void testContinueInWhileLoop() throws Exception {
        String input = "while processing { if skip_this { continue } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // Parse the whole while statement which contains continue
        WhileStatementParser whileParser = new WhileStatementParser();
        var whileNode = whileParser.parse(context);

        assertNotNull(whileNode);
        assertNotNull(whileNode.getBody());
    }

    @Test
    public void testContinueInForLoop() throws Exception {
        String input = "for each item in items { if not valid(item) { continue } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // Parse the whole for statement which contains continue
        ForStatementParser forParser = new ForStatementParser();
        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode forNode = forParser.parse(context);

        assertNotNull(forNode);
        assertTrue(forNode instanceof ForStatementNode);
        ForStatementNode forStmt = (ForStatementNode) forNode;
        assertNotNull(forStmt.getBody());
    }

    @Test
    public void testContinuePositionTracking() throws Exception {
        String input = "continue";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ContinueStatementNode result = parser.parse(context);

        // Verify position information is captured
        assertTrue(result.getLine() >= 0);
        assertTrue(result.getColumn() >= 0);
    }

    @Test
    public void testMultipleContinuesInLoop() throws Exception {
        String input = "while true { if a { continue } if b { continue } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementParser whileParser = new WhileStatementParser();
        var whileNode = whileParser.parse(context);

        assertNotNull(whileNode);
    }
}
