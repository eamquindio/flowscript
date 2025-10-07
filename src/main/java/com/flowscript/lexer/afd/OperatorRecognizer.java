package com.flowscript.lexer.afd;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * Recognizer for operators and delimiters using finite automaton approach.
 */
public class OperatorRecognizer implements TokenRecognizer {

    @Override
    public Token recognize(String input, int startPos, int line, int column) {
        if (startPos >= input.length()) {
            return null;
        }

        char current = input.charAt(startPos);
        char next = (startPos + 1 < input.length()) ? input.charAt(startPos + 1) : '\0';

        // Two-character operators
        if (current == '-' && next == '>') {
            return new Token(TokenType.ARROW, "->", line, column, startPos);
        }
        if (current == '<' && next == '=') {
            return new Token(TokenType.LESS_EQUAL, "<=", line, column, startPos);
        }
        if (current == '>' && next == '=') {
            return new Token(TokenType.GREATER_EQUAL, ">=", line, column, startPos);
        }
        if (current == '=' && next == '=') {
            return new Token(TokenType.EQUAL, "==", line, column, startPos);
        }
        if (current == '!' && next == '=') {
            return new Token(TokenType.NOT_EQUAL, "!=", line, column, startPos);
        }

        // Single-character operators and delimiters
        switch (current) {
            case '+':
                return new Token(TokenType.PLUS, "+", line, column, startPos);
            case '-':
                return new Token(TokenType.MINUS, "-", line, column, startPos);
            case '*':
                return new Token(TokenType.MULTIPLY, "*", line, column, startPos);
            case '/':
                // Check if it's a comment start
                if (next == '*') {
                    return null; // Let CommentRecognizer handle it
                }
                return new Token(TokenType.DIVIDE, "/", line, column, startPos);
            case '%':
                return new Token(TokenType.MODULO, "%", line, column, startPos);
            case '<':
                return new Token(TokenType.LESS_THAN, "<", line, column, startPos);
            case '>':
                return new Token(TokenType.GREATER_THAN, ">", line, column, startPos);
            case '=':
                return new Token(TokenType.ASSIGN, "=", line, column, startPos);
            case '.':
                return new Token(TokenType.DOT, ".", line, column, startPos);
            case '(':
                return new Token(TokenType.LEFT_PAREN, "(", line, column, startPos);
            case ')':
                return new Token(TokenType.RIGHT_PAREN, ")", line, column, startPos);
            case '{':
                return new Token(TokenType.LEFT_BRACE, "{", line, column, startPos);
            case '}':
                return new Token(TokenType.RIGHT_BRACE, "}", line, column, startPos);
            case '[':
                return new Token(TokenType.LEFT_BRACKET, "[", line, column, startPos);
            case ']':
                return new Token(TokenType.RIGHT_BRACKET, "]", line, column, startPos);
            case ',':
                return new Token(TokenType.COMMA, ",", line, column, startPos);
            case ';':
                return new Token(TokenType.SEMICOLON, ";", line, column, startPos);
            case ':':
                return new Token(TokenType.COLON, ":", line, column, startPos);
            case '?':
                return new Token(TokenType.QUESTION, "?", line, column, startPos);
            default:
                return null;
        }
    }

    @Override
    public int getPriority() {
        return 2; // Medium-high priority
    }
}