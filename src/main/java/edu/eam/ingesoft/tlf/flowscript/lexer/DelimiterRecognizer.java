package edu.eam.ingesoft.tlf.flowscript.lexer;

import java.util.HashMap;
import java.util.Map;

/**
 * Reconocedor de delimitadores del lenguaje FlowScript.
 * Delimitadores: ( ) { } [ ] , : .
 *
 * Cada delimitador es un símbolo de un solo carácter que se reconoce directamente.
 */
public class DelimiterRecognizer implements TokenRecognizer {

    private static final Map<Character, TokenType> DELIMITERS = new HashMap<>();

    static {
        DELIMITERS.put('(', TokenType.LEFT_PAREN);
        DELIMITERS.put(')', TokenType.RIGHT_PAREN);
        DELIMITERS.put('{', TokenType.LEFT_BRACE);
        DELIMITERS.put('}', TokenType.RIGHT_BRACE);
        DELIMITERS.put('[', TokenType.LEFT_BRACKET);
        DELIMITERS.put(']', TokenType.RIGHT_BRACKET);
        DELIMITERS.put(',', TokenType.COMMA);
        DELIMITERS.put(':', TokenType.COLON);
        DELIMITERS.put('.', TokenType.DOT);
    }

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        char c = input.charAt(start);
        TokenType type = DELIMITERS.get(c);

        if (type != null) {
            return new Token(type, String.valueOf(c), line, column);
        }

        return null;
    }

    @Override
    public int getPriority() {
        return 40; // Prioridad media
    }
}
