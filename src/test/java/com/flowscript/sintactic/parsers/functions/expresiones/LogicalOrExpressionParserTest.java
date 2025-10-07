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
 * Tests para LogicalOrExpressionParser.
 *
 * Gramática BNF:
 * LogicalOrExpression ::= LogicalAndExpression ( 'or' LogicalAndExpression )*
 */
public class LogicalOrExpressionParserTest {

    private LogicalOrExpressionParser parser = new LogicalOrExpressionParser();

    @Test
    public void testSimpleOr() throws Exception {
        String input = "true or false";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testOrWithIdentifiers() throws Exception {
        String input = "x or y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testChainedOr() throws Exception {
        String input = "a or b or c";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testOrWithComparisons() throws Exception {
        String input = "x > 100 or x < 0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testOrWithAndPrecedence() throws Exception {
        String input = "a and b or c and d";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testComplexOrExpression() throws Exception {
        String input = "x == 0 or y == 0 or z == 0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testSingleAndExpression() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }
}
