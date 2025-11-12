package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Reconocedor de literales numéricos (enteros y decimales) del lenguaje FlowScript.
 *
 * INTEGER_LITERAL: [0-9]+
 * DECIMAL_LITERAL: [0-9]+ '.' [0-9]+
 *
 * Estados del autómata:
 * - q0: Estado inicial
 * - q1: Parte entera leída (uno o más dígitos)
 * - q2: Punto decimal leído
 * - q3: Parte decimal leída (uno o más dígitos)
 * - qf: Estado final
 *
 * Transiciones:
 * - δ(q0, [0-9]) = q1
 * - δ(q1, [0-9]) = q1 (bucle)
 * - δ(q1, '.') = q2
 * - δ(q1, otro) = qf (acepta como INTEGER)
 * - δ(q2, [0-9]) = q3
 * - δ(q3, [0-9]) = q3 (bucle)
 * - δ(q3, otro) = qf (acepta como DECIMAL)
 */
public class NumberRecognizer implements TokenRecognizer {

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        // Estado q0 -> q1: el primer carácter debe ser un dígito
        char firstChar = input.charAt(start);
        if (!Character.isDigit(firstChar)) {
            return null; // No es un número
        }

        // Estado q1: consumir dígitos de la parte entera
        int pos = start + 1;
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            pos++;
        }

        // Verificar si hay un punto decimal
        if (pos < input.length() && input.charAt(pos) == '.') {
            // Estado q2: punto decimal encontrado
            int dotPos = pos;
            pos++;

            // Debe haber al menos un dígito después del punto
            if (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                // Estado q3: consumir dígitos de la parte decimal
                pos++;
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                    pos++;
                }

                // Estado qf: acepta como DECIMAL_LITERAL
                String lexeme = input.substring(start, pos);
                return new Token(TokenType.DECIMAL_LITERAL, lexeme, line, column);
            } else {
                // No hay dígitos después del punto: es un entero seguido de un punto
                // Solo reconocemos la parte entera
                String lexeme = input.substring(start, dotPos);
                return new Token(TokenType.INTEGER_LITERAL, lexeme, line, column);
            }
        } else {
            // No hay punto decimal: es un INTEGER_LITERAL
            String lexeme = input.substring(start, pos);
            return new Token(TokenType.INTEGER_LITERAL, lexeme, line, column);
        }
    }

    @Override
    public int getPriority() {
        return 30; // Prioridad media-alta
    }
}
