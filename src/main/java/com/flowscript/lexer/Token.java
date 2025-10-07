package com.flowscript.lexer;

/**
 * Represents a token in the FlowScript language.
 * Each token has a type, value, and position information.
 */
public class Token {
    private final TokenType type;
    private final String value;
    private final int line;
    private final int column;
    private final int position;  // Position in the input stream

    public Token(TokenType type, String value, int line, int column, int position) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
        this.position = position;
    }

    // Constructor for tokens without value (like operators)
    public Token(TokenType type, int line, int column, int position) {
        this(type, type.getDefaultValue(), line, column, position);
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return String.format("Token[%s, '%s', %d:%d]", type, value, line, column);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Token token = (Token) obj;
        return type == token.type &&
               value.equals(token.value) &&
               line == token.line &&
               column == token.column;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + line;
        result = 31 * result + column;
        return result;
    }
}