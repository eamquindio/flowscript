package edu.eam.ingesoft.tlf.flowscript.lexer;

/**
 * Reconocedor de comentarios multilinea del lenguaje FlowScript.
 * Patron: comentarios de bloque que comienzan con slash-asterisco y terminan con asterisco-slash.
 * Puede extenderse a traves de multiples lineas.
 */
public class BlockCommentRecognizer implements TokenRecognizer {

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        // El primer caracter debe ser '/'
        if (input.charAt(start) != '/') {
            return null;
        }

        // Verificar que hay al menos un caracter mas
        if (start + 1 >= input.length()) {
            return null;
        }

        // El segundo caracter debe ser '*'
        if (input.charAt(start + 1) != '*') {
            return null; // No es un comentario de bloque
        }

        // Buscar el cierre del comentario
        int pos = start + 2;
        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (c == '*' && pos + 1 < input.length() && input.charAt(pos + 1) == '/') {
                // Encontrado el cierre del comentario
                pos += 2; // Incluir el cierre
                String lexeme = input.substring(start, pos);
                return new Token(TokenType.BLOCK_COMMENT, lexeme, line, column);
            }

            pos++;
        }

        // Comentario no terminado - lanzar excepcion
        throw new LexerException("Unterminated block comment", line, column,
                input.substring(start, Math.min(start + 20, input.length())));
    }

    @Override
    public int getPriority() {
        return 14; // Prioridad muy alta
    }
}
