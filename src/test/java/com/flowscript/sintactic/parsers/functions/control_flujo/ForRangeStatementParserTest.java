package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ForRangeStatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ForRangeStatementParser (via ForStatementParser).
 *
 * Grammar BNF:
 * ForRangeStatement ::= 'for' IDENTIFIER 'from' Expression 'to' Expression ('step' Expression)? Statement
 */
public class ForRangeStatementParserTest {

    private ForStatementParser parser = new ForStatementParser();

    @Test
    public void testBasicForRange() throws Exception {
        String input = "for i from 1 to 10 { imprimir(i) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode stmt = parser.parse(context);

        assertNotNull(stmt);
        assertTrue(stmt instanceof ForRangeStatementNode);

        ForRangeStatementNode rangeNode = (ForRangeStatementNode) stmt;
        assertEquals("i", rangeNode.getLoopVariable());
        assertNotNull(rangeNode.getFromExpression());
        assertNotNull(rangeNode.getToExpression());
        assertFalse(rangeNode.hasStepExpression());
        assertNotNull(rangeNode.getBody());
    }

    @Test
    public void testForRangeWithStep() throws Exception {
        String input = "for x from 0 to 100 step 5 { procesar(x) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode stmt = parser.parse(context);

        assertNotNull(stmt);
        assertTrue(stmt instanceof ForRangeStatementNode);

        ForRangeStatementNode rangeNode = (ForRangeStatementNode) stmt;
        assertEquals("x", rangeNode.getLoopVariable());
        assertTrue(rangeNode.hasStepExpression());
        assertNotNull(rangeNode.getStepExpression());
    }

    @Test
    public void testForRangeDescending() throws Exception {
        String input = "for i from 10 to 1 step -1 { imprimir(i) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode stmt = parser.parse(context);

        assertNotNull(stmt);
        assertTrue(stmt instanceof ForRangeStatementNode);

        ForRangeStatementNode rangeNode = (ForRangeStatementNode) stmt;
        assertEquals("i", rangeNode.getLoopVariable());
        assertTrue(rangeNode.hasStepExpression());
    }

    @Test
    public void testForRangeWithExpressions() throws Exception {
        String input = "for n from start_value to end_value step increment { procesar(n) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode stmt = parser.parse(context);

        assertNotNull(stmt);
        assertTrue(stmt instanceof ForRangeStatementNode);

        ForRangeStatementNode rangeNode = (ForRangeStatementNode) stmt;
        assertEquals("n", rangeNode.getLoopVariable());
        assertNotNull(rangeNode.getFromExpression());
        assertNotNull(rangeNode.getToExpression());
        assertNotNull(rangeNode.getStepExpression());
    }

    @Test
    public void testForRangeWithComplexExpressions() throws Exception {
        String input = "for i from a + b to x * y step c / 2 { work(i) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode stmt = parser.parse(context);

        assertNotNull(stmt);
        assertTrue(stmt instanceof ForRangeStatementNode);
    }

    @Test
    public void testForRangeNodeType() throws Exception {
        String input = "for i from 1 to 5 { imprimir(i) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode stmt = parser.parse(context);

        assertEquals("ForRangeStatement", stmt.getNodeType());
    }

    @Test
    public void testForRangeVsForEach() throws Exception {
        // Test that for-each still works
        String inputEach = "for each item in items { procesar(item) }";
        Lexer lexerEach = new Lexer(inputEach);
        List<Token> tokensEach = lexerEach.tokenize();
        ParserContext contextEach = new ParserContext(tokensEach);

        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode stmtEach = parser.parse(contextEach);
        assertNotNull(stmtEach);
        assertFalse(stmtEach instanceof ForRangeStatementNode);
        assertEquals("ForStatement", stmtEach.getNodeType());

        // Test that for-range works
        String inputRange = "for i from 1 to 10 { procesar(i) }";
        Lexer lexerRange = new Lexer(inputRange);
        List<Token> tokensRange = lexerRange.tokenize();
        ParserContext contextRange = new ParserContext(tokensRange);

        com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode stmtRange = parser.parse(contextRange);
        assertNotNull(stmtRange);
        assertTrue(stmtRange instanceof ForRangeStatementNode);
        assertEquals("ForRangeStatement", stmtRange.getNodeType());
    }
}
