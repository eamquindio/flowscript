package com.flowscript.sintactic;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.*;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import java.util.List;

/**
 * Base parser class for FlowScript language.
 * Implements recursive descent parsing based on the BNF grammar.
 */
public class Parser {
    protected final List<Token> tokens;
    protected int currentIndex;
    protected Token currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
        this.currentToken = tokens.isEmpty() ? null : tokens.get(0);
    }

    /**
     * Parses the token stream into an AST.
     */
    public ProgramNode parse() throws ParseException {
        return parseProgram();
    }

    /**
     * Returns the current token being processed.
     */
    public Token getCurrentToken() {
        return currentToken;
    }

    /**
     * Returns the token at the specified offset from current position.
     */
    protected Token peekToken(int offset) {
        int index = currentIndex + offset;
        return (index < tokens.size()) ? tokens.get(index) : null;
    }

    /**
     * Advances to the next token.
     */
    protected void nextToken() {
        if (currentIndex < tokens.size() - 1) {
            currentIndex++;
            currentToken = tokens.get(currentIndex);
        } else {
            currentToken = null;
        }
    }

    /**
     * Consumes the current token if it matches the expected type.
     * Throws ParseException if token doesn't match.
     */
    protected Token consume(TokenType expectedType) throws ParseException {
        if (currentToken == null) {
            throw new ParseException("Unexpected end of input, expected " + expectedType);
        }
        if (currentToken.getType() != expectedType) {
            throw new ParseException("Expected " + expectedType + " but found " +
                currentToken.getType() + " at line " + currentToken.getLine());
        }
        Token token = currentToken;
        nextToken();
        return token;
    }

    /**
     * Checks if the current token matches the expected type without consuming it.
     */
    protected boolean check(TokenType expectedType) {
        return currentToken != null && currentToken.getType() == expectedType;
    }

    /**
     * Checks if the current token matches any of the expected types.
     */
    protected boolean checkAny(TokenType... expectedTypes) {
        if (currentToken == null) return false;
        for (TokenType type : expectedTypes) {
            if (currentToken.getType() == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * Consumes the current token without type checking.
     */
    protected Token consume() throws ParseException {
        Token token = getCurrentToken();
        nextToken();
        return token;
    }

    /**
     * Returns the current index in the token list.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Synchronizes this parser's position with another parser.
     */
    public void syncTo(Token token, int index) {
        this.currentIndex = index;
        this.currentToken = token;
    }

    /**
     * Skips tokens until we reach EOF or recover from error.
     */
    protected void skipToRecovery() {
        while (currentToken != null && currentToken.getType() != TokenType.EOF) {
            if (checkAny(TokenType.SEMICOLON, TokenType.RIGHT_BRACE, TokenType.FUNCTION,
                        TokenType.PROCESS, TokenType.IMPORT, TokenType.IMPORT_JAR)) {
                if (check(TokenType.SEMICOLON)) {
                    nextToken(); // consume semicolon
                }
                break;
            }
            nextToken();
        }
    }

    /**
     * Main parsing method - to be implemented by specific parsers.
     */
    protected ProgramNode parseProgram() throws ParseException {
        throw new UnsupportedOperationException("parseProgram() must be implemented by subclasses");
    }

    /**
     * Exception thrown during parsing errors.
     */
    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }

        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}