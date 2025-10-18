package com.flowscript.lexer.afd;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * AFD for recognizing decimal (floating-point) literals.
 * Supports formats: 3.14, 1.23e-5, -0.5, 1e10
 */
public class DecimalLiteralRecognizer implements TokenRecognizer {

    private enum State {
        START,
        MINUS,
        INTEGER_PART,
        DOT,
        FRACTION_PART,
        EXPONENT_MARK,
        EXPONENT_SIGN,
        EXPONENT_DIGIT,
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
        boolean hasIntegerPart = false;
        boolean hasFractionPart = false;
        boolean hasExponentPart = false;

        while (currentPos < input.length() && currentState != State.ACCEPT && currentState != State.REJECT) {
            char c = input.charAt(currentPos);

            switch (currentState) {
                case START:
                    if (c == '-') {
                        currentState = State.MINUS;
                        lexeme.append(c);
                        currentPos++;
                    } else if (isDigit(c)) {
                        currentState = State.INTEGER_PART;
                        lexeme.append(c);
                        hasIntegerPart = true;
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case MINUS:
                    if (isDigit(c)) {
                        currentState = State.INTEGER_PART;
                        lexeme.append(c);
                        hasIntegerPart = true;
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case INTEGER_PART:
                    if (isDigit(c)) {
                        lexeme.append(c);
                        currentPos++;
                    } else if (c == '.') {
                        currentState = State.DOT;
                        lexeme.append(c);
                        currentPos++;
                    } else if (c == 'e' || c == 'E') {
                        currentState = State.EXPONENT_MARK;
                        lexeme.append(c);
                        currentPos++;
                    } else {
                        currentState = State.ACCEPT;
                    }
                    break;

                case DOT:
                    if (isDigit(c)) {
                        currentState = State.FRACTION_PART;
                        lexeme.append(c);
                        hasFractionPart = true;
                        currentPos++;
                    } else {
                        currentState = State.REJECT; // Decimal point must be followed by digits
                    }
                    break;

                case FRACTION_PART:
                    if (isDigit(c)) {
                        lexeme.append(c);
                        currentPos++;
                    } else if (c == 'e' || c == 'E') {
                        currentState = State.EXPONENT_MARK;
                        lexeme.append(c);
                        currentPos++;
                    } else {
                        currentState = State.ACCEPT;
                    }
                    break;

                case EXPONENT_MARK:
                    if (c == '+' || c == '-') {
                        currentState = State.EXPONENT_SIGN;
                        lexeme.append(c);
                        currentPos++;
                    } else if (isDigit(c)) {
                        currentState = State.EXPONENT_DIGIT;
                        lexeme.append(c);
                        hasExponentPart = true;
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case EXPONENT_SIGN:
                    if (isDigit(c)) {
                        currentState = State.EXPONENT_DIGIT;
                        lexeme.append(c);
                        hasExponentPart = true;
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case EXPONENT_DIGIT:
                    if (isDigit(c)) {
                        lexeme.append(c);
                        currentPos++;
                    } else {
                        currentState = State.ACCEPT;
                    }
                    break;
            }
        }

        // Check if we have a valid decimal literal
        if ((currentState == State.FRACTION_PART || currentState == State.EXPONENT_DIGIT ||
             currentState == State.ACCEPT) &&
            (hasFractionPart || hasExponentPart)) {

            String value = lexeme.toString();

            // Validate it's a valid decimal
            try {
                Double.parseDouble(value);
                return new Token(TokenType.DECIMAL_LITERAL, value, line, column, startPos);
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
        return 3; // Higher priority than integer (to catch decimals first)
    }
}