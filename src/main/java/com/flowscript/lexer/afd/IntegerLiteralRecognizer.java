package com.flowscript.lexer.afd;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * AFD for recognizing integer literals.
 * Supports formats: 42, 1_000_000, -17
 */
public class IntegerLiteralRecognizer implements TokenRecognizer {

    private enum State {
        START,
        MINUS,
        FIRST_DIGIT,
        DIGIT,
        UNDERSCORE,
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
        int currentPos = startPos;
        boolean hasDigits = false;

        while (currentPos < input.length() && currentState != State.ACCEPT && currentState != State.REJECT) {
            char c = input.charAt(currentPos);

            switch (currentState) {
                case START:
                    if (c == '-') {
                        currentState = State.MINUS;
                        lexeme.append(c);
                        currentPos++;
                    } else if (isDigit(c)) {
                        currentState = State.FIRST_DIGIT;
                        lexeme.append(c);
                        hasDigits = true;
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case MINUS:
                    if (isDigit(c)) {
                        currentState = State.FIRST_DIGIT;
                        lexeme.append(c);
                        hasDigits = true;
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case FIRST_DIGIT:
                case DIGIT:
                    if (isDigit(c)) {
                        currentState = State.DIGIT;
                        lexeme.append(c);
                        currentPos++;
                    } else if (c == '_') {
                        // Check if next character is a digit
                        if (currentPos + 1 < input.length() && isDigit(input.charAt(currentPos + 1))) {
                            currentState = State.UNDERSCORE;
                            lexeme.append(c);
                            currentPos++;
                        } else {
                            currentState = State.ACCEPT;
                        }
                    } else if (c == '.' || c == 'e' || c == 'E') {
                        // This might be a decimal, stop here
                        currentState = State.ACCEPT;
                    } else {
                        currentState = State.ACCEPT;
                    }
                    break;

                case UNDERSCORE:
                    if (isDigit(c)) {
                        currentState = State.DIGIT;
                        lexeme.append(c);
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;
            }
        }

        // Check if we ended in an accepting state
        if ((currentState == State.DIGIT || currentState == State.FIRST_DIGIT ||
             currentState == State.ACCEPT) && hasDigits) {
            String value = lexeme.toString();
            // Remove underscores for the actual value
            String cleanValue = value.replace("_", "");

            // Validate it's a valid integer
            try {
                Long.parseLong(cleanValue);
                return new Token(TokenType.INTEGER_LITERAL, value, line, column, startPos);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    @Override
    public int getPriority() {
        return 4; // Lower priority than keywords and operators
    }
}