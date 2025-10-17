package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para PrimaryExpressionParser.
 *
 * Gramática BNF:
 * PrimaryExpression ::= IDENTIFIER | Literal | ObjectLiteral | ListLiteral |
 * '(' Expression ')'
 */
public class PrimaryExpressionParserTest {

    private PrimaryExpressionParser parser = new PrimaryExpressionParser();

    @Test
    public void testIdentifier() throws Exception {
        String input = "variable";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testIntegerLiteral() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testStringLiteral() throws Exception {
        String input = "\"hello\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testBooleanLiteral() throws Exception {
        String input = "true";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testListLiteral() throws Exception {
        String input = "[1, 2, 3]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testObjectLiteral() throws Exception {
        String input = "{ name: \"John\", age: 30 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testParenthesizedExpression() throws Exception {
        String input = "(42)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testNestedParenthesizedExpression() throws Exception {
        String input = "((5 + 3))";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testComplexParenthesizedExpression() throws Exception {
        String input = "(a + b * c)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testInvalidInput_MissingClosingParen() throws Exception {
        String input = "(42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
