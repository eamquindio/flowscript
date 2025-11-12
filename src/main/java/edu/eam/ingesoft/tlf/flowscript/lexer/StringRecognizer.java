package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Reconocedor de literales de cadena del lenguaje FlowScript.
 * Patrón: " [^"]* "
 *
 * Estados del autómata:
 * - q0: Estado inicial
 * - q1: Comilla abierta leída
 * - q2: Contenido de string leído
 * - qf: Estado final (comilla cerrada)
 *
 * Transiciones:
 * - δ(q0, '"') = q1
 * - δ(q1, [^"]) = q2
 * - δ(q1, '"') = qf (string vacío)
 * - δ(q2, [^"]) = q2 (bucle)
 * - δ(q2, '"') = qf
 */
public class StringRecognizer implements TokenRecognizer {

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        // Estado q0 -> q1: el primer carácter debe ser comilla doble
        if (input.charAt(start) != '"') {
            return null; // No es una cadena
        }

        // Estado q1/q2: buscar la comilla de cierre
        int pos = start + 1;
        while (pos < input.length() && input.charAt(pos) != '"') {
            // TODO: En una versión más completa, aquí se manejarían secuencias de escape
            pos++;
        }

        // Verificar si encontramos la comilla de cierre
        if (pos >= input.length()) {
            // String no terminado - lanzar excepción
            throw new LexerException("Unterminated string literal", line, column,
                    input.substring(start, Math.min(start + 20, input.length())));
        }

        // Estado qf: comilla de cierre encontrada
        pos++; // Incluir la comilla de cierre

        // Crear token con el lexema completo (incluyendo comillas)
        String lexeme = input.substring(start, pos);
        return new Token(TokenType.STRING_LITERAL, lexeme, line, column);
    }

    @Override
    public int getPriority() {
        return 20; // Prioridad alta
    }
}
