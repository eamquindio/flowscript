package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Reconocedor de comentarios de línea del lenguaje FlowScript.
 * Patrón: # [^\n]*
 *
 * Un comentario de línea comienza con '#' y se extiende hasta el final de la línea.
 *
 * Estados del autómata:
 * - q0: Estado inicial
 * - q1: '#' leído
 * - q2: Contenido del comentario
 * - qf: Estado final (fin de línea o fin de archivo)
 *
 * Transiciones:
 * - δ(q0, '#') = q1
 * - δ(q1, [^\n]) = q2
 * - δ(q1, '\n' | EOF) = qf
 * - δ(q2, [^\n]) = q2 (bucle)
 * - δ(q2, '\n' | EOF) = qf
 */
public class LineCommentRecognizer implements TokenRecognizer {

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        // Estado q0 -> q1: el primer carácter debe ser '#'
        if (input.charAt(start) != '#') {
            return null; // No es un comentario de línea
        }

        // Estado q1/q2: consumir hasta el final de la línea o el final del archivo
        int pos = start + 1;
        while (pos < input.length() && input.charAt(pos) != '\n') {
            pos++;
        }

        // Estado qf: crear token con el comentario completo
        // Nota: no incluimos el '\n' en el lexema del comentario
        String lexeme = input.substring(start, pos);
        return new Token(TokenType.LINE_COMMENT, lexeme, line, column);
    }

    @Override
    public int getPriority() {
        return 15; // Alta prioridad (antes de que '/' sea reconocido como operador de división)
    }
}
