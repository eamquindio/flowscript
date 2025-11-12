package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de números (enteros y decimales).
 * INTEGER_LITERAL: [0-9]+
 * DECIMAL_LITERAL: [0-9]+ '.' [0-9]+
 */
class NumberRecognizerTest {

    private NumberRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new NumberRecognizer();
    }

    // Tests para INTEGER_LITERAL

    @Test
    void testSingleDigitInteger() {
        assertInteger("0", "0");
        assertInteger("5", "5");
        assertInteger("9", "9");
    }

    @Test
    void testMultipleDigitInteger() {
        assertInteger("123", "123");
        assertInteger("42", "42");
        assertInteger("999", "999");
    }

    @Test
    void testLargeInteger() {
        assertInteger("123456789", "123456789");
        assertInteger("999999999999", "999999999999");
    }

    @Test
    void testIntegerFollowedByWhitespace() {
        Token token = recognizer.recognize("123 ", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.INTEGER_LITERAL, token.getType());
        assertEquals("123", token.getLexeme());
    }

    @Test
    void testIntegerFollowedByOperator() {
        Token token = recognizer.recognize("123+", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.INTEGER_LITERAL, token.getType());
        assertEquals("123", token.getLexeme());
    }

    @Test
    void testIntegerFollowedByDelimiter() {
        Token token = recognizer.recognize("123)", 0, 1, 1);
        assertNotNull(token);
        assertEquals("123", token.getLexeme());

        token = recognizer.recognize("456]", 0, 1, 1);
        assertEquals("456", token.getLexeme());
    }

    // Tests para DECIMAL_LITERAL

    @Test
    void testSimpleDecimal() {
        assertDecimal("1.5", "1.5");
        assertDecimal("3.14", "3.14");
        assertDecimal("0.5", "0.5");
    }

    @Test
    void testDecimalWithMultipleDigits() {
        assertDecimal("123.456", "123.456");
        assertDecimal("999.999", "999.999");
    }

    @Test
    void testDecimalWithZero() {
        assertDecimal("0.0", "0.0");
        assertDecimal("0.123", "0.123");
        assertDecimal("100.0", "100.0");
    }

    @Test
    void testDecimalFollowedByWhitespace() {
        Token token = recognizer.recognize("3.14 ", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.DECIMAL_LITERAL, token.getType());
        assertEquals("3.14", token.getLexeme());
    }

    @Test
    void testDecimalFollowedByOperator() {
        Token token = recognizer.recognize("2.5+", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.DECIMAL_LITERAL, token.getType());
        assertEquals("2.5", token.getLexeme());
    }

    // Tests para casos especiales

    @Test
    void testIntegerFollowedByDotWithoutDigits() {
        // "123." debe reconocerse como integer, no como decimal incompleto
        Token token = recognizer.recognize("123.", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.INTEGER_LITERAL, token.getType());
        assertEquals("123", token.getLexeme());
    }

    @Test
    void testIntegerFollowedByDotAndNonDigit() {
        // "123.abc" debe reconocerse como integer
        Token token = recognizer.recognize("123.abc", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.INTEGER_LITERAL, token.getType());
        assertEquals("123", token.getLexeme());
    }

    @Test
    void testEmptyInput() {
        Token token = recognizer.recognize("", 0, 1, 1);
        assertNull(token);
    }

    @Test
    void testNonDigitStart() {
        Token token = recognizer.recognize("abc123", 0, 1, 1);
        assertNull(token, "No debe reconocer si no empieza con dígito");

        token = recognizer.recognize(".123", 0, 1, 1);
        assertNull(token, "No debe reconocer si empieza con punto");
    }

    @Test
    void testNumberAtPosition() {
        // Test reconocimiento en posiciones diferentes de 0
        String input = "suma(123, 456)";
        Token token = recognizer.recognize(input, 5, 1, 6);
        assertNotNull(token);
        assertEquals("123", token.getLexeme());
    }

    @Test
    void testVeryLongNumber() {
        String longInt = "123456789012345678901234567890";
        assertInteger(longInt, longInt);

        String longDecimal = "123456789.987654321";
        assertDecimal(longDecimal, longDecimal);
    }

    /**
     * Método auxiliar para verificar que un entero se reconoce correctamente.
     */
    private void assertInteger(String input, String expectedLexeme) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como INTEGER_LITERAL", input));
        assertEquals(TokenType.INTEGER_LITERAL, token.getType());
        assertEquals(expectedLexeme, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }

    /**
     * Método auxiliar para verificar que un decimal se reconoce correctamente.
     */
    private void assertDecimal(String input, String expectedLexeme) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como DECIMAL_LITERAL", input));
        assertEquals(TokenType.DECIMAL_LITERAL, token.getType());
        assertEquals(expectedLexeme, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
