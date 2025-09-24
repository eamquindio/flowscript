package com.flowscript.sintactic;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import java.util.List;

/**
 * Helper utilities for parser tests.
 */
public class TestHelper {

    /**
     * Creates tokens from source code string.
     */
    public static List<Token> tokenize(String sourceCode) {
        Lexer lexer = new Lexer(sourceCode);
        return lexer.tokenize();
    }

    /**
     * Creates a parser with tokens from source code.
     */
    public static <T extends Parser> T createParser(Class<T> parserClass, String sourceCode) {
        try {
            List<Token> tokens = tokenize(sourceCode);
            return parserClass.getDeclaredConstructor(List.class).newInstance(tokens);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create parser", e);
        }
    }

    /**
     * Asserts that parsing throws an exception.
     */
    public static void assertParseError(Runnable parseAction) {
        try {
            parseAction.run();
            throw new AssertionError("Expected ParseException but none was thrown");
        } catch (Exception e) {
            if (!(e instanceof Parser.ParseException) &&
                !(e.getCause() instanceof Parser.ParseException)) {
                throw new AssertionError("Expected ParseException but got: " + e.getClass().getSimpleName());
            }
        }
    }
}