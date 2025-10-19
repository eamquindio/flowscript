package com.flowscript.sintactic.parsers.process.navegacion;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.navegacion.GotoStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para GotoStatementParser.
 *
 * Gramática BNF:
 * GotoStatement ::= 'go_to' IDENTIFIER
 */
public class GotoStatementParserTest {

    private GotoStatementParser parser = new GotoStatementParser();

    @Test
    public void testSimpleGoto() throws Exception {
        String input = "go_to NextTask";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        GotoStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("NextTask", result.getTargetLabel());
    }

    @Test
    public void testGotoWithUnderscoreInName() throws Exception {
        String input = "go_to Validate_Input";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        GotoStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("Validate_Input", result.getTargetLabel());
    }

    @Test
    public void testGotoToEnd() throws Exception {
        String input = "go_to FinOK";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        GotoStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("FinOK", result.getTargetLabel());
    }

    @Test
    public void testInvalidGoto_MissingTarget() throws Exception {
        String input = "go_to";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}