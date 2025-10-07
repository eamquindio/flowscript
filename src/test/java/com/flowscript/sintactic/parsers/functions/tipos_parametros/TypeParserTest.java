package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para TypeParser.
 *
 * Gramática BNF:
 * Type ::= 'integer' | 'decimal' | 'boolean' | 'text' | 'list' | 'object' | 'void'
 */
public class TypeParserTest {

    private TypeParser parser = new TypeParser();

    @Test
    public void testIntegerType() throws Exception {
        String input = "integer";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TypeNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("integer", result.getTypeName());
    }

    @Test
    public void testDecimalType() throws Exception {
        String input = "decimal";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TypeNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("decimal", result.getTypeName());
    }

    @Test
    public void testBooleanType() throws Exception {
        String input = "boolean";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TypeNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("boolean", result.getTypeName());
    }

    @Test
    public void testTextType() throws Exception {
        String input = "text";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TypeNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("text", result.getTypeName());
    }

    @Test
    public void testListType() throws Exception {
        String input = "list";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TypeNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("list", result.getTypeName());
    }

    @Test
    public void testObjectType() throws Exception {
        String input = "object";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TypeNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("object", result.getTypeName());
    }

    @Test
    public void testVoidType() throws Exception {
        String input = "void";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TypeNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("void", result.getTypeName());
    }

    @Test
    public void testInvalidType() throws Exception {
        String input = "string";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
