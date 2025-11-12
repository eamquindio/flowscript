package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de operadores.
 * Operadores de un carácter: + - * / % < > = !
 * Operadores de dos caracteres: == != <= >= ->
 */
class OperatorRecognizerTest {

    private OperatorRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new OperatorRecognizer();
    }

    // Tests para operadores aritméticos de un carácter

    @Test
    void testArithmeticOperators() {
        assertOperator("+", TokenType.PLUS);
        assertOperator("-", TokenType.MINUS);
        assertOperator("*", TokenType.MULTIPLY);
        assertOperator("/", TokenType.DIVIDE);
        assertOperator("%", TokenType.MODULO);
    }

    // Tests para operadores relacionales de un carácter

    @Test
    void testRelationalOperators() {
        assertOperator("<", TokenType.LESS_THAN);
        assertOperator(">", TokenType.GREATER_THAN);
    }

    // Tests para operador de asignación

    @Test
    void testAssignmentOperator() {
        assertOperator("=", TokenType.ASSIGN);
    }

    // Tests para operadores de dos caracteres

    @Test
    void testEqualityOperators() {
        assertOperator("==", TokenType.EQUAL);
        assertOperator("!=", TokenType.NOT_EQUAL);
    }

    @Test
    void testCompoundRelationalOperators() {
        assertOperator("<=", TokenType.LESS_EQUAL);
        assertOperator(">=", TokenType.GREATER_EQUAL);
    }

    @Test
    void testArrowOperator() {
        assertOperator("->", TokenType.ARROW);
    }

    // Tests para maximal munch (prioridad de operadores de dos caracteres)

    @Test
    void testMaximalMunchEqual() {
        // "==" debe reconocerse como EQUAL, no como dos ASSIGN
        Token token = recognizer.recognize("==", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.EQUAL, token.getType());
        assertEquals("==", token.getLexeme());
    }

    @Test
    void testMaximalMunchNotEqual() {
        Token token = recognizer.recognize("!=", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.NOT_EQUAL, token.getType());
        assertEquals("!=", token.getLexeme());
    }

    @Test
    void testMaximalMunchLessEqual() {
        Token token = recognizer.recognize("<=", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.LESS_EQUAL, token.getType());
        assertEquals("<=", token.getLexeme());
    }

    @Test
    void testMaximalMunchGreaterEqual() {
        Token token = recognizer.recognize(">=", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.GREATER_EQUAL, token.getType());
        assertEquals(">=", token.getLexeme());
    }

    @Test
    void testMaximalMunchArrow() {
        Token token = recognizer.recognize("->", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.ARROW, token.getType());
        assertEquals("->", token.getLexeme());
    }

    // Tests para casos con operadores seguidos de otros caracteres

    @Test
    void testOperatorFollowedByWhitespace() {
        Token token = recognizer.recognize("+ ", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.PLUS, token.getType());
        assertEquals("+", token.getLexeme());
    }

    @Test
    void testOperatorFollowedByDigit() {
        Token token = recognizer.recognize("+123", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.PLUS, token.getType());
        assertEquals("+", token.getLexeme());
    }

    @Test
    void testOperatorFollowedByIdentifier() {
        Token token = recognizer.recognize("=variable", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLexeme());
    }

    // Tests para casos especiales

    @Test
    void testSingleExclamationMark() {
        // '!' solo no es válido (solo como parte de '!=')
        assertThrows(LexerException.class, () -> {
            recognizer.recognize("!", 0, 1, 1);
        });
    }

    @Test
    void testExclamationMarkFollowedByNonEqual() {
        // '!a' debe lanzar excepción
        assertThrows(LexerException.class, () -> {
            recognizer.recognize("!a", 0, 1, 1);
        });
    }

    @Test
    void testEmptyInput() {
        Token token = recognizer.recognize("", 0, 1, 1);
        assertNull(token);
    }

    @Test
    void testNonOperatorChar() {
        Token token = recognizer.recognize("a", 0, 1, 1);
        assertNull(token, "Letra no es operador");

        token = recognizer.recognize("5", 0, 1, 1);
        assertNull(token, "Dígito no es operador");

        token = recognizer.recognize("(", 0, 1, 1);
        assertNull(token, "Delimitador no es operador");
    }

    @Test
    void testOperatorAtPosition() {
        // Test reconocimiento en posiciones diferentes de 0
        String input = "a + b";
        Token token = recognizer.recognize(input, 2, 1, 3);
        assertNotNull(token);
        assertEquals(TokenType.PLUS, token.getType());
        assertEquals("+", token.getLexeme());
    }

    @Test
    void testConsecutiveOperators() {
        // Debe reconocer el primero correctamente
        Token token = recognizer.recognize("+-", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.PLUS, token.getType());
        assertEquals("+", token.getLexeme());
    }

    @Test
    void testAssignVsEqual() {
        // Un solo '=' debe ser ASSIGN
        Token token = recognizer.recognize("=", 0, 1, 1);
        assertEquals(TokenType.ASSIGN, token.getType());

        // '==' debe ser EQUAL
        token = recognizer.recognize("==", 0, 1, 1);
        assertEquals(TokenType.EQUAL, token.getType());
    }

    @Test
    void testMinusVsArrow() {
        // Un solo '-' debe ser MINUS
        Token token = recognizer.recognize("-", 0, 1, 1);
        assertEquals(TokenType.MINUS, token.getType());

        // '->' debe ser ARROW
        token = recognizer.recognize("->", 0, 1, 1);
        assertEquals(TokenType.ARROW, token.getType());
    }

    /**
     * Método auxiliar para verificar que un operador se reconoce correctamente.
     */
    private void assertOperator(String input, TokenType expectedType) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como operador", input));
        assertEquals(expectedType, token.getType());
        assertEquals(input, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
