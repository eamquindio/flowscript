package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el reconocedor de comentarios de bloque/multilínea.
 * Patrón: /* .* *\/
 */
class BlockCommentRecognizerTest {

    private BlockCommentRecognizer recognizer;

    @BeforeEach
    void setUp() {
        recognizer = new BlockCommentRecognizer();
    }

    @Test
    void testEmptyComment() {
        assertBlockComment("/**/", "/**/");
    }

    @Test
    void testSimpleComment() {
        assertBlockComment("/* comentario */", "/* comentario */");
    }

    @Test
    void testCommentWithText() {
        assertBlockComment("/* esto es un comentario de bloque */",
                "/* esto es un comentario de bloque */");
    }

    @Test
    void testMultilineComment() {
        String comment = "/* línea 1\nlínea 2\nlínea 3 */";
        assertBlockComment(comment, comment);
    }

    @Test
    void testCommentWithSpecialCharacters() {
        assertBlockComment("/* !@#$%^&*() */", "/* !@#$%^&*() */");
        assertBlockComment("/* código: a = b + c */", "/* código: a = b + c */");
    }

    @Test
    void testCommentWithNumbers() {
        assertBlockComment("/* 123 456 */", "/* 123 456 */");
    }

    @Test
    void testCommentWithAsterisks() {
        assertBlockComment("/* ** múltiples ** asteriscos ** */",
                "/* ** múltiples ** asteriscos ** */");
    }

    @Test
    void testCommentWithMultipleCloseAttempts() {
        // Debe manejar asteriscos que no sean seguidos de /
        assertBlockComment("/* esto * no * cierra * aquí */",
                "/* esto * no * cierra * aquí */");
    }

    @Test
    void testCommentWithSlashes() {
        assertBlockComment("/* barras / en / el / comentario */",
                "/* barras / en / el / comentario */");
    }

    @Test
    void testCommentWithKeywords() {
        assertBlockComment("/* funcion proceso retornar */",
                "/* funcion proceso retornar */");
    }

    @Test
    void testCommentWithCode() {
        assertBlockComment("/* \nfuncion suma(a: entero, b: entero) -> entero {\n    retornar a + b\n}\n */",
                "/* \nfuncion suma(a: entero, b: entero) -> entero {\n    retornar a + b\n}\n */");
    }

    @Test
    void testUnterminatedComment() {
        // Comentario sin cierre debe lanzar excepción
        assertThrows(LexerException.class, () -> {
            recognizer.recognize("/* sin cierre", 0, 1, 1);
        });
    }

    @Test
    void testUnterminatedCommentAtEndOfFile() {
        assertThrows(LexerException.class, () -> {
            recognizer.recognize("/* no termina", 0, 1, 1);
        });
    }

    @Test
    void testUnterminatedCommentWithAsterisk() {
        assertThrows(LexerException.class, () -> {
            recognizer.recognize("/* termina con asterisco *", 0, 1, 1);
        });
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

        token = recognizer.recognize("#", 0, 1, 1);
        assertNull(token, "# no es comentario de bloque");
    }

    @Test
    void testSingleSlash() {
        Token token = recognizer.recognize("/", 0, 1, 1);
        assertNull(token, "Una sola barra no es comentario de bloque");
    }

    @Test
    void testSlashWithoutAsterisk() {
        Token token = recognizer.recognize("/a", 0, 1, 1);
        assertNull(token, "Barra sin asterisco no es comentario de bloque");
    }

    @Test
    void testCommentAtPosition() {
        // Test reconocimiento en posiciones diferentes de 0
        String input = "codigo /* comentario */ mas_codigo";
        Token token = recognizer.recognize(input, 7, 1, 8);
        assertNotNull(token);
        assertEquals("/* comentario */", token.getLexeme());
    }

    @Test
    void testVeryLongComment() {
        String longContent = "a".repeat(1000);
        String longComment = "/* " + longContent + " */";
        assertBlockComment(longComment, longComment);
    }

    @Test
    void testCommentWithTabs() {
        assertBlockComment("/*\tcomentario\tcon\ttabs\t*/",
                "/*\tcomentario\tcon\ttabs\t*/");
    }

    @Test
    void testCommentWithUnicode() {
        assertBlockComment("/* café señor año */", "/* café señor año */");
        assertBlockComment("/* 你好 こんにちは */", "/* 你好 こんにちは */");
    }

    @Test
    void testNestedCommentSyntax() {
        // Nota: en la especificación simplificada no se soportan comentarios anidados,
        // el primer */ cierra el comentario
        Token token = recognizer.recognize("/* externo /* interno */ */", 0, 1, 1);
        assertNotNull(token);
        // Debe cerrar en el primer */
        assertEquals("/* externo /* interno */", token.getLexeme());
    }

    @Test
    void testCommentFollowedByOtherTokens() {
        Token token = recognizer.recognize("/* comment */ codigo", 0, 1, 1);
        assertNotNull(token);
        assertEquals("/* comment */", token.getLexeme());
    }

    /**
     * Método auxiliar para verificar que un comentario de bloque se reconoce correctamente.
     */
    private void assertBlockComment(String input, String expectedLexeme) {
        Token token = recognizer.recognize(input, 0, 1, 1);
        assertNotNull(token, String.format("'%s' debe ser reconocido como BLOCK_COMMENT", input));
        assertEquals(TokenType.BLOCK_COMMENT, token.getType());
        assertEquals(expectedLexeme, token.getLexeme());
        assertEquals(1, token.getLine());
        assertEquals(1, token.getColumn());
    }
}
