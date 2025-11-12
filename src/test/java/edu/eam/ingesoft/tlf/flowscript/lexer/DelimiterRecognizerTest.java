package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de delimitadores.
 * Delimitadores: ( ) { } [ ] , : .
 */
class DelimiterRecognizerTest {

    private DelimiterRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new DelimiterRecognizer();
    }

    @Test
    void testParentheses() {
        assertDelimiter("(", TokenType.LEFT_PAREN);
        assertDelimiter(")", TokenType.RIGHT_PAREN);
    }

    @Test
    void testBraces() {
        assertDelimiter("{", TokenType.LEFT_BRACE);
        assertDelimiter("}", TokenType.RIGHT_BRACE);
    }

    @Test
    void testBrackets() {
        assertDelimiter("[", TokenType.LEFT_BRACKET);
        assertDelimiter("]", TokenType.RIGHT_BRACKET);
    }

    @Test
    void testComma() {
        assertDelimiter(",", TokenType.COMMA);
    }

    @Test
    void testColon() {
        assertDelimiter(":", TokenType.COLON);
    }

    @Test
    void testDot() {
        assertDelimiter(".", TokenType.DOT);
    }

    @Test
    void testDelimiterFollowedByWhitespace() {
        Token token = recognizer.recognize("( ", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.LEFT_PAREN, token.getType());
        assertEquals("(", token.getLexeme());
    }

    @Test
    void testDelimiterFollowedByOtherChar() {
        Token token = recognizer.recognize("(a", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.LEFT_PAREN, token.getType());
        assertEquals("(", token.getLexeme());

        token = recognizer.recognize(",123", 0, 1, 1);
        assertEquals(TokenType.COMMA, token.getType());
    }

    @Test
    void testConsecutiveDelimiters() {
        Token token = recognizer.recognize("()", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.LEFT_PAREN, token.getType());
        assertEquals("(", token.getLexeme());

        token = recognizer.recognize("[]", 0, 1, 1);
        assertEquals(TokenType.LEFT_BRACKET, token.getType());

        token = recognizer.recognize("{}", 0, 1, 1);
        assertEquals(TokenType.LEFT_BRACE, token.getType());
    }

    @Test
    void testEmptyInput() {
        Token token = recognizer.recognize("", 0, 1, 1);
        assertNull(token);
    }

    @Test
    void testNonDelimiterChar() {
        Token token = recognizer.recognize("a", 0, 1, 1);
        assertNull(token, "Letra no es delimitador");

        token = recognizer.recognize("5", 0, 1, 1);
        assertNull(token, "Dígito no es delimitador");

        token = recognizer.recognize("+", 0, 1, 1);
        assertNull(token, "Operador no es delimitador");
    }

    @Test
    void testDelimiterAtPosition() {
        // Test reconocimiento en posiciones diferentes de 0
        String input = "funcion()";
        Token token = recognizer.recognize(input, 7, 1, 8);
        assertNotNull(token);
        assertEquals(TokenType.LEFT_PAREN, token.getType());
    }

    /**
     * Método auxiliar para verificar que un delimitador se reconoce correctamente.
     */
    private void assertDelimiter(String input, TokenType expectedType) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como delimitador", input));
        assertEquals(expectedType, token.getType());
        assertEquals(input, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
