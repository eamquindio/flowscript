package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de identificadores.
 * Patrón: [a-zA-Z_][a-zA-Z0-9_]*
 */
class IdentifierRecognizerTest {

    private IdentifierRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new IdentifierRecognizer();
    }

    @Test
    void testSimpleIdentifier() {
        assertIdentifier("variable", "variable");
        assertIdentifier("x", "x");
        assertIdentifier("contador", "contador");
    }

    @Test
    void testIdentifierStartingWithUnderscore() {
        assertIdentifier("_privado", "_privado");
        assertIdentifier("_", "_");
        assertIdentifier("_var123", "_var123");
    }

    @Test
    void testIdentifierWithDigits() {
        assertIdentifier("variable123", "variable123");
        assertIdentifier("var1", "var1");
        assertIdentifier("a1b2c3", "a1b2c3");
    }

    @Test
    void testIdentifierWithUnderscores() {
        assertIdentifier("mi_variable", "mi_variable");
        assertIdentifier("nombre_completo", "nombre_completo");
        assertIdentifier("_variable_privada_", "_variable_privada_");
    }

    @Test
    void testIdentifierWithMixedCase() {
        assertIdentifier("MiVariable", "MiVariable");
        assertIdentifier("camelCase", "camelCase");
        assertIdentifier("PascalCase", "PascalCase");
        assertIdentifier("CONSTANTE", "CONSTANTE");
    }

    @Test
    void testIdentifierFollowedByWhitespace() {
        Token token = recognizer.recognize("variable ", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("variable", token.getLexeme());
    }

    @Test
    void testIdentifierFollowedByDelimiter() {
        Token token = recognizer.recognize("variable(", 0, 1, 1);
        assertNotNull(token);
        assertEquals("variable", token.getLexeme());

        token = recognizer.recognize("lista[", 0, 1, 1);
        assertEquals("lista", token.getLexeme());

        token = recognizer.recognize("objeto.", 0, 1, 1);
        assertEquals("objeto", token.getLexeme());
    }

    @Test
    void testIdentifierFollowedByOperator() {
        Token token = recognizer.recognize("a+", 0, 1, 1);
        assertNotNull(token);
        assertEquals("a", token.getLexeme());

        token = recognizer.recognize("count=", 0, 1, 1);
        assertEquals("count", token.getLexeme());
    }

    @Test
    void testCannotStartWithDigit() {
        Token token = recognizer.recognize("123variable", 0, 1, 1);
        assertNull(token, "Identificador no puede comenzar con dígito");
    }

    @Test
    void testEmptyInput() {
        Token token = recognizer.recognize("", 0, 1, 1);
        assertNull(token);
    }

    @Test
    void testNonIdentifierChar() {
        Token token = recognizer.recognize("@variable", 0, 1, 1);
        assertNull(token, "@ no es un carácter válido para identificador");

        token = recognizer.recognize("$variable", 0, 1, 1);
        assertNull(token, "$ no es un carácter válido para identificador");
    }

    @Test
    void testIdentifierAtPosition() {
        // Test reconocimiento en posiciones diferentes de 0
        String input = "funcion nombre() {}";
        Token token = recognizer.recognize(input, 8, 1, 9);
        assertNotNull(token);
        assertEquals("nombre", token.getLexeme());
    }

    @Test
    void testVeryLongIdentifier() {
        String longId = "variable_con_nombre_muy_largo_que_describe_algo_especifico_123";
        assertIdentifier(longId, longId);
    }

    /**
     * Método auxiliar para verificar que un identificador se reconoce correctamente.
     */
    private void assertIdentifier(String input, String expectedLexeme) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como identificador", input));
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals(expectedLexeme, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
