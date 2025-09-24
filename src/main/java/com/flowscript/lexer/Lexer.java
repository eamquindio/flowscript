package com.flowscript.lexer;

import com.flowscript.lexer.afd.*;
import java.util.*;

/**
 * Main lexer class for FlowScript language.
 * Uses multiple AFD recognizers to tokenize input.
 */
public class Lexer {
    private final String input;
    private int currentPosition;
    private int currentLine;
    private int currentColumn;
    private final List<TokenRecognizer> recognizers;
    private final List<Token> tokens;
    private boolean includeComments;

    public Lexer(String input) {
        this(input, false);
    }

    public Lexer(String input, boolean includeComments) {
        this.input = input;
        this.currentPosition = 0;
        this.currentLine = 1;
        this.currentColumn = 1;
        this.tokens = new ArrayList<>();
        this.includeComments = includeComments;
        this.recognizers = initializeRecognizers();
    }

    private List<TokenRecognizer> initializeRecognizers() {
        List<TokenRecognizer> list = new ArrayList<>();
        list.add(new CommentRecognizer());
        list.add(new KeywordRecognizer());
        list.add(new OperatorRecognizer());
        list.add(new DecimalLiteralRecognizer());
        list.add(new IntegerLiteralRecognizer());
        list.add(new StringLiteralRecognizer());
        list.add(new IdentifierRecognizer());

        // Sort by priority (lower number = higher priority)
        list.sort(Comparator.comparingInt(TokenRecognizer::getPriority));
        return list;
    }

    public List<Token> tokenize() throws LexicalException {
        tokens.clear();
        currentPosition = 0;
        currentLine = 1;
        currentColumn = 1;

        while (currentPosition < input.length()) {
            // Skip whitespace
            if (skipWhitespace()) {
                continue;
            }

            // Try to recognize a token
            Token token = recognizeNextToken();

            if (token != null) {
                // Only add non-comment tokens unless includeComments is true
                if (includeComments ||
                    (token.getType() != TokenType.SINGLE_LINE_COMMENT &&
                     token.getType() != TokenType.MULTI_LINE_COMMENT)) {
                    tokens.add(token);
                }

                // Update position based on token length
                updatePosition(token);
            } else {
                // No recognizer could handle this character
                throw new LexicalException(
                    String.format("Unexpected character '%c' at line %d, column %d",
                                  input.charAt(currentPosition), currentLine, currentColumn)
                );
            }
        }

        // Add EOF token
        tokens.add(new Token(TokenType.EOF, "", currentLine, currentColumn, currentPosition));
        return tokens;
    }

    private boolean skipWhitespace() {
        boolean skipped = false;
        while (currentPosition < input.length() &&
               isWhitespace(input.charAt(currentPosition))) {
            char c = input.charAt(currentPosition);
            if (c == '\n') {
                currentLine++;
                currentColumn = 1;
            } else if (c == '\r') {
                // Handle \r\n as single newline
                if (currentPosition + 1 < input.length() &&
                    input.charAt(currentPosition + 1) == '\n') {
                    currentPosition++;
                }
                currentLine++;
                currentColumn = 1;
            } else {
                currentColumn++;
            }
            currentPosition++;
            skipped = true;
        }
        return skipped;
    }

    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private Token recognizeNextToken() {
        Token longestMatch = null;
        int longestLength = 0;

        // Try each recognizer and find the longest match
        for (TokenRecognizer recognizer : recognizers) {
            Token token = recognizer.recognize(input, currentPosition, currentLine, currentColumn);
            if (token != null) {
                int tokenLength = getTokenLength(token);
                if (tokenLength > longestLength) {
                    longestMatch = token;
                    longestLength = tokenLength;
                }
            }
        }

        return longestMatch;
    }

    private int getTokenLength(Token token) {
        // For most tokens, the length is the value length
        // For operators with default values, use that
        if (token.getValue() != null) {
            return token.getValue().length();
        }

        // Special cases for two-character operators
        switch (token.getType()) {
            case ARROW:
            case LESS_EQUAL:
            case GREATER_EQUAL:
            case EQUAL:
            case NOT_EQUAL:
                return 2;
            default:
                return 1;
        }
    }

    private void updatePosition(Token token) {
        String value = token.getValue();
        if (value == null) {
            value = token.getType().getDefaultValue();
        }

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '\n') {
                currentLine++;
                currentColumn = 1;
            } else if (c == '\r') {
                // Handle \r\n as single newline
                if (i + 1 < value.length() && value.charAt(i + 1) == '\n') {
                    i++;
                }
                currentLine++;
                currentColumn = 1;
            } else {
                currentColumn++;
            }
            currentPosition++;
        }
    }

    public List<Token> getTokens() {
        return Collections.unmodifiableList(tokens);
    }

    /**
     * Custom exception for lexical errors.
     */
    public static class LexicalException extends Exception {
        public LexicalException(String message) {
            super(message);
        }
    }
}