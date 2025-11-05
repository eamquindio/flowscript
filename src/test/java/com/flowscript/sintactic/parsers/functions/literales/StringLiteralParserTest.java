package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.StringLiteralNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para StringLiteralParser.
 *
 * Gramática BNF:
 * StringLiteral ::= STRING_LITERAL
 *
 * NOTA: getValue() retorna el contenido procesado (sin comillas, escapes resueltos),
 * getRawValue() retorna el literal exacto con comillas y escapes.
 */
public class StringLiteralParserTest {

    private StringLiteralParser parser = new StringLiteralParser();

    @Test
    public void testSimpleString() throws Exception {
        String input = "\"Hello World\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StringLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("Hello World", result.getValue());        // contenido procesado
        assertEquals("\"Hello World\"", result.getRawValue()); // literal original
    }

    @Test
    public void testEmptyString() throws Exception {
        String input = "\"\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StringLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("", result.getValue());
        assertEquals("\"\"", result.getRawValue());
    }

    @Test
    public void testStringWithEscapes() throws Exception {
        String input = "\"Line 1\\nLine 2\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StringLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("Line 1\nLine 2", result.getValue());
        assertEquals("\"Line 1\\nLine 2\"", result.getRawValue());
    }

    @Test
    public void testStringWithQuotes() throws Exception {
        String input = "\"Quote: \\\"Hello\\\"\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StringLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("Quote: \"Hello\"", result.getValue());
        assertEquals("\"Quote: \\\"Hello\\\"\"", result.getRawValue());
    }

    @Test
    public void testStringWithMultipleEscapes() throws Exception {
        String input = "\"Tab:\\tNewline:\\nBackslash:\\\\\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StringLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("Tab:\tNewline:\nBackslash:\\", result.getValue());
        assertEquals("\"Tab:\\tNewline:\\nBackslash:\\\\\"", result.getRawValue());
    }

    @Test
    public void testStringWithUnicodeEscape() throws Exception {
        String input = "\"Unicode: \\u0041\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StringLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("Unicode: \\u0041", result.getValue());
        assertEquals("\"Unicode: \\u0041\"", result.getRawValue());
    }

    @Test
    public void testInvalidInput_Identifier() throws Exception {
        String input = "hello";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> parser.parse(context));
    }

    @Test
    public void testInvalidInput_Integer() throws Exception {
        String input = "123";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> parser.parse(context));
    }
}
