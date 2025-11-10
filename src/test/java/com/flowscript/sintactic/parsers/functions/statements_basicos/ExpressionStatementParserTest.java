package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.statements_basicos.ExpressionStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ExpressionStatementParser.
 *
 * Gramática BNF:
 * ExpressionStatement ::= Expression
 */
public class ExpressionStatementParserTest {

    private ExpressionStatementParser parser = new ExpressionStatementParser();

    @Test
    public void testSimpleFunctionCall() throws Exception {
        String input = "imprimir(\"Hola\")";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getExpression());
    }

    @Test
    public void testFunctionCallWithMultipleArguments() throws Exception {
        String input = "calcular(10, 20, 30)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getExpression());
    }

    @Test
    public void testMethodCall() throws Exception {
        String input = "usuario.enviar_email(\"mensaje\")";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testChainedMethodCalls() throws Exception {
        String input = "db.usuarios.buscar(\"nombre\")";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testPropertyAccess() throws Exception {
        String input = "usuario.nombre";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testArrayAccess() throws Exception {
        String input = "items[0]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testSimpleExpression() throws Exception {
        String input = "x + y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testComplexExpression() throws Exception {
        String input = "a * b + c / d - e";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testLogicalExpression() throws Exception {
        String input = "x > 10 y y < 20";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testLiteralExpression() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testStringLiteralExpression() throws Exception {
        String input = "\"Mensaje\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testObjectLiteralExpression() throws Exception {
        String input = "{ nombre: \"Ana\", edad: 25 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testInvalidExpression_EndOfInput() throws Exception {
        String input = "";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
