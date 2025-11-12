package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Excepción lanzada cuando se encuentra un error durante el análisis léxico.
 */
public class LexerException extends RuntimeException {
    private final int line;
    private final int column;
    private final String lexeme;

    /**
     * Constructor con mensaje de error, posición y lexema.
     *
     * @param message Mensaje de error
     * @param line    Línea donde ocurrió el error (1-based)
     * @param column  Columna donde ocurrió el error (1-based)
     * @param lexeme  Lexema que causó el error
     */
    public LexerException(String message, int line, int column, String lexeme) {
        super(String.format("%s at line %d, column %d: '%s'", message, line, column, lexeme));
        this.line = line;
        this.column = column;
        this.lexeme = lexeme;
    }

    /**
     * Constructor con mensaje de error y posición.
     */
    public LexerException(String message, int line, int column) {
        this(message, line, column, "");
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getLexeme() {
        return lexeme;
    }
}
