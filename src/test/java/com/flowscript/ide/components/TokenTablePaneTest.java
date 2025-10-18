package com.flowscript.ide.components;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import java.util.List;

/**
 * Test for TokenTablePane functionality.
 * Note: This test focuses on the lexer integration rather than JavaFX components
 * to avoid JavaFX initialization complexity in unit tests.
 */
public class TokenTablePaneTest {

    @Test
    public void testEmptyCodeTokenization() throws Lexer.LexicalException {
        Lexer lexer = new Lexer("", false);
        List<Token> tokens = lexer.tokenize();

        // Should contain only EOF token
        assertEquals(1, tokens.size());
        assertEquals(com.flowscript.lexer.TokenType.EOF, tokens.get(0).getType());
    }

    @Test
    public void testSimpleFunctionTokenization() throws Lexer.LexicalException {
        String code = "function add(a: integer) -> integer { return a + 1 }";
        Lexer lexer = new Lexer(code, false);
        List<Token> tokens = lexer.tokenize();

        // Should have more than just EOF token
        assertTrue(tokens.size() > 1);

        // First token should be FUNCTION
        assertEquals(com.flowscript.lexer.TokenType.FUNCTION, tokens.get(0).getType());

        // Should contain IDENTIFIER for function name 'add'
        boolean hasAddIdentifier = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.IDENTIFIER &&
                         "add".equals(t.getValue()));
        assertTrue(hasAddIdentifier, "Should contain 'add' identifier");
    }

    @Test
    public void testCodeWithLexicalError() {
        String codeWithError = "function test() { @ }"; // @ is invalid
        Lexer lexer = new Lexer(codeWithError, false);

        // Should throw LexicalException
        assertThrows(Lexer.LexicalException.class, () -> {
            lexer.tokenize();
        });
    }

    @Test
    public void testComplexFlowScriptTokenization() throws Lexer.LexicalException {
        String complexCode = """
            process TestProcess {
                start -> Task1
                task Task1 {
                    action:
                        value = 42.5
                        goto Gateway1
                }
                gateway Gateway1 {
                    when value > 0.0 -> Success
                    else -> Failed
                }
                end Success
                end Failed
            }
            """;

        Lexer lexer = new Lexer(complexCode, false);
        List<Token> tokens = lexer.tokenize();

        // Should have many tokens
        assertTrue(tokens.size() > 20);

        // Should start with PROCESS token
        assertEquals(com.flowscript.lexer.TokenType.PROCESS, tokens.get(0).getType());

        // Should contain key FlowScript tokens
        boolean hasStart = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.START);
        boolean hasTask = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.TASK);
        boolean hasGateway = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.GATEWAY);
        boolean hasWhen = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.WHEN);
        boolean hasEnd = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.END);

        assertTrue(hasStart, "Should contain START token");
        assertTrue(hasTask, "Should contain TASK token");
        assertTrue(hasGateway, "Should contain GATEWAY token");
        assertTrue(hasWhen, "Should contain WHEN token");
        assertTrue(hasEnd, "Should contain END token");
    }

    @Test
    public void testCommentsTokenization() throws Lexer.LexicalException {
        String codeWithComments = """
            # Single line comment
            function test() {
                /* Multi-line
                   comment */
                return 42
            }
            """;

        // Test without comments
        Lexer lexerNoComments = new Lexer(codeWithComments, false);
        List<Token> tokensNoComments = lexerNoComments.tokenize();

        boolean hasComment = tokensNoComments.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.SINGLE_LINE_COMMENT ||
                          t.getType() == com.flowscript.lexer.TokenType.MULTI_LINE_COMMENT);
        assertFalse(hasComment, "Should not contain comment tokens when comments are excluded");

        // Test with comments
        Lexer lexerWithComments = new Lexer(codeWithComments, true);
        List<Token> tokensWithComments = lexerWithComments.tokenize();

        boolean hasSingleLineComment = tokensWithComments.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.SINGLE_LINE_COMMENT);
        boolean hasMultiLineComment = tokensWithComments.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.MULTI_LINE_COMMENT);

        assertTrue(hasSingleLineComment, "Should contain single-line comment token");
        assertTrue(hasMultiLineComment, "Should contain multi-line comment token");
    }

    @Test
    public void testTokenRowCreation() {
        // Test the TokenRow inner class
        TokenTablePane.TokenRow row = new TokenTablePane.TokenRow(1, 5, "FUNCTION", "function", "Keyword");

        assertEquals(1, row.line);
        assertEquals(5, row.column);
        assertEquals("FUNCTION", row.type);
        assertEquals("function", row.value);
        assertEquals("Keyword", row.category);
    }

    @Test
    public void testLiteralsTokenization() throws Lexer.LexicalException {
        String code = """
            value1 = 42
            value2 = 3.14
            value3 = "hello"
            value4 = true
            value5 = false
            value6 = null
            """;

        Lexer lexer = new Lexer(code, false);
        List<Token> tokens = lexer.tokenize();

        // Check for different literal types
        boolean hasIntegerLiteral = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.INTEGER_LITERAL);
        boolean hasDecimalLiteral = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.DECIMAL_LITERAL);
        boolean hasStringLiteral = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.STRING_LITERAL);
        boolean hasTrue = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.TRUE);
        boolean hasFalse = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.FALSE);
        boolean hasNull = tokens.stream()
            .anyMatch(t -> t.getType() == com.flowscript.lexer.TokenType.NULL);

        assertTrue(hasIntegerLiteral, "Should contain integer literal");
        assertTrue(hasDecimalLiteral, "Should contain decimal literal");
        assertTrue(hasStringLiteral, "Should contain string literal");
        assertTrue(hasTrue, "Should contain TRUE token");
        assertTrue(hasFalse, "Should contain FALSE token");
        assertTrue(hasNull, "Should contain NULL token");
    }
}