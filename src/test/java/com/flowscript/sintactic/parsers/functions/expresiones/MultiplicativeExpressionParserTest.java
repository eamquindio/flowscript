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
 * Tests para MultiplicativeExpressionParser.
 *
 * Gramática BNF:
 * MultiplicativeExpression ::= UnaryExpression ( ( '*' | '/' | '%' ) UnaryExpression )*
 */
public class MultiplicativeExpressionParserTest {

    private MultiplicativeExpressionParser parser = new MultiplicativeExpressionParser();

    @Test
    public void testSimpleMultiplication() throws Exception {
        String input = "2 * 3";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testSimpleDivision() throws Exception {
        String input = "10 / 2";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testModuloOperation() throws Exception {
        String input = "10 % 3";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testChainedMultiplication() throws Exception {
        String input = "2 * 3 * 4";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testMixedOperations() throws Exception {
        String input = "10 * 2 / 5 % 3";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testMultiplicationWithIdentifiers() throws Exception {
        String input = "x * y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testDivisionWithDecimals() throws Exception {
        String input = "10.5 / 2.5";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testSingleUnaryExpression() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }
}
