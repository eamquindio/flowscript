package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Enumeración de todos los tipos de tokens del lenguaje FlowScript Simplificado.
 */
public enum TokenType {
    // Palabras reservadas - Declaraciones
    FUNCION,
    PROCESO,
    RETORNAR,

    // Palabras reservadas - Control
    SI,
    SINO_SI,
    SINO,
    PARA,
    EN,

    // Palabras reservadas - Tipos
    ENTERO,
    DECIMAL,
    BOOLEANO,
    TEXTO,
    LISTA,
    OBJETO,
    VACIO,

    // Palabras reservadas - Literales booleanos
    VERDADERO,
    FALSO,
    NULO,

    // Palabras reservadas - Operadores lógicos
    Y,
    O,
    NO,

    // Palabras reservadas - Elementos de proceso
    INICIO,
    FIN,
    TAREA,
    GATEWAY,
    PARALELO,
    RAMA,
    UNIR,
    ACCION,
    IR_A,

    // Palabras reservadas - Operaciones especiales
    DB,
    HTTP,

    // Identificadores
    IDENTIFIER,

    // Literales
    INTEGER_LITERAL,
    DECIMAL_LITERAL,
    STRING_LITERAL,

    // Operadores aritméticos
    PLUS,           // +
    MINUS,          // -
    MULTIPLY,       // *
    DIVIDE,         // /
    MODULO,         // %

    // Operadores relacionales
    LESS_THAN,      // <
    GREATER_THAN,   // >
    LESS_EQUAL,     // <=
    GREATER_EQUAL,  // >=

    // Operadores de igualdad
    EQUAL,          // ==
    NOT_EQUAL,      // !=

    // Operador de asignación
    ASSIGN,         // =

    // Operador de flecha
    ARROW,          // ->

    // Delimitadores
    LEFT_PAREN,     // (
    RIGHT_PAREN,    // )
    LEFT_BRACE,     // {
    RIGHT_BRACE,    // }
    LEFT_BRACKET,   // [
    RIGHT_BRACKET,  // ]
    COMMA,          // ,
    COLON,          // :
    DOT,            // .

    // Espacios en blanco (generalmente ignorados)
    WHITESPACE,

    // Comentarios (generalmente ignorados)
    LINE_COMMENT,
    BLOCK_COMMENT,

    // Fin de archivo
    EOF,

    // Token de error
    ERROR;

    /**
     * Verifica si este tipo de token es una palabra reservada.
     */
    public boolean isKeyword() {
        return this.ordinal() >= FUNCION.ordinal() && this.ordinal() <= HTTP.ordinal();
    }

    /**
     * Verifica si este tipo de token debe ser ignorado en el análisis sintáctico.
     */
    public boolean isIgnorable() {
        return this == WHITESPACE || this == LINE_COMMENT || this == BLOCK_COMMENT;
    }

    /**
     * Verifica si este tipo de token es un literal.
     */
    public boolean isLiteral() {
        return this == INTEGER_LITERAL ||
               this == DECIMAL_LITERAL ||
               this == STRING_LITERAL ||
               this == VERDADERO ||
               this == FALSO ||
               this == NULO;
    }

    /**
     * Verifica si este tipo de token es un operador.
     */
    public boolean isOperator() {
        return this.ordinal() >= PLUS.ordinal() && this.ordinal() <= ARROW.ordinal();
    }

    /**
     * Verifica si este tipo de token es un delimitador.
     */
    public boolean isDelimiter() {
        return this.ordinal() >= LEFT_PAREN.ordinal() && this.ordinal() <= DOT.ordinal();
    }
}