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
 * Tests para LogicalAndExpressionParser.
 *
 * Gramática BNF:
 * LogicalAndExpression ::= EqualityExpression ( 'and' EqualityExpression )*
 */
public class LogicalAndExpressionParserTest {

    private LogicalAndExpressionParser parser = new LogicalAndExpressionParser();

    @Test
    public void testSimpleAnd() throws Exception {
        String input = "true and false";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testAndWithIdentifiers() throws Exception {
        String input = "x and y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testChainedAnd() throws Exception {
        String input = "a and b and c";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testAndWithComparisons() throws Exception {
        String input = "x > 5 and y < 10";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testAndWithEquality() throws Exception {
        String input = "a == b and c != d";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testComplexAndExpression() throws Exception {
        String input = "x > 0 and x < 100 and y != null";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testSingleEqualityExpression() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
    }
}
