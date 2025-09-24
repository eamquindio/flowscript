package com.flowscript.lexer;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {

    @Test
    public void testKeywords() throws Lexer.LexicalException {
        String input = "process function import as return if else while for";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.PROCESS, tokens.get(0).getType());
        assertEquals(TokenType.FUNCTION, tokens.get(1).getType());
        assertEquals(TokenType.IMPORT, tokens.get(2).getType());
        assertEquals(TokenType.AS, tokens.get(3).getType());
        assertEquals(TokenType.RETURN, tokens.get(4).getType());
        assertEquals(TokenType.IF, tokens.get(5).getType());
        assertEquals(TokenType.ELSE, tokens.get(6).getType());
        assertEquals(TokenType.WHILE, tokens.get(7).getType());
        assertEquals(TokenType.FOR, tokens.get(8).getType());
        assertEquals(TokenType.EOF, tokens.get(9).getType());
    }

    @Test
    public void testIdentifiers() throws Lexer.LexicalException {
        String input = "customer_id _temp ProcessStep3 myVariable";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("customer_id", tokens.get(0).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("_temp", tokens.get(1).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(2).getType());
        assertEquals("ProcessStep3", tokens.get(2).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(3).getType());
        assertEquals("myVariable", tokens.get(3).getValue());
    }

    @Test
    public void testIntegerLiterals() throws Lexer.LexicalException {
        String input = "42 1_000_000 -17 0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.INTEGER_LITERAL, tokens.get(0).getType());
        assertEquals("42", tokens.get(0).getValue());
        assertEquals(TokenType.INTEGER_LITERAL, tokens.get(1).getType());
        assertEquals("1_000_000", tokens.get(1).getValue());
        assertEquals(TokenType.INTEGER_LITERAL, tokens.get(2).getType());
        assertEquals("-17", tokens.get(2).getValue());
        assertEquals(TokenType.INTEGER_LITERAL, tokens.get(3).getType());
        assertEquals("0", tokens.get(3).getValue());
    }

    @Test
    public void testDecimalLiterals() throws Lexer.LexicalException {
        String input = "3.14 1.23e-5 -0.5 2.0e10";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.DECIMAL_LITERAL, tokens.get(0).getType());
        assertEquals("3.14", tokens.get(0).getValue());
        assertEquals(TokenType.DECIMAL_LITERAL, tokens.get(1).getType());
        assertEquals("1.23e-5", tokens.get(1).getValue());
        assertEquals(TokenType.DECIMAL_LITERAL, tokens.get(2).getType());
        assertEquals("-0.5", tokens.get(2).getValue());
        assertEquals(TokenType.DECIMAL_LITERAL, tokens.get(3).getType());
        assertEquals("2.0e10", tokens.get(3).getValue());
    }

    @Test
    public void testStringLiterals() throws Lexer.LexicalException {
        String input = "\"Hello World\" \"Line 1\\nLine 2\" \"Quote: \\\"Hello\\\"\" \"\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.STRING_LITERAL, tokens.get(0).getType());
        assertEquals("\"Hello World\"", tokens.get(0).getValue());
        assertEquals(TokenType.STRING_LITERAL, tokens.get(1).getType());
        assertEquals("\"Line 1\\nLine 2\"", tokens.get(1).getValue());
        assertEquals(TokenType.STRING_LITERAL, tokens.get(2).getType());
        assertEquals("\"Quote: \\\"Hello\\\"\"", tokens.get(2).getValue());
        assertEquals(TokenType.STRING_LITERAL, tokens.get(3).getType());
        assertEquals("\"\"", tokens.get(3).getValue());
    }

    @Test
    public void testOperators() throws Lexer.LexicalException {
        String input = "+ - * / % < > <= >= == != = . -> and or not";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.PLUS, tokens.get(0).getType());
        assertEquals(TokenType.MINUS, tokens.get(1).getType());
        assertEquals(TokenType.MULTIPLY, tokens.get(2).getType());
        assertEquals(TokenType.DIVIDE, tokens.get(3).getType());
        assertEquals(TokenType.MODULO, tokens.get(4).getType());
        assertEquals(TokenType.LESS_THAN, tokens.get(5).getType());
        assertEquals(TokenType.GREATER_THAN, tokens.get(6).getType());
        assertEquals(TokenType.LESS_EQUAL, tokens.get(7).getType());
        assertEquals(TokenType.GREATER_EQUAL, tokens.get(8).getType());
        assertEquals(TokenType.EQUAL, tokens.get(9).getType());
        assertEquals(TokenType.NOT_EQUAL, tokens.get(10).getType());
        assertEquals(TokenType.ASSIGN, tokens.get(11).getType());
        assertEquals(TokenType.DOT, tokens.get(12).getType());
        assertEquals(TokenType.ARROW, tokens.get(13).getType());
        assertEquals(TokenType.AND, tokens.get(14).getType());
        assertEquals(TokenType.OR, tokens.get(15).getType());
        assertEquals(TokenType.NOT, tokens.get(16).getType());
    }

    @Test
    public void testDelimiters() throws Lexer.LexicalException {
        String input = "( ) { } [ ] , ; :";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).getType());
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).getType());
        assertEquals(TokenType.LEFT_BRACE, tokens.get(2).getType());
        assertEquals(TokenType.RIGHT_BRACE, tokens.get(3).getType());
        assertEquals(TokenType.LEFT_BRACKET, tokens.get(4).getType());
        assertEquals(TokenType.RIGHT_BRACKET, tokens.get(5).getType());
        assertEquals(TokenType.COMMA, tokens.get(6).getType());
        assertEquals(TokenType.SEMICOLON, tokens.get(7).getType());
        assertEquals(TokenType.COLON, tokens.get(8).getType());
    }

    @Test
    public void testComments() throws Lexer.LexicalException {
        String input = "# Single line comment\ncode /* Multi\nline\ncomment */ more_code";

        // Test without comments
        Lexer lexer1 = new Lexer(input, false);
        List<Token> tokens1 = lexer1.tokenize();
        assertEquals(TokenType.IDENTIFIER, tokens1.get(0).getType());
        assertEquals("code", tokens1.get(0).getValue());
        assertEquals(TokenType.IDENTIFIER, tokens1.get(1).getType());
        assertEquals("more_code", tokens1.get(1).getValue());

        // Test with comments
        Lexer lexer2 = new Lexer(input, true);
        List<Token> tokens2 = lexer2.tokenize();
        assertEquals(TokenType.SINGLE_LINE_COMMENT, tokens2.get(0).getType());
        assertEquals(TokenType.IDENTIFIER, tokens2.get(1).getType());
        assertEquals(TokenType.MULTI_LINE_COMMENT, tokens2.get(2).getType());
        assertEquals(TokenType.IDENTIFIER, tokens2.get(3).getType());
    }

    @Test
    public void testComplexFunction() throws Lexer.LexicalException {
        String input = "function calculate_total(items: list, tax_rate: decimal) -> decimal {\n" +
                       "    subtotal = 0.0\n" +
                       "    return subtotal + tax\n" +
                       "}";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.FUNCTION, tokens.get(0).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("calculate_total", tokens.get(1).getValue());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(2).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(3).getType());
        assertEquals("items", tokens.get(3).getValue());
        assertEquals(TokenType.COLON, tokens.get(4).getType());
        assertEquals(TokenType.LIST_TYPE, tokens.get(5).getType());
        assertEquals(TokenType.COMMA, tokens.get(6).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(7).getType());
        assertEquals("tax_rate", tokens.get(7).getValue());
        assertEquals(TokenType.COLON, tokens.get(8).getType());
        assertEquals(TokenType.DECIMAL_TYPE, tokens.get(9).getType());
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(10).getType());
        assertEquals(TokenType.ARROW, tokens.get(11).getType());
        assertEquals(TokenType.DECIMAL_TYPE, tokens.get(12).getType());
        assertEquals(TokenType.LEFT_BRACE, tokens.get(13).getType());
    }

    @Test
    public void testProcessSyntax() throws Lexer.LexicalException {
        String input = "process OrderValidation {\n" +
                       "    start -> ValidateInput\n" +
                       "    task ValidateInput {\n" +
                       "        action:\n" +
                       "            goto CheckInventory\n" +
                       "    }\n" +
                       "    end Success\n" +
                       "}";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.PROCESS, tokens.get(0).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("OrderValidation", tokens.get(1).getValue());
        assertEquals(TokenType.LEFT_BRACE, tokens.get(2).getType());
        assertEquals(TokenType.START, tokens.get(3).getType());
        assertEquals(TokenType.ARROW, tokens.get(4).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(5).getType());
        assertEquals("ValidateInput", tokens.get(5).getValue());
        assertEquals(TokenType.TASK, tokens.get(6).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(7).getType());
        assertEquals(TokenType.LEFT_BRACE, tokens.get(8).getType());
        assertEquals(TokenType.ACTION, tokens.get(9).getType());
        assertEquals(TokenType.COLON, tokens.get(10).getType());
        assertEquals(TokenType.GOTO, tokens.get(11).getType());
    }

    @Test
    public void testBooleanLiterals() throws Lexer.LexicalException {
        String input = "true false null";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.TRUE, tokens.get(0).getType());
        assertEquals(TokenType.FALSE, tokens.get(1).getType());
        assertEquals(TokenType.NULL, tokens.get(2).getType());
    }

    @Test
    public void testLineAndColumnTracking() throws Lexer.LexicalException {
        String input = "a\nb\nc";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(1, tokens.get(0).getLine());
        assertEquals(1, tokens.get(0).getColumn());
        assertEquals(2, tokens.get(1).getLine());
        assertEquals(1, tokens.get(1).getColumn());
        assertEquals(3, tokens.get(2).getLine());
        assertEquals(1, tokens.get(2).getColumn());
    }

    @Test
    public void testInvalidCharacter() {
        String input = "valid @invalid";
        Lexer lexer = new Lexer(input);

        assertThrows(Lexer.LexicalException.class, () -> {
            lexer.tokenize();
        });
    }

    @Test
    public void testGatewayKeywords() throws Lexer.LexicalException {
        String input = "gateway when branch join parallel else";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.GATEWAY, tokens.get(0).getType());
        assertEquals(TokenType.WHEN, tokens.get(1).getType());
        assertEquals(TokenType.BRANCH, tokens.get(2).getType());
        assertEquals(TokenType.JOIN, tokens.get(3).getType());
        assertEquals(TokenType.PARALLEL, tokens.get(4).getType());
        assertEquals(TokenType.ELSE, tokens.get(5).getType());
    }

    @Test
    public void testListAndObjectLiterals() throws Lexer.LexicalException {
        String input = "[1, 2, 3] { name: \"John\", age: 30 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.LEFT_BRACKET, tokens.get(0).getType());
        assertEquals(TokenType.INTEGER_LITERAL, tokens.get(1).getType());
        assertEquals(TokenType.COMMA, tokens.get(2).getType());
        assertEquals(TokenType.INTEGER_LITERAL, tokens.get(3).getType());
        assertEquals(TokenType.COMMA, tokens.get(4).getType());
        assertEquals(TokenType.INTEGER_LITERAL, tokens.get(5).getType());
        assertEquals(TokenType.RIGHT_BRACKET, tokens.get(6).getType());
        assertEquals(TokenType.LEFT_BRACE, tokens.get(7).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(8).getType());
        assertEquals("name", tokens.get(8).getValue());
        assertEquals(TokenType.COLON, tokens.get(9).getType());
        assertEquals(TokenType.STRING_LITERAL, tokens.get(10).getType());
    }
}