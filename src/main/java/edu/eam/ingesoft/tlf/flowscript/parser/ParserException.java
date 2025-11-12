package edu.eam.ingesoft.tlf.flowscript.parser;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;

/**
 * Excepción lanzada cuando ocurre un error durante el análisis sintáctico.
 */
public class ParserException extends RuntimeException {
    private final Token token;
    private final String expected;

    public ParserException(String message, Token token) {
        super(formatMessage(message, token));
        this.token = token;
        this.expected = null;
    }

    public ParserException(String message, Token token, String expected) {
        super(formatMessage(message + ". Expected: " + expected, token));
        this.token = token;
        this.expected = expected;
    }

    private static String formatMessage(String message, Token token) {
        return String.format("%s at line %d, column %d. Got token: %s '%s'",
                message,
                token.getLine(),
                token.getColumn(),
                token.getType(),
                token.getLexeme());
    }

    public Token getToken() {
        return token;
    }

    public String getExpected() {
        return expected;
    }
}
