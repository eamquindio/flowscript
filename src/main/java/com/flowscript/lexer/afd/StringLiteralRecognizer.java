package com.flowscript.lexer.afd;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * AFD for recognizing string literals with escape sequences.
 * Supports: "Hello World", "Line 1\nLine 2", "Quote: \"Hello\"", etc.
 */
public class StringLiteralRecognizer implements TokenRecognizer {

    private enum State {
        START,
        OPEN_QUOTE,
        CHARACTER,
        ESCAPE,
        UNICODE_1,
        UNICODE_2,
        UNICODE_3,
        UNICODE_4,
        CLOSE_QUOTE,
        ACCEPT,
        REJECT
    }

    @Override
    public Token recognize(String input, int startPos, int line, int column) {
        if (startPos >= input.length()) {
            return null;
        }

        State currentState = State.START;
        StringBuilder lexeme = new StringBuilder();
        StringBuilder value = new StringBuilder(); // The actual string value (with escape sequences processed)
        int currentPos = startPos;
        String unicodeBuffer = "";

        while (currentPos < input.length() && currentState != State.ACCEPT && currentState != State.REJECT) {
            char c = input.charAt(currentPos);

            switch (currentState) {
                case START:
                    if (c == '"') {
                        currentState = State.OPEN_QUOTE;
                        lexeme.append(c);
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case OPEN_QUOTE:
                    if (c == '"') {
                        currentState = State.CLOSE_QUOTE;
                        lexeme.append(c);
                        currentPos++;
                    } else if (c == '\\') {
                        currentState = State.ESCAPE;
                        lexeme.append(c);
                        currentPos++;
                    } else if (c == '\n' || c == '\r') {
                        // Unclosed string
                        currentState = State.REJECT;
                    } else {
                        currentState = State.CHARACTER;
                        lexeme.append(c);
                        value.append(c);
                        currentPos++;
                    }
                    break;

                case CHARACTER:
                    if (c == '"') {
                        currentState = State.CLOSE_QUOTE;
                        lexeme.append(c);
                        currentPos++;
                    } else if (c == '\\') {
                        currentState = State.ESCAPE;
                        lexeme.append(c);
                        currentPos++;
                    } else if (c == '\n' || c == '\r') {
                        // Unclosed string
                        currentState = State.REJECT;
                    } else {
                        lexeme.append(c);
                        value.append(c);
                        currentPos++;
                    }
                    break;

                case ESCAPE:
                    lexeme.append(c);
                    currentPos++;
                    switch (c) {
                        case 'n':
                            value.append('\n');
                            currentState = State.CHARACTER;
                            break;
                        case 't':
                            value.append('\t');
                            currentState = State.CHARACTER;
                            break;
                        case 'r':
                            value.append('\r');
                            currentState = State.CHARACTER;
                            break;
                        case '"':
                            value.append('"');
                            currentState = State.CHARACTER;
                            break;
                        case '\\':
                            value.append('\\');
                            currentState = State.CHARACTER;
                            break;
                        case 'u':
                            unicodeBuffer = "";
                            currentState = State.UNICODE_1;
                            break;
                        default:
                            // Invalid escape sequence
                            currentState = State.REJECT;
                            break;
                    }
                    break;

                case UNICODE_1:
                    if (isHexDigit(c)) {
                        unicodeBuffer += c;
                        lexeme.append(c);
                        currentPos++;
                        currentState = State.UNICODE_2;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case UNICODE_2:
                    if (isHexDigit(c)) {
                        unicodeBuffer += c;
                        lexeme.append(c);
                        currentPos++;
                        currentState = State.UNICODE_3;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case UNICODE_3:
                    if (isHexDigit(c)) {
                        unicodeBuffer += c;
                        lexeme.append(c);
                        currentPos++;
                        currentState = State.UNICODE_4;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case UNICODE_4:
                    if (isHexDigit(c)) {
                        unicodeBuffer += c;
                        lexeme.append(c);
                        currentPos++;
                        // Convert unicode to character
                        int codePoint = Integer.parseInt(unicodeBuffer, 16);
                        value.append((char) codePoint);
                        currentState = State.CHARACTER;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case CLOSE_QUOTE:
                    currentState = State.ACCEPT;
                    break;
            }
        }

        // Check if we ended in the accepting state
        if (currentState == State.CLOSE_QUOTE || currentState == State.ACCEPT) {
            return new Token(TokenType.STRING_LITERAL, lexeme.toString(), line, column, startPos);
        }

        return null;
    }

    private boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') ||
               (c >= 'a' && c <= 'f') ||
               (c >= 'A' && c <= 'F');
    }

    @Override
    public int getPriority() {
        return 5; // Medium priority
    }
}