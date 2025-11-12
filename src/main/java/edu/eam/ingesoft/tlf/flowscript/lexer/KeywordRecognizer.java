package edu.eam.ingesoft.tlf.flowscript.lexer;

import java.util.HashMap;
import java.util.Map;

/**
 * Reconocedor de palabras reservadas (keywords) del lenguaje FlowScript.
 * Implementa autómatas finitos para reconocer cada keyword específica.
 *
 * Las keywords deben ser reconocidas como palabras completas, no como parte
 * de un identificador. Por ejemplo, "funcion" es keyword pero "funcion2" es identificador.
 */
public class KeywordRecognizer implements TokenRecognizer {

    // Mapa de keywords a sus tipos de token correspondientes
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {
        // Declaraciones
        KEYWORDS.put("funcion", TokenType.FUNCION);
        KEYWORDS.put("proceso", TokenType.PROCESO);
        KEYWORDS.put("retornar", TokenType.RETORNAR);

        // Control
        KEYWORDS.put("si", TokenType.SI);
        KEYWORDS.put("sino_si", TokenType.SINO_SI);
        KEYWORDS.put("sino", TokenType.SINO);
        KEYWORDS.put("para", TokenType.PARA);
        KEYWORDS.put("en", TokenType.EN);

        // Tipos
        KEYWORDS.put("entero", TokenType.ENTERO);
        KEYWORDS.put("decimal", TokenType.DECIMAL);
        KEYWORDS.put("booleano", TokenType.BOOLEANO);
        KEYWORDS.put("texto", TokenType.TEXTO);
        KEYWORDS.put("lista", TokenType.LISTA);
        KEYWORDS.put("objeto", TokenType.OBJETO);
        KEYWORDS.put("vacio", TokenType.VACIO);

        // Literales
        KEYWORDS.put("verdadero", TokenType.VERDADERO);
        KEYWORDS.put("falso", TokenType.FALSO);
        KEYWORDS.put("nulo", TokenType.NULO);

        // Operadores lógicos
        KEYWORDS.put("y", TokenType.Y);
        KEYWORDS.put("o", TokenType.O);
        KEYWORDS.put("no", TokenType.NO);

        // Elementos de proceso
        KEYWORDS.put("inicio", TokenType.INICIO);
        KEYWORDS.put("fin", TokenType.FIN);
        KEYWORDS.put("tarea", TokenType.TAREA);
        KEYWORDS.put("gateway", TokenType.GATEWAY);
        KEYWORDS.put("paralelo", TokenType.PARALELO);
        KEYWORDS.put("rama", TokenType.RAMA);
        KEYWORDS.put("unir", TokenType.UNIR);
        KEYWORDS.put("accion", TokenType.ACCION);
        KEYWORDS.put("ir_a", TokenType.IR_A);

        // Operaciones especiales
        KEYWORDS.put("db", TokenType.DB);
        KEYWORDS.put("http", TokenType.HTTP);
    }

    @Override
    public Token recognize(String input, int start, int line, int column) {
        if (start >= input.length()) {
            return null;
        }

        // Buscar la keyword más larga que coincida
        Token longestMatch = null;
        int longestLength = 0;

        for (Map.Entry<String, TokenType> entry : KEYWORDS.entrySet()) {
            String keyword = entry.getKey();
            TokenType type = entry.getValue();

            // Verificar si la keyword coincide en la posición actual
            if (matches(input, start, keyword)) {
                int endPos = start + keyword.length();

                // Verificar que la keyword no sea parte de un identificador más largo
                // Es decir, el siguiente carácter no debe ser letra, dígito o guión bajo
                if (endPos >= input.length() || !isIdentifierChar(input.charAt(endPos))) {
                    if (keyword.length() > longestLength) {
                        longestMatch = new Token(type, keyword, line, column);
                        longestLength = keyword.length();
                    }
                }
            }
        }

        return longestMatch;
    }

    /**
     * Verifica si el keyword coincide en la posición dada del input.
     */
    private boolean matches(String input, int start, String keyword) {
        if (start + keyword.length() > input.length()) {
            return false;
        }

        for (int i = 0; i < keyword.length(); i++) {
            if (input.charAt(start + i) != keyword.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica si un carácter puede ser parte de un identificador.
     */
    private boolean isIdentifierChar(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    @Override
    public int getPriority() {
        return 10; // Alta prioridad para que las keywords se reconozcan antes que los identificadores
    }
}
