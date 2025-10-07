package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ParameterParser.
 *
 * Gramática BNF:
 * Parameter ::= IDENTIFIER ':' Type
 */
public class ParameterParserTest {

    private ParameterParser parser = new ParameterParser();

    @Test
    public void testSimpleParameter() throws Exception {
        String input = "x: integer";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ParameterNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("x", result.getName());
        assertEquals("integer", result.getType().getTypeName());
    }

    @Test
    public void testParameterWithDecimalType() throws Exception {
        String input = "amount: decimal";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ParameterNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("amount", result.getName());
        assertEquals("decimal", result.getType().getTypeName());
    }

    @Test
    public void testParameterWithTextType() throws Exception {
        String input = "name: text";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ParameterNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("name", result.getName());
        assertEquals("text", result.getType().getTypeName());
    }

    @Test
    public void testParameterWithListType() throws Exception {
        String input = "items: list";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ParameterNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("items", result.getName());
        assertEquals("list", result.getType().getTypeName());
    }

    @Test
    public void testParameterWithObjectType() throws Exception {
        String input = "config: object";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ParameterNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("config", result.getName());
        assertEquals("object", result.getType().getTypeName());
    }

    @Test
    public void testInvalidParameter_MissingColon() throws Exception {
        String input = "x integer";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidParameter_MissingType() throws Exception {
        String input = "x:";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
