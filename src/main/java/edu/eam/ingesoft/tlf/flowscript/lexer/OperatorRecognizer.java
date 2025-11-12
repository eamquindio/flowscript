package edu.eam.ingesoft.tlf.flowscript.lexer;

import java.util.HashMap;
import java.util.Map;

/**
 * Reconocedor de operadores del lenguaje FlowScript.
 * Maneja operadores de uno y dos caracteres.
 *
 * Operadores de un carácter: + - * / % < > = !
 * Operadores de dos caracteres: == != <= >= ->
 *
 * El reconocedor intenta primero hacer match con operadores de dos caracteres
 * antes de probar con operadores de un carácter (maximal munch).
 */
public class OperatorRecognizer implements TokenRecognizer {

    // Mapa de operadores de dos caracteres
    private static final Map<String, TokenType> TWO_CHAR_OPERATORS = new HashMap<>();

    // Mapa de operadores de un carácter
    private static final Map<Character, TokenType> ONE_CHAR_OPERATORS = new HashMap<>();

    static {
        // Operadores de dos caracteres
        TWO_CHAR_OPERATORS.put("==", TokenType.EQUAL);
        TWO_CHAR_OPERATORS.put("!=", TokenType.NOT_EQUAL);
        TWO_CHAR_OPERATORS.put("<=", TokenType.LESS_EQUAL);
        TWO_CHAR_OPERATORS.put(">=", TokenType.GREATER_EQUAL);
        TWO_CHAR_OPERATORS.put("->", TokenType.ARROW);

        // Operadores de un carácter
        ONE_CHAR_OPERATORS.put('+', TokenType.PLUS);
        ONE_CHAR_OPERATORS.put('-', TokenType.MINUS);
        ONE_CHAR_OPERATORS.put('*', TokenType.MULTIPLY);
        ONE_CHAR_OPERATORS.put('/', TokenType.DIVIDE);
        ONE_CHAR_OPERATORS.put('%', TokenType.MODULO);
        ONE_CHAR_OPERATORS.put('<', TokenType.LESS_THAN);
        ONE_CHAR_OPERATORS.put('>', TokenType.GREATER_THAN);
        ONE_CHAR_OPERATORS.put('=', TokenType.ASSIGN);
        // Nota: '!' solo es válido como parte de '!='
    }

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        // Intentar reconocer operador de dos caracteres primero (maximal munch)
        if (start + 1 < input.length()) {
            String twoChar = input.substring(start, start + 2);
            TokenType type = TWO_CHAR_OPERATORS.get(twoChar);
            if (type != null) {
                return new Token(type, twoChar, line, column);
            }
        }

        // Intentar reconocer operador de un carácter
        char c = input.charAt(start);
        TokenType type = ONE_CHAR_OPERATORS.get(c);
        if (type != null) {
            return new Token(type, String.valueOf(c), line, column);
        }

        // '!' solo es válido como parte de '!='
        if (c == '!') {
            throw new LexerException("Unexpected character '!'", line, column, "!");
        }

        return null;
    }

    @Override
    public int getPriority() {
        return 25; // Prioridad alta
    }
}
