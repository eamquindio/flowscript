package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Reconocedor de identificadores del lenguaje FlowScript.
 * Implementa un AFD para reconocer identificadores con el patrón: [a-zA-Z_][a-zA-Z0-9_]*
 *
 * Estados del autómata:
 * - q0: Estado inicial
 * - q1: Primer carácter válido leído (letra o guión bajo)
 * - qf: Estado final (cualquier número de caracteres alfanuméricos o guiones bajos)
 *
 * Transiciones:
 * - δ(q0, [a-zA-Z_]) = q1
 * - δ(q1, [a-zA-Z0-9_]) = q1 (bucle)
 * - δ(q1, otro) = qf (acepta)
 */
public class IdentifierRecognizer implements TokenRecognizer {

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        // Estado q0 -> q1: el primer carácter debe ser letra o guión bajo
        char firstChar = input.charAt(start);
        if (!isIdentifierStart(firstChar)) {
            return null; // No es un identificador
        }

        // Estado q1: consumir caracteres alfanuméricos o guiones bajos
        int pos = start + 1;
        while (pos < input.length() && isIdentifierPart(input.charAt(pos))) {
            pos++;
        }

        // Estado qf: crear token con el lexema reconocido
        String lexeme = input.substring(start, pos);
        return new Token(TokenType.IDENTIFIER, lexeme, line, column);
    }

    /**
     * Verifica si un carácter puede iniciar un identificador.
     * [a-zA-Z_]
     */
    private boolean isIdentifierStart(char c) {
        return Character.isLetter(c) || c == '_';
    }

    /**
     * Verifica si un carácter puede ser parte de un identificador.
     * [a-zA-Z0-9_]
     */
    private boolean isIdentifierPart(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    @Override
    public int getPriority() {
        return 50; // Prioridad media-baja (las keywords deben tener mayor prioridad)
    }
}
