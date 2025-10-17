package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.DecimalLiteralNode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para DecimalLiteralParser.
 *
 * Gramática BNF:
 * DecimalLiteral ::= INTEGER_TOKEN '.' INTEGER_TOKEN ( ( 'e' | 'E' ) ( '+' | '-' )? INTEGER_TOKEN )?
 *                  | INTEGER_TOKEN ( 'e' | 'E' ) ( '+' | '-' )? INTEGER_TOKEN
 * 
 * NOTA: Fue necesario corregir estos tests porque getValue() retorna BigDecimal (valor procesado),
 * no String. Para verificar el formato original, usar getRawValue() en su lugar.
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
        assertEquals(new BigDecimal("3.14"), result.getValue());
        assertEquals("3.14", result.getRawValue());
    }

    @Test
    public void testDecimalWithZero() throws Exception {
        String input = "0.0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigDecimal("0.0"), result.getValue());
        assertEquals("0.0", result.getRawValue());
    }

    @Test
    public void testDecimalWithScientificNotation() throws Exception {
        String input = "1.23e-5";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigDecimal("1.23e-5"), result.getValue());
        assertEquals("1.23e-5", result.getRawValue());
    }

    @Test
    public void testDecimalWithPositiveExponent() throws Exception {
        String input = "2.0e10";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigDecimal("2.0e10"), result.getValue());
        assertEquals("2.0e10", result.getRawValue());
    }

    @Test
    public void testScientificNotationWithoutDot() throws Exception {
        String input = "5e3";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigDecimal("5e3"), result.getValue());
        assertEquals("5e3", result.getRawValue());
    }

    @Test
    public void testLargeDecimal() throws Exception {
        String input = "123456.789012";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DecimalLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigDecimal("123456.789012"), result.getValue());
        assertEquals("123456.789012", result.getRawValue());
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
