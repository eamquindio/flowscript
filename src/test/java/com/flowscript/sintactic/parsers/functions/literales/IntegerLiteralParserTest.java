package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.IntegerLiteralNode;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class IntegerLiteralParserTest {

    private IntegerLiteralParser parser = new IntegerLiteralParser();

    @Test
    public void testSimpleInteger() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IntegerLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigInteger("42"), result.getValue());
        assertEquals("42", result.getRawValue());
    }

    @Test
    public void testZero() throws Exception {
        String input = "0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IntegerLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigInteger("0"), result.getValue());
        assertEquals("0", result.getRawValue());
    }

    @Test
    public void testIntegerWithUnderscore() throws Exception {
        String input = "1_000_000";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IntegerLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigInteger("1000000"), result.getValue());
        assertEquals("1_000_000", result.getRawValue());
    }

    @Test
    public void testLargeInteger() throws Exception {
        String input = "9876543210";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IntegerLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigInteger("9876543210"), result.getValue());
        assertEquals("9876543210", result.getRawValue());
    }

    @Test
    public void testIntegerWithMultipleUnderscores() throws Exception {
        String input = "1_2_3_4_5";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        IntegerLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(new BigInteger("12345"), result.getValue());
        assertEquals("1_2_3_4_5", result.getRawValue());
    }

    @Test
    public void testInvalidInput_DecimalNumber() throws Exception {
        String input = "3.14";
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
