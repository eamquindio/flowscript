package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para BlockParser.
 *
 * Gramática BNF:
 * Block ::= '{' StatementList '}'
 */
public class BlockParserTest {

    private BlockParser parser = new BlockParser();

    @Test
    public void testEmptyBlock() throws Exception {
        String input = "{}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        BlockNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getStatements().isEmpty());
    }

    @Test
    public void testBlockWithSingleStatement() throws Exception {
        String input = "{ x = 1 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        BlockNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.getStatements().size());
    }

    @Test
    public void testBlockWithMultipleStatements() throws Exception {
        String input = "{ x = 1\n y = 2\n z = 3 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        BlockNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.getStatements().size());
    }

    @Test
    public void testBlockWithIfStatement() throws Exception {
        String input = "{ if x > 0 { y = 1 } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        BlockNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testNestedBlocks() throws Exception {
        String input = "{ { x = 1 } { y = 2 } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        BlockNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getStatements().size());
    }

    @Test
    public void testInvalidBlock_MissingClosingBrace() throws Exception {
        String input = "{ x = 1";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
