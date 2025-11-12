package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Reconocedor de espacios en blanco del lenguaje FlowScript.
 * Patrón: [ \t\n\r]+
 *
 * Los espacios en blanco son normalmente ignorados en el análisis sintáctico,
 * pero son importantes para separar tokens.
 *
 * Estados del autómata:
 * - q0: Estado inicial
 * - q1: Al menos un espacio en blanco leído
 * - qf: Estado final
 *
 * Transiciones:
 * - δ(q0, [ \t\n\r]) = q1
 * - δ(q1, [ \t\n\r]) = q1 (bucle)
 * - δ(q1, otro) = qf
 */
public class WhitespaceRecognizer implements TokenRecognizer {

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        // Estado q0 -> q1: el primer carácter debe ser un espacio en blanco
        char firstChar = input.charAt(start);
        if (!isWhitespace(firstChar)) {
            return null; // No es un espacio en blanco
        }

        // Estado q1: consumir todos los espacios en blanco consecutivos
        int pos = start + 1;
        while (pos < input.length() && isWhitespace(input.charAt(pos))) {
            pos++;
        }

        // Estado qf: crear token con los espacios en blanco reconocidos
        String lexeme = input.substring(start, pos);
        return new Token(TokenType.WHITESPACE, lexeme, line, column);
    }

    /**
     * Verifica si un carácter es un espacio en blanco.
     * [ \t\n\r]
     */
    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    @Override
    public int getPriority() {
        return 60; // Prioridad baja (debe intentarse después de otros reconocedores)
    }
}
