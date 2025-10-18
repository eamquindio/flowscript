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
 * Tests para TernaryExpressionParser.
 *
 * Gramática BNF:
 * TernaryExpression ::= LogicalOrExpression ( '?' Expression ':' Expression )?
 */
public class TernaryExpressionParserTest {

    private TernaryExpressionParser parser = new TernaryExpressionParser();

    @Test
    public void testSimpleTernary() throws Exception {
        String input = "x > 0 ? 1 : -1";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testTernaryWithBooleans() throws Exception {
        String input = "isValid ? true : false";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testTernaryWithStrings() throws Exception {
        String input = "flag ? \"yes\" : \"no\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testTernaryWithExpressions() throws Exception {
        String input = "a > b ? a + 1 : b - 1";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testNestedTernary() throws Exception {
        String input = "x > 0 ? y > 0 ? 1 : 2 : 3";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testTernaryWithLogicalOr() throws Exception {
        String input = "a or b ? x : y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testPlainLogicalOrExpression() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testTernaryWithFunctionCalls() throws Exception {
        String input = "condition ? func1() : func2()";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }
}
