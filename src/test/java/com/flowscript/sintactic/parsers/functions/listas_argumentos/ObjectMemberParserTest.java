package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ObjectMemberParser.
 *
 * Gramática BNF:
 * ObjectMember ::= IDENTIFIER ':' Expression | STRING_LITERAL ':' Expression
 */
public class ObjectMemberParserTest {

    private ObjectMemberParser parser = new ObjectMemberParser();

    @Test
    public void testIdentifierKey() throws Exception {
        String input = "name: \"John\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode.ObjectMemberNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("name", result.getKey());
    }

    @Test
    public void testStringKey() throws Exception {
        String input = "firstName: \"John\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode.ObjectMemberNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("firstName", result.getKey());
    }

    @Test
    public void testValueAsInteger() throws Exception {
        String input = "age: 30";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode.ObjectMemberNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getValue());
    }

    @Test
    public void testValueAsBoolean() throws Exception {
        String input = "active: true";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode.ObjectMemberNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getValue());
    }

    @Test
    public void testValueAsObject() throws Exception {
        String input = "person: { name: \"John\" }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode.ObjectMemberNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getValue());
    }

    @Test
    public void testValueAsExpression() throws Exception {
        String input = "total: x + y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode.ObjectMemberNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getValue());
    }
}
