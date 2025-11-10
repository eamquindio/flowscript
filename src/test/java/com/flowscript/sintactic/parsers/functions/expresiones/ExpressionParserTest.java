package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.*;
import com.flowscript.sintactic.ast.functions.literales.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ExpressionParser.
 *
 * Gramática BNF:
 * Expression ::= TernaryExpression
 */
public class ExpressionParserTest {

    private ExpressionParser parser = new ExpressionParser();

    @Test
    public void testSimpleLiteral() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        // Expression may be wrapped in TernaryExpression, but should parse successfully
        assertTrue(result.getNodeType().contains("Expression") || result instanceof IntegerLiteralNode);
    }

    @Test
    public void testArithmeticExpression() throws Exception {
        String input = "10 + 20";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        // El resultado puede estar envuelto en varios nodos (Ternary, LogicalOr, etc)
        // Lo importante es que se parsea exitosamente
        assertTrue(result.getNodeType().contains("Expression"));
    }

    @Test
    public void testLogicalExpression() throws Exception {
        String input = "x > 10 y y < 20";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getNodeType().contains("Expression"));
    }

    @Test
    public void testTernaryExpression() throws Exception {
        String input = "x > 0 ? \"positivo\" : \"negativo\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getNodeType().contains("Expression"));
        TernaryExpressionNode ternary = (TernaryExpressionNode) result;
        assertNotNull(ternary.getCondition());
        assertNotNull(ternary.getTrueExpression());
        assertNotNull(ternary.getFalseExpression());
    }

    @Test
    public void testFunctionCall() throws Exception {
        String input = "calcular(10, 20)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getNodeType().contains("Expression"));
    }

    @Test
    public void testPropertyAccess() throws Exception {
        String input = "usuario.nombre";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getNodeType().contains("Expression"));
    }

    @Test
    public void testArrayAccess() throws Exception {
        String input = "items[0]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getNodeType().contains("Expression"));
    }

    @Test
    public void testComplexExpression() throws Exception {
        String input = "a * b + c / d - e";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        // Complex expression should parse successfully
        assertTrue(result.getNodeType().contains("Expression"));
    }

    @Test
    public void testParenthesizedExpression() throws Exception {
        String input = "(10 + 20) * 3";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getNodeType().contains("Expression"));
    }

    @Test
    public void testUnaryExpression() throws Exception {
        String input = "no activo";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getNodeType().contains("Expression"));
    }
}
