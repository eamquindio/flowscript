package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.DecimalLiteralNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para DecimalLiteralParser.
 *
 * Gramática BNF:
 * DecimalLiteral ::= INTEGER_TOKEN '.' INTEGER_TOKEN ( ( 'e' | 'E' ) ( '+' | '-' )? INTEGER_TOKEN )?
 *                  | INTEGER_TOKEN ( 'e' | 'E' ) ( '+' | '-' )? INTEGER_TOKEN
 */
public class DecimalLiteralParserTest {

    private DecimalLiteralParser parser = new DecimalLiteralParser();

    @Test
    public void testSimpleDecimal() throws Exception {
        String input = "3.14";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("3.14", result.getValue());
    }

    @Test
    public void testDecimalWithZero() throws Exception {
        String input = "0.0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("0.0", result.getValue());
    }

    @Test
    public void testDecimalWithScientificNotation() throws Exception {
        String input = "1.23e-5";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("1.23e-5", result.getValue());
    }

    @Test
    public void testDecimalWithPositiveExponent() throws Exception {
        String input = "2.0e10";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("2.0e10", result.getValue());
    }

    @Test
    public void testScientificNotationWithoutDot() throws Exception {
        String input = "5e3";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("5e3", result.getValue());
    }

    @Test
    public void testLargeDecimal() throws Exception {
        String input = "123456.789012";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("123456.789012", result.getValue());
    }

    @Test
    public void testInvalidInput_Integer() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidInput_Identifier() throws Exception {
        String input = "abc";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
