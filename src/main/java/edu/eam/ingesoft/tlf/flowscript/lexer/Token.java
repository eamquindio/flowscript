package edu.eam.ingesoft.tlf.flowscript.lexer;

import java.util.Objects;

/**
 * Representa un token en el análisis léxico de FlowScript.
 * Un token contiene:
 * - El tipo de token (TokenType)
 * - El lexema (texto original del token)
 * - La posición en el código fuente (línea y columna)
 */
public class Token {
    private final TokenType type;
    private final String lexeme;
    private final int line;
    private final int column;

    /**
     * Constructor de Token.
     *
     * @param type   Tipo del token
     * @param lexeme Lexema (texto) del token
     * @param line   Número de línea (1-based)
     * @param column Número de columna (1-based)
     */
    public Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    /**
     * Constructor de Token sin posición (para casos especiales).
     */
    public Token(TokenType type, String lexeme) {
        this(type, lexeme, 0, 0);
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    /**
     * Verifica si este token es de un tipo específico.
     */
    public boolean is(TokenType type) {
        return this.type == type;
    }

    /**
     * Verifica si este token es de alguno de los tipos especificados.
     */
    public boolean isAnyOf(TokenType... types) {
        for (TokenType t : types) {
            if (this.type == t) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (line > 0 && column > 0) {
            return String.format("Token{type=%s, lexeme='%s', line=%d, column=%d}",
                    type, lexeme, line, column);
        } else {
            return String.format("Token{type=%s, lexeme='%s'}",
                    type, lexeme);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return line == token.line &&
                column == token.column &&
                type == token.type &&
                Objects.equals(lexeme, token.lexeme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, lexeme, line, column);
    }

    /**
     * Crea un token EOF (fin de archivo).
     */
    public static Token eof(int line, int column) {
        return new Token(TokenType.EOF, "", line, column);
    }

    /**
     * Crea un token de error.
     */
    public static Token error(String lexeme, int line, int column) {
        return new Token(TokenType.ERROR, lexeme, line, column);
    }
}
