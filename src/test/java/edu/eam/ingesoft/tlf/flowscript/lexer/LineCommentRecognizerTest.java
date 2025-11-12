package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de comentarios de línea.
 * Patrón: # [^\n]*
 */
class LineCommentRecognizerTest {

    private LineCommentRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new LineCommentRecognizer();
    }

    @Test
    void testEmptyComment() {
        assertLineComment("#", "#");
    }

    @Test
    void testSimpleComment() {
        assertLineComment("# esto es un comentario", "# esto es un comentario");
    }

    @Test
    void testCommentWithText() {
        assertLineComment("# comentario de una línea", "# comentario de una línea");
    }

    @Test
    void testCommentFollowedByNewline() {
        Token token = recognizer.recognize("# comentario\n", 0, 1, 1);
        assertNotNull(token);
        assertEquals(TokenType.LINE_COMMENT, token.getType());
        assertEquals("# comentario", token.getLexeme());
        // El '\n' no debe ser parte del lexema
    }

    @Test
    void testCommentWithSpecialCharacters() {
        assertLineComment("# !@#$%^&*()", "# !@#$%^&*()");
        assertLineComment("# código: a = b + c", "# código: a = b + c");
    }

    @Test
    void testCommentWithNumbers() {
        assertLineComment("# 123 456", "# 123 456");
        assertLineComment("# TODO: item #42", "# TODO: item #42");
    }

    @Test
    void testCommentAtEndOfFile() {
        // Comentario sin newline al final (EOF)
        assertLineComment("# último comentario", "# último comentario");
    }

    @Test
    void testMultipleHashSymbols() {
        assertLineComment("## comentario doble", "## comentario doble");
        assertLineComment("### comentario triple", "### comentario triple");
    }

    @Test
    void testCommentWithOperators() {
        assertLineComment("# a + b = c", "# a + b = c");
        assertLineComment("# x -> y", "# x -> y");
    }

    @Test
    void testCommentWithKeywords() {
        assertLineComment("# funcion proceso retornar", "# funcion proceso retornar");
    }

    @Test
    void testCommentWithQuotes() {
        assertLineComment("# \"texto entre comillas\"", "# \"texto entre comillas\"");
    }

    @Test
    void testEmptyInput() {
        Token token = recognizer.recognize("", 0, 1, 1);
        assertNull(token);
    }

    @Test
    void testNonCommentChar() {
        Token token = recognizer.recognize("a", 0, 1, 1);
        assertNull(token, "Letra no es comentario");

        token = recognizer.recognize("5", 0, 1, 1);
        assertNull(token, "Dígito no es comentario");

        token = recognizer.recognize("/", 0, 1, 1);
        assertNull(token, "Barra no es comentario de línea");
    }

    @Test
    void testCommentAtPosition() {
        // Test reconocimiento en posiciones diferentes de 0
        String input = "codigo # comentario";
        Token token = recognizer.recognize(input, 7, 1, 8);
        assertNotNull(token);
        assertEquals("# comentario", token.getLexeme());
    }

    @Test
    void testVeryLongComment() {
        String longComment = "# " + "a".repeat(1000);
        assertLineComment(longComment, longComment);
    }

    @Test
    void testCommentWithTabs() {
        assertLineComment("#\tcomentario\tcon\ttabs", "#\tcomentario\tcon\ttabs");
    }

    @Test
    void testCommentWithUnicode() {
        assertLineComment("# café señor año", "# café señor año");
        assertLineComment("# 你好 こんにちは", "# 你好 こんにちは");
    }

    /**
     * Método auxiliar para verificar que un comentario de línea se reconoce correctamente.
     */
    private void assertLineComment(String input, String expectedLexeme) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como LINE_COMMENT", input));
        assertEquals(TokenType.LINE_COMMENT, token.getType());
        assertEquals(expectedLexeme, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
