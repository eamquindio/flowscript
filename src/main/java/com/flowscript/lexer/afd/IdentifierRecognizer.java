package com.flowscript.lexer.afd;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * AFD for recognizing identifiers.
 * Pattern: [a-zA-Z_][a-zA-Z0-9_]*
 */
public class IdentifierRecognizer implements TokenRecognizer {

    private enum State {
        START,
        IDENTIFIER,
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

        while (currentPos < input.length() && currentState != State.ACCEPT && currentState != State.REJECT) {
            char c = input.charAt(currentPos);

            switch (currentState) {
                case START:
                    if (isIdentifierStart(c)) {
                        currentState = State.IDENTIFIER;
                        lexeme.append(c);
                        currentPos++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case IDENTIFIER:
                    if (isIdentifierPart(c)) {
                        lexeme.append(c);
                        currentPos++;
                    } else {
                        currentState = State.ACCEPT;
                    }
                    break;
            }
        }

        // Check if we ended in an accepting state
        if (currentState == State.IDENTIFIER || currentState == State.ACCEPT) {
            String identifier = lexeme.toString();
            return new Token(TokenType.IDENTIFIER, identifier, line, column, startPos);
        }

        return null;
    }

    private boolean isIdentifierStart(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isIdentifierPart(char c) {
        return isIdentifierStart(c) || (c >= '0' && c <= '9');
    }

    @Override
    public int getPriority() {
        return 10; // Low priority - check after keywords
    }
}