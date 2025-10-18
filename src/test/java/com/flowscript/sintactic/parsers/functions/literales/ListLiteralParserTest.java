package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ListLiteralParser.
 *
 * Gramática BNF:
 * ListLiteral ::= '[' ExpressionList? ']'
 */
public class ListLiteralParserTest {

    private ListLiteralParser parser = new ListLiteralParser();

    @Test
    public void testEmptyList() throws Exception {
        String input = "[]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ListLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testListWithSingleElement() throws Exception {
        String input = "[42]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ListLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testListWithMultipleIntegers() throws Exception {
        String input = "[1, 2, 3]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ListLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testListWithMixedTypes() throws Exception {
        String input = "[1, \"hello\", true, null]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ListLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(4, result.size());
    }

    @Test
    public void testListWithIdentifiers() throws Exception {
        String input = "[x, y, z]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ListLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testNestedList() throws Exception {
        String input = "[[1, 2], [3, 4]]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ListLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testListWithExpressions() throws Exception {
        String input = "[1 + 2, 3 * 4]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ListLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testInvalidInput_MissingClosingBracket() throws Exception {
        String input = "[1, 2, 3";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidInput_NotAList() throws Exception {
        String input = "abc";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
