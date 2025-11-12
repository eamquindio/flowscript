package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de palabras reservadas (keywords).
 */
class KeywordRecognizerTest {

    private KeywordRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new KeywordRecognizer();
    }

    @Test
    void testRecognizeDeclarationKeywords() {
        assertKeyword("funcion", TokenType.FUNCION);
        assertKeyword("proceso", TokenType.PROCESO);
        assertKeyword("retornar", TokenType.RETORNAR);
    }

    @Test
    void testRecognizeControlKeywords() {
        assertKeyword("si", TokenType.SI);
        assertKeyword("sino_si", TokenType.SINO_SI);
        assertKeyword("sino", TokenType.SINO);
        assertKeyword("para", TokenType.PARA);
        assertKeyword("en", TokenType.EN);
    }

    @Test
    void testRecognizeTypeKeywords() {
        assertKeyword("entero", TokenType.ENTERO);
        assertKeyword("decimal", TokenType.DECIMAL);
        assertKeyword("booleano", TokenType.BOOLEANO);
        assertKeyword("texto", TokenType.TEXTO);
        assertKeyword("lista", TokenType.LISTA);
        assertKeyword("objeto", TokenType.OBJETO);
        assertKeyword("vacio", TokenType.VACIO);
    }

    @Test
    void testRecognizeLiteralKeywords() {
        assertKeyword("verdadero", TokenType.VERDADERO);
        assertKeyword("falso", TokenType.FALSO);
        assertKeyword("nulo", TokenType.NULO);
    }

    @Test
    void testRecognizeLogicalOperatorKeywords() {
        assertKeyword("y", TokenType.Y);
        assertKeyword("o", TokenType.O);
        assertKeyword("no", TokenType.NO);
    }

    @Test
    void testRecognizeProcessKeywords() {
        assertKeyword("inicio", TokenType.INICIO);
        assertKeyword("fin", TokenType.FIN);
        assertKeyword("tarea", TokenType.TAREA);
        assertKeyword("gateway", TokenType.GATEWAY);
        assertKeyword("paralelo", TokenType.PARALELO);
        assertKeyword("rama", TokenType.RAMA);
        assertKeyword("unir", TokenType.UNIR);
        assertKeyword("accion", TokenType.ACCION);
        assertKeyword("ir_a", TokenType.IR_A);
    }

    @Test
    void testRecognizeSpecialOperationKeywords() {
        assertKeyword("db", TokenType.DB);
        assertKeyword("http", TokenType.HTTP);
    }

    @Test
    void testKeywordNotPartOfIdentifier() {
        // "funcion2" no debe reconocerse como keyword
        Token token = recognizer.recognize("funcion2", 0, 1, 1);
        assertNull(token, "Keyword seguido de dígito no debe ser reconocido como keyword");

        // "funcion_test" no debe reconocerse como keyword
        token = recognizer.recognize("funcion_test", 0, 1, 1);
        assertNull(token, "Keyword seguido de guión bajo no debe ser reconocido como keyword");
    }

    @Test
    void testKeywordAtEndOfInput() {
        // Keyword al final de la entrada debe ser reconocido
        assertKeyword("si", TokenType.SI);
    }

    @Test
    void testKeywordFollowedByWhitespace() {
        // Keyword seguido de espacio debe ser reconocido
        Token token = recognizer.recognize("si ", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.SI, token.getType());
        assertEquals("si", token.getLexeme());
    }

    @Test
    void testKeywordFollowedByDelimiter() {
        // Keyword seguido de delimitador debe ser reconocido
        Token token = recognizer.recognize("si(", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.SI, token.getType());
        assertEquals("si", token.getLexeme());
    }

    @Test
    void testNonKeyword() {
        // Texto que no es keyword no debe ser reconocido
        Token token = recognizer.recognize("variable", 0, 1, 1);
        assertNull(token, "Identificador no debe ser reconocido como keyword");
    }

    @Test
    void testEmptyInput() {
        Token token = recognizer.recognize("", 0, 1, 1);
        assertNull(token);
    }

    @Test
    void testLongestMatchPriority() {
        // "sino_si" debe tener prioridad sobre "sino"
        Token token = recognizer.recognize("sino_si", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.SINO_SI, token.getType());
        assertEquals("sino_si", token.getLexeme());
    }

    /**
     * Método auxiliar para verificar que una keyword se reconoce correctamente.
     */
    private void assertKeyword(String input, TokenType expectedType) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("Keyword '%s' debe ser reconocido", input));
        assertEquals(expectedType, token.getType(),
                String.format("Keyword '%s' debe tener tipo %s", input, expectedType));
        assertEquals(input, token.getLexeme(),
                String.format("Lexema debe ser '%s'", input));
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
