package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de espacios en blanco.
 * Patrón: [ \t\n\r]+
 */
class WhitespaceRecognizerTest {

    private WhitespaceRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new WhitespaceRecognizer();
    }

    @Test
    void testSingleSpace() {
        assertWhitespace(" ", " ");
    }

    @Test
    void testMultipleSpaces() {
        assertWhitespace("   ", "   ");
        assertWhitespace("          ", "          ");
    }

    @Test
    void testTab() {
        assertWhitespace("\t", "\t");
    }

    @Test
    void testMultipleTabs() {
        assertWhitespace("\t\t", "\t\t");
        assertWhitespace("\t\t\t", "\t\t\t");
    }

    @Test
    void testNewline() {
        assertWhitespace("\n", "\n");
    }

    @Test
    void testCarriageReturn() {
        assertWhitespace("\r", "\r");
    }

    @Test
    void testCarriageReturnNewline() {
        assertWhitespace("\r\n", "\r\n");
    }

    @Test
    void testMixedWhitespace() {
        assertWhitespace(" \t ", " \t ");
        assertWhitespace("\t \t", "\t \t");
        assertWhitespace(" \n ", " \n ");
        assertWhitespace("  \t\n\r  ", "  \t\n\r  ");
    }

    @Test
    void testWhitespaceFollowedByNonWhitespace() {
        Token token = recognizer.recognize("  abc", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.WHITESPACE, token.getType());
        assertEquals("  ", token.getLexeme());
    }

    @Test
    void testWhitespaceFollowedByOperator() {
        Token token = recognizer.recognize(" +", 0, 1, 1);
        assertNotNull(token);
        assertEquals(" ", token.getLexeme());
    }

    @Test
    void testWhitespaceFollowedByDelimiter() {
        Token token = recognizer.recognize("\t(", 0, 1, 1);
        assertNotNull(token);
        assertEquals("\t", token.getLexeme());
    }

    @Test
    void testEmptyInput() {
        Token token = recognizer.recognize("", 0, 1, 1);
        assertNull(token);
    }

    @Test
    void testNonWhitespaceChar() {
        Token token = recognizer.recognize("a", 0, 1, 1);
        assertNull(token, "Letra no es espacio en blanco");

        token = recognizer.recognize("5", 0, 1, 1);
        assertNull(token, "Dígito no es espacio en blanco");

        token = recognizer.recognize("+", 0, 1, 1);
        assertNull(token, "Operador no es espacio en blanco");
    }

    @Test
    void testWhitespaceAtPosition() {
        // Test reconocimiento en posiciones diferentes de 0
        String input = "a   b";
        Token token = recognizer.recognize(input, 1, 1, 2);
        assertNotNull(token);
        assertEquals("   ", token.getLexeme());
    }

    @Test
    void testVeryLongWhitespace() {
        String longWhitespace = " ".repeat(100);
        assertWhitespace(longWhitespace, longWhitespace);
    }

    @Test
    void testMultipleLinesWhitespace() {
        String multiline = "\n\n\n";
        assertWhitespace(multiline, multiline);

        String mixedMultiline = " \n \n ";
        assertWhitespace(mixedMultiline, mixedMultiline);
    }

    /**
     * Método auxiliar para verificar que un espacio en blanco se reconoce correctamente.
     */
    private void assertWhitespace(String input, String expectedLexeme) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como WHITESPACE", input));
        assertEquals(TokenType.WHITESPACE, token.getType());
        assertEquals(expectedLexeme, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
