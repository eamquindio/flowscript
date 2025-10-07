package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.IfStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para IfStatementParser.
 *
 * Gramática BNF:
 * IfStatement ::= 'if' Expression Statement ( 'else_if' Expression Statement )* ( 'else' Statement )?
 */
public class IfStatementParserTest {

    private IfStatementParser parser = new IfStatementParser();

    @Test
    public void testSimpleIf() throws Exception {
        String input = "if x > 0 { y = 1 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IfStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getCondition());
        assertNotNull(result.getThenStatement());
        assertNull(result.getElseStatement());
    }

    @Test
    public void testIfElse() throws Exception {
        String input = "if x > 0 { y = 1 } else { y = -1 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IfStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getElseStatement());
    }

    @Test
    public void testIfElseIf() throws Exception {
        String input = "if x > 0 { y = 1 } else_if x < 0 { y = -1 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IfStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getElseIfClauses());
        assertFalse(result.getElseIfClauses().isEmpty());
    }

    @Test
    public void testIfElseIfElse() throws Exception {
        String input = "if x > 0 { y = 1 } else_if x < 0 { y = -1 } else { y = 0 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IfStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getElseIfClauses());
        assertFalse(result.getElseIfClauses().isEmpty());
        assertNotNull(result.getElseStatement());
    }

    @Test
    public void testMultipleElseIf() throws Exception {
        String input = "if x == 1 { y = \"one\" } else_if x == 2 { y = \"two\" } else_if x == 3 { y = \"three\" } else { y = \"other\" }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IfStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getElseIfClauses().size());
        assertNotNull(result.getElseStatement());
    }

    @Test
    public void testNestedIf() throws Exception {
        String input = "if x > 0 { if y > 0 { z = 1 } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IfStatementNode result = parser.parse(context);

        assertNotNull(result);
    }
}
