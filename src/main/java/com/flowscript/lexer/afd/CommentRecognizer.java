package com.flowscript.lexer.afd;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * AFD for recognizing single-line (#) and multi-line comments.
 * Single-line comments start with #
 * Multi-line comments are enclosed between slash-star and star-slash
 */
public class CommentRecognizer implements TokenRecognizer {

    private enum State {
        START,
        SINGLE_LINE_COMMENT,
        MULTI_LINE_START,
        MULTI_LINE_CONTENT,
        MULTI_LINE_STAR,
        ACCEPT,
        REJECT
    }

    @Override
    public Token recognize(String input, int startPos, int line, int column) {
        if (startPos >= input.length()) {
            return null;
        }

        char firstChar = input.charAt(startPos);

        // Check for single-line comment
        if (firstChar == '#') {
            return recognizeSingleLineComment(input, startPos, line, column);
        }

        // Check for multi-line comment
        if (firstChar == '/' && startPos + 1 < input.length() && input.charAt(startPos + 1) == '*') {
            return recognizeMultiLineComment(input, startPos, line, column);
        }

        return null;
    }

    private Token recognizeSingleLineComment(String input, int startPos, int line, int column) {
        State currentState = State.START;
        StringBuilder lexeme = new StringBuilder();
        int currentPos = startPos;

        while (currentPos < input.length() && currentState != State.ACCEPT) {
            char c = input.charAt(currentPos);

            switch (currentState) {
                case START:
                    if (c == '#') {
                        currentState = State.SINGLE_LINE_COMMENT;
                        lexeme.append(c);
                        currentPos++;
                    } else {
                        return null;
                    }
                    break;

                case SINGLE_LINE_COMMENT:
                    if (c == '\n' || c == '\r') {
                        currentState = State.ACCEPT;
                    } else {
                        lexeme.append(c);
                        currentPos++;
                    }
                    break;
            }
        }

        // If we reached end of input while in comment, that's also valid
        if (currentState == State.SINGLE_LINE_COMMENT || currentState == State.ACCEPT) {
            return new Token(TokenType.SINGLE_LINE_COMMENT, lexeme.toString(), line, column, startPos);
        }

        return null;
    }

    private Token recognizeMultiLineComment(String input, int startPos, int line, int column) {
        State currentState = State.START;
        StringBuilder lexeme = new StringBuilder();
        int currentPos = startPos;
        int currentLine = line;
        int currentColumn = column;

        while (currentPos < input.length() && currentState != State.ACCEPT && currentState != State.REJECT) {
            char c = input.charAt(currentPos);

            switch (currentState) {
                case START:
                    if (c == '/') {
                        currentState = State.MULTI_LINE_START;
                        lexeme.append(c);
                        currentPos++;
                        currentColumn++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case MULTI_LINE_START:
                    if (c == '*') {
                        currentState = State.MULTI_LINE_CONTENT;
                        lexeme.append(c);
                        currentPos++;
                        currentColumn++;
                    } else {
                        currentState = State.REJECT;
                    }
                    break;

                case MULTI_LINE_CONTENT:
                    if (c == '*') {
                        currentState = State.MULTI_LINE_STAR;
                        lexeme.append(c);
                        currentPos++;
                        currentColumn++;
                    } else if (c == '\n') {
                        lexeme.append(c);
                        currentPos++;
                        currentLine++;
                        currentColumn = 1;
                    } else {
                        lexeme.append(c);
                        currentPos++;
                        currentColumn++;
                    }
                    break;

                case MULTI_LINE_STAR:
                    if (c == '/') {
                        lexeme.append(c);
                        currentPos++;
                        currentState = State.ACCEPT;
                    } else if (c == '*') {
                        // Stay in MULTI_LINE_STAR state
                        lexeme.append(c);
                        currentPos++;
                        currentColumn++;
                    } else if (c == '\n') {
                        currentState = State.MULTI_LINE_CONTENT;
                        lexeme.append(c);
                        currentPos++;
                        currentLine++;
                        currentColumn = 1;
                    } else {
                        currentState = State.MULTI_LINE_CONTENT;
                        lexeme.append(c);
                        currentPos++;
                        currentColumn++;
                    }
                    break;
            }
        }

        if (currentState == State.ACCEPT) {
            return new Token(TokenType.MULTI_LINE_COMMENT, lexeme.toString(), line, column, startPos);
        }

        return null;
    }

    @Override
    public int getPriority() {
        return 0; // Highest priority - check comments before anything else
    }
}