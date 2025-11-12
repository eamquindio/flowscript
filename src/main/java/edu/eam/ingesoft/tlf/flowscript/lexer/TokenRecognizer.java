package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Interfaz para reconocedores de tokens.
 * Cada reconocedor implementa un autómata finito para reconocer un tipo específico de token.
 */
public interface TokenRecognizer {
    /**
     * Intenta reconocer un token desde la posición actual de la entrada.
     *
     * @param input  Cadena de entrada completa
     * @param start  Posición inicial para el reconocimiento
     * @param line   Número de línea actual (1-based)
     * @param column Número de columna actual (1-based)
     * @return Token reconocido, o null si no se pudo reconocer
     */
    Token recognize(String input, int start, int line, int column);

    /**
     * Obtiene la prioridad del reconocedor (menor número = mayor prioridad).
     * Útil cuando múltiples reconocedores pueden aceptar la misma entrada.
     * Por ejemplo, las keywords deben tener prioridad sobre los identificadores.
     *
     * @return Prioridad del reconocedor
     */
    default int getPriority() {
        return 100; // Prioridad por defecto (media)
    }
}
