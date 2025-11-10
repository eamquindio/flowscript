package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para PostfixOperatorParser.
 *
 * Gramática BNF:
 * PostfixOperator ::= '.' IDENTIFIER | '[' Expression ']' | '(' ArgumentList? ')'
 */
public class PostfixOperatorParserTest {

    private PostfixOperatorParser parser = new PostfixOperatorParser();

    @Test
    public void testPropertyAccess() throws Exception {
        String input = ".nombre";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.PropertyAccessOperatorNode);
        PostfixOperatorNode.PropertyAccessOperatorNode propAccess =
            (PostfixOperatorNode.PropertyAccessOperatorNode) result;
        assertEquals("nombre", propAccess.getPropertyName());
    }

    @Test
    public void testPropertyAccess_NestedProperty() throws Exception {
        String input = ".direccion";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.PropertyAccessOperatorNode);
        assertEquals("direccion", ((PostfixOperatorNode.PropertyAccessOperatorNode) result).getPropertyName());
    }

    @Test
    public void testIndexAccess_IntegerIndex() throws Exception {
        String input = "[0]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.IndexAccessOperatorNode);
        PostfixOperatorNode.IndexAccessOperatorNode indexAccess =
            (PostfixOperatorNode.IndexAccessOperatorNode) result;
        assertNotNull(indexAccess.getIndex());
    }

    @Test
    public void testIndexAccess_VariableIndex() throws Exception {
        String input = "[i]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.IndexAccessOperatorNode);
    }

    @Test
    public void testIndexAccess_ExpressionIndex() throws Exception {
        String input = "[i + 1]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.IndexAccessOperatorNode);
    }

    @Test
    public void testFunctionCall_NoArguments() throws Exception {
        String input = "()";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.FunctionCallOperatorNode);
        PostfixOperatorNode.FunctionCallOperatorNode funcCall =
            (PostfixOperatorNode.FunctionCallOperatorNode) result;
        assertEquals(0, funcCall.getArguments().size());
    }

    @Test
    public void testFunctionCall_OneArgument() throws Exception {
        String input = "(42)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.FunctionCallOperatorNode);
        PostfixOperatorNode.FunctionCallOperatorNode funcCall =
            (PostfixOperatorNode.FunctionCallOperatorNode) result;
        assertEquals(1, funcCall.getArguments().size());
    }

    @Test
    public void testFunctionCall_MultipleArguments() throws Exception {
        String input = "(10, 20, 30)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.FunctionCallOperatorNode);
        PostfixOperatorNode.FunctionCallOperatorNode funcCall =
            (PostfixOperatorNode.FunctionCallOperatorNode) result;
        assertEquals(3, funcCall.getArguments().size());
    }

    @Test
    public void testFunctionCall_ComplexArguments() throws Exception {
        String input = "(x + y, \"mensaje\", verdadero)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        PostfixOperatorNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof PostfixOperatorNode.FunctionCallOperatorNode);
        assertEquals(3, ((PostfixOperatorNode.FunctionCallOperatorNode) result).getArguments().size());
    }

    @Test
    public void testInvalidOperator_MissingIdentifierAfterDot() throws Exception {
        String input = ".";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidOperator_UnclosedBracket() throws Exception {
        String input = "[0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidOperator_UnclosedParen() throws Exception {
        String input = "(42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
