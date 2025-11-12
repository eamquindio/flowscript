package edu.eam.ingesoft.tlf.flowscript.lexer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Analizador léxico (Lexer) para el lenguaje FlowScript Simplificado.
 *
 * El lexer coordina múltiples reconocedores de tokens (TokenRecognizer),
 * cada uno implementando un autómata finito para reconocer un tipo específico de token.
 *
 * El proceso de tokenización:
 * 1. Lee la entrada carácter por carácter
 * 2. Intenta reconocer un token usando los reconocedores disponibles (por orden de prioridad)
 * 3. Mantiene el seguimiento de líneas y columnas para reportar errores
 * 4. Genera una lista de tokens hasta EOF
 *
 * Uso:
 * <pre>
 *     Lexer lexer = new Lexer("funcion suma(a: entero, b: entero) -> entero { retornar a + b }");
 *     List<Token> tokens = lexer.tokenize();
 * </pre>
 */
public class Lexer {
    private final String input;
    private final List<TokenRecognizer> recognizers;
    private int position;
    private int line;
    private int column;

    /**
     * Constructor que inicializa el lexer con la entrada de texto.
     *
     * @param input Código fuente de FlowScript a analizar
     */
    public Lexer(String input) {
        this.input = input;
        this.position = 0;
        this.line = 1;
        this.column = 1;
        this.recognizers = new ArrayList<>();

        // Registrar todos los reconocedores
        registerRecognizers();

        // Ordenar reconocedores por prioridad (menor número = mayor prioridad)
        recognizers.sort(Comparator.comparingInt(TokenRecognizer::getPriority));
    }

    /**
     * Registra todos los reconocedores de tokens disponibles.
     * El orden de registro no importa porque se ordenan por prioridad.
     */
    private void registerRecognizers() {
        // Comentarios (mayor prioridad para evitar confusión con operadores)
        recognizers.add(new BlockCommentRecognizer());
        recognizers.add(new LineCommentRecognizer());

        // Keywords (alta prioridad para evitar que sean identificadores)
        recognizers.add(new KeywordRecognizer());

        // Literales
        recognizers.add(new StringRecognizer());
        recognizers.add(new NumberRecognizer());

        // Operadores
        recognizers.add(new OperatorRecognizer());

        // Delimitadores
        recognizers.add(new DelimiterRecognizer());

        // Identificadores (prioridad baja)
        recognizers.add(new IdentifierRecognizer());

        // Espacios en blanco (menor prioridad)
        recognizers.add(new WhitespaceRecognizer());
    }

    /**
     * Tokeniza toda la entrada y retorna la lista de tokens.
     *
     * @return Lista de tokens reconocidos, incluyendo EOF al final
     * @throws LexerException si se encuentra un carácter inválido
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            Token token = nextToken();

            if (token != null) {
                tokens.add(token);
            } else {
                // No se pudo reconocer ningún token - error léxico
                char invalidChar = input.charAt(position);
                throw new LexerException(
                        String.format("Unexpected character '%c'", invalidChar),
                        line,
                        column,
                        String.valueOf(invalidChar)
                );
            }
        }

        // Agregar token EOF al final
        tokens.add(Token.eof(line, column));

        return tokens;
    }

    /**
     * Tokeniza toda la entrada y retorna solo los tokens relevantes
     * (filtra espacios en blanco y comentarios).
     *
     * @return Lista de tokens relevantes, incluyendo EOF al final
     * @throws LexerException si se encuentra un carácter inválido
     */
    public List<Token> tokenizeFiltered() {
        List<Token> allTokens = tokenize();
        List<Token> filteredTokens = new ArrayList<>();

        for (Token token : allTokens) {
            if (!token.getType().isIgnorable()) {
                filteredTokens.add(token);
            }
        }

        return filteredTokens;
    }

    /**
     * Intenta reconocer el siguiente token en la posición actual.
     *
     * @return Token reconocido, o null si ningún reconocedor pudo reconocer un token
     */
    private Token nextToken() {
        // Intentar cada reconocedor en orden de prioridad
        for (TokenRecognizer recognizer : recognizers) {
            Token token = recognizer.recognize(input, position, line, column);

            if (token != null) {
                // Token reconocido - actualizar posición y contadores de línea/columna
                int tokenLength = token.getLexeme().length();
                updatePosition(token.getLexeme());
                return token;
            }
        }

        return null; // Ningún reconocedor pudo reconocer un token
    }

    /**
     * Actualiza la posición, línea y columna después de consumir un lexema.
     *
     * @param lexeme Lexema consumido
     */
    private void updatePosition(String lexeme) {
        for (char c : lexeme.toCharArray()) {
            position++;

            if (c == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
        }
    }

    /**
     * Obtiene la posición actual en la entrada.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Obtiene la línea actual.
     */
    public int getLine() {
        return line;
    }

    /**
     * Obtiene la columna actual.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Obtiene la entrada completa.
     */
    public String getInput() {
        return input;
    }
}
