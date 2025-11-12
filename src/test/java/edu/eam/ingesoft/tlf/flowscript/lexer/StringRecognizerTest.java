package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de cadenas de texto.
 * Patrón: " [^"]* "
 */
class StringRecognizerTest {

    private StringRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new StringRecognizer();
    }

    @Test
    void testEmptyString() {
        assertString("\"\"", "\"\"");
    }

    @Test
    void testSimpleString() {
        assertString("\"hola\"", "\"hola\"");
        assertString("\"mundo\"", "\"mundo\"");
    }

    @Test
    void testStringWithSpaces() {
        assertString("\"hola mundo\"", "\"hola mundo\"");
        assertString("\" espacios al inicio\"", "\" espacios al inicio\"");
        assertString("\"espacios al final \"", "\"espacios al final \"");
    }

    @Test
    void testStringWithNumbers() {
        assertString("\"123\"", "\"123\"");
        assertString("\"abc123def\"", "\"abc123def\"");
    }

    @Test
    void testStringWithSpecialCharacters() {
        assertString("\"!@#$%^&*()\"", "\"!@#$%^&*()\"");
        assertString("\"a+b=c\"", "\"a+b=c\"");
        assertString("\"[]{},.:;\"", "\"[]{},.:;\"");
    }

    @Test
    void testStringWithUnderscores() {
        assertString("\"variable_nombre\"", "\"variable_nombre\"");
        assertString("\"_test_\"", "\"_test_\"");
    }

    @Test
    void testStringWithAccents() {
        assertString("\"café\"", "\"café\"");
        assertString("\"señor\"", "\"señor\"");
        assertString("\"año\"", "\"año\"");
    }

    @Test
    void testStringWithUnicodeCharacters() {
        assertString("\"你好\"", "\"你好\"");
        assertString("\"こんにちは\"", "\"こんにちは\"");
        assertString("\"🚀\"", "\"🚀\"");
    }

    @Test
    void testMultipleStringsInInput() {
        Token token = recognizer.recognize("\"first\" \"second\"", 0, 1, 1);
        assertNotNull(token);
        assertEquals("\"first\"", token.getLexeme());

        token = recognizer.recognize("\"first\" \"second\"", 8, 1, 9);
        assertEquals("\"second\"", token.getLexeme());
    }

    @Test
    void testStringFollowedByOtherTokens() {
        Token token = recognizer.recognize("\"texto\" + ", 0, 1, 1);
        assertNotNull(token);
        assertEquals("\"texto\"", token.getLexeme());
    }

    @Test
    void testUnterminatedString() {
        // String sin comilla de cierre debe lanzar excepción
        assertThrows(LexerException.class, () -> {
            recognizer.recognize("\"sin cierre", 0, 1, 1);
        });
    }

    @Test
    void testUnterminatedStringAtEndOfFile() {
        assertThrows(LexerException.class, () -> {
            recognizer.recognize("\"no termina", 0, 1, 1);
        });
    }

    @Test
    void testNonStringInput() {
        Token token = recognizer.recognize("abc", 0, 1, 1);
        assertNull(token, "No debe reconocer texto sin comillas");

        token = recognizer.recognize("123", 0, 1, 1);
        assertNull(token, "No debe reconocer números");
    }

    @Test
    void testEmptyInput() {
        Token token = recognizer.recognize("", 0, 1, 1);
        assertNull(token);
    }

    @Test
    void testSingleQuote() {
        assertThrows(LexerException.class, () -> {
            recognizer.recognize("\"", 0, 1, 1);
        });
    }

    @Test
    void testStringAtPosition() {
        // Test reconocimiento en posiciones diferentes de 0
        String input = "mensaje = \"hola\"";
        Token token = recognizer.recognize(input, 10, 1, 11);
        assertNotNull(token);
        assertEquals("\"hola\"", token.getLexeme());
    }

    @Test
    void testVeryLongString() {
        String content = "a".repeat(1000);
        String longString = "\"" + content + "\"";
        assertString(longString, longString);
    }

    @Test
    void testStringWithTabs() {
        assertString("\"\t\"", "\"\t\"");
        assertString("\"valor\tcon\ttabs\"", "\"valor\tcon\ttabs\"");
    }

    @Test
    void testStringWithNewlines() {
        // Nota: en la versión simplificada, las cadenas no manejan
        // saltos de línea dentro de ellas, pero el test documenta el comportamiento
        assertString("\"linea1\nlinea2\"", "\"linea1\nlinea2\"");
    }

    /**
     * Método auxiliar para verificar que una cadena se reconoce correctamente.
     */
    private void assertString(String input, String expectedLexeme) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como STRING_LITERAL", input));
        assertEquals(TokenType.STRING_LITERAL, token.getType());
        assertEquals(expectedLexeme, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
