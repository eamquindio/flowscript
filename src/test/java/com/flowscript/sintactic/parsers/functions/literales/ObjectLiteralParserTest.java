package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ObjectLiteralParser.
 *
 * Gramática BNF:
 * ObjectLiteral ::= '{' ObjectMemberList? '}'
 */
public class ObjectLiteralParserTest {

    private ObjectLiteralParser parser = new ObjectLiteralParser();

    @Test
    public void testEmptyObject() throws Exception {
        String input = "{}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getMembers());
        assertTrue(result.getMembers().isEmpty());
    }

    @Test
    public void testObjectWithSingleMember() throws Exception {
        String input = "{ name: \"John\" }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getMembers());
        assertEquals(1, result.getMembers().size());
    }

    @Test
    public void testObjectWithMultipleMembers() throws Exception {
        String input = "{ name: \"John\", age: 30, active: true }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getMembers());
        assertEquals(3, result.getMembers().size());
    }

    @Test
    public void testObjectWithStringKeys() throws Exception {
        String input = "{ \"first-name\": \"John\", \"last-name\": \"Doe\" }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getMembers());
        assertEquals(2, result.getMembers().size());
    }

    @Test
    public void testNestedObject() throws Exception {
        String input = "{ person: { name: \"John\", age: 30 } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getMembers());
        assertEquals(1, result.getMembers().size());
    }

    @Test
    public void testObjectWithArrayValue() throws Exception {
        String input = "{ items: [1, 2, 3] }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getMembers());
        assertEquals(1, result.getMembers().size());
    }

    @Test
    public void testObjectWithExpressionValues() throws Exception {
        String input = "{ total: 10 + 20, double: 5 * 2 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getMembers());
        assertEquals(2, result.getMembers().size());
    }

    @Test
    public void testObjectWithMixedKeyTypes() throws Exception {
        String input = "{ name: \"John\", \"full-name\": \"John Doe\", age: 30 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getMembers());
        assertEquals(3, result.getMembers().size());
    }

    @Test
    public void testInvalidInput_MissingClosingBrace() throws Exception {
        String input = "{ name: \"John\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidInput_NotAnObject() throws Exception {
        String input = "abc";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
