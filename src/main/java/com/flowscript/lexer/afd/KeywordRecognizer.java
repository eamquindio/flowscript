package com.flowscript.lexer.afd;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import java.util.HashMap;
import java.util.Map;

/**
 * Recognizer for keywords and reserved words using a finite automaton approach.
 */
public class KeywordRecognizer implements TokenRecognizer {
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {
        // Structure Keywords (English and Spanish)
        KEYWORDS.put("process", TokenType.PROCESS);
        KEYWORDS.put("proceso", TokenType.PROCESS);
        KEYWORDS.put("function", TokenType.FUNCTION);
        KEYWORDS.put("funcion", TokenType.FUNCTION);
        KEYWORDS.put("import", TokenType.IMPORT);
        KEYWORDS.put("importar", TokenType.IMPORT);
        KEYWORDS.put("import_jar", TokenType.IMPORT_JAR);
        KEYWORDS.put("importar_jar", TokenType.IMPORT_JAR);
        KEYWORDS.put("as", TokenType.AS);
        KEYWORDS.put("como", TokenType.AS);
        KEYWORDS.put("return", TokenType.RETURN);
        KEYWORDS.put("retornar", TokenType.RETURN);

        // Flow Control Keywords (English and Spanish)
        KEYWORDS.put("start", TokenType.START);
        KEYWORDS.put("inicio", TokenType.START);
        KEYWORDS.put("end", TokenType.END);
        KEYWORDS.put("fin", TokenType.END);
        KEYWORDS.put("task", TokenType.TASK);
        KEYWORDS.put("tarea", TokenType.TASK);
        KEYWORDS.put("gateway", TokenType.GATEWAY);
        KEYWORDS.put("go_to", TokenType.GOTO);
        KEYWORDS.put("ir_a", TokenType.GOTO);
        KEYWORDS.put("when", TokenType.WHEN);
        KEYWORDS.put("cuando", TokenType.WHEN);
        KEYWORDS.put("branch", TokenType.BRANCH);
        KEYWORDS.put("rama", TokenType.BRANCH);
        KEYWORDS.put("join", TokenType.JOIN);
        KEYWORDS.put("unir", TokenType.JOIN);
        KEYWORDS.put("else", TokenType.ELSE);
        KEYWORDS.put("sino", TokenType.ELSE);
        KEYWORDS.put("parallel", TokenType.PARALLEL);
        KEYWORDS.put("paralelo", TokenType.PARALLEL);
        KEYWORDS.put("action", TokenType.ACTION);
        KEYWORDS.put("accion", TokenType.ACTION);

        // Control Flow Keywords (English and Spanish)
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("si", TokenType.IF);
        KEYWORDS.put("else_if", TokenType.ELSE_IF);
        KEYWORDS.put("sino_si", TokenType.ELSE_IF);
        KEYWORDS.put("try", TokenType.TRY);
        KEYWORDS.put("intentar", TokenType.TRY);
        KEYWORDS.put("catch", TokenType.CATCH);
        KEYWORDS.put("capturar", TokenType.CATCH);
        KEYWORDS.put("throw", TokenType.THROW);
        KEYWORDS.put("lanzar", TokenType.THROW);
        KEYWORDS.put("while", TokenType.WHILE);
        KEYWORDS.put("mientras", TokenType.WHILE);
        KEYWORDS.put("for", TokenType.FOR);
        KEYWORDS.put("para", TokenType.FOR);
        KEYWORDS.put("each", TokenType.EACH);
        KEYWORDS.put("cada", TokenType.EACH);
        KEYWORDS.put("in", TokenType.IN);
        KEYWORDS.put("en", TokenType.IN);
        KEYWORDS.put("from", TokenType.FROM);
        KEYWORDS.put("desde", TokenType.FROM);
        KEYWORDS.put("to", TokenType.TO);
        KEYWORDS.put("hasta", TokenType.TO);
        KEYWORDS.put("step", TokenType.STEP);
        KEYWORDS.put("break", TokenType.BREAK);
        KEYWORDS.put("continue", TokenType.CONTINUE);

        // Data Type Keywords (English and Spanish)
        KEYWORDS.put("integer", TokenType.INTEGER_TYPE);
        KEYWORDS.put("entero", TokenType.INTEGER_TYPE);
        KEYWORDS.put("decimal", TokenType.DECIMAL_TYPE);
        KEYWORDS.put("boolean", TokenType.BOOLEAN_TYPE);
        KEYWORDS.put("booleano", TokenType.BOOLEAN_TYPE);
        KEYWORDS.put("text", TokenType.TEXT_TYPE);
        KEYWORDS.put("texto", TokenType.TEXT_TYPE);
        KEYWORDS.put("list", TokenType.LIST_TYPE);
        KEYWORDS.put("lista", TokenType.LIST_TYPE);
        KEYWORDS.put("object", TokenType.OBJECT_TYPE);
        KEYWORDS.put("objeto", TokenType.OBJECT_TYPE);
        KEYWORDS.put("void", TokenType.VOID);
        KEYWORDS.put("vacio", TokenType.VOID);

        // Literal Values (English and Spanish)
        KEYWORDS.put("null", TokenType.NULL);
        KEYWORDS.put("nulo", TokenType.NULL);
        KEYWORDS.put("true", TokenType.TRUE);
        KEYWORDS.put("verdadero", TokenType.TRUE);
        KEYWORDS.put("false", TokenType.FALSE);
        KEYWORDS.put("falso", TokenType.FALSE);

        // Logical Operators (English and Spanish - only unambiguous ones)
        KEYWORDS.put("and", TokenType.AND);
        KEYWORDS.put("y", TokenType.AND);
        KEYWORDS.put("or", TokenType.OR);
        KEYWORDS.put("o", TokenType.OR);
        KEYWORDS.put("not", TokenType.NOT);
        KEYWORDS.put("no", TokenType.NOT);

        // Special (English and Spanish)
        KEYWORDS.put("input", TokenType.INPUT);
        KEYWORDS.put("entrada", TokenType.INPUT);
    }

    @Override
    public Token recognize(String input, int startPos, int line, int column) {
        if (startPos >= input.length()) {
            return null;
        }

        // Check if we're starting with a letter or underscore (valid identifier/keyword start)
        char firstChar = input.charAt(startPos);
        if (!isIdentifierStart(firstChar)) {
            return null;
        }

        // Build the identifier/keyword
        StringBuilder lexeme = new StringBuilder();
        int currentPos = startPos;

        while (currentPos < input.length() && isIdentifierPart(input.charAt(currentPos))) {
            lexeme.append(input.charAt(currentPos));
            currentPos++;
        }

        String word = lexeme.toString();

        // Check if it's a keyword
        TokenType keywordType = KEYWORDS.get(word);
        if (keywordType != null) {
            return new Token(keywordType, word, line, column, startPos);
        }

        // Not a keyword, let the IdentifierRecognizer handle it
        return null;
    }

    private boolean isIdentifierStart(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isIdentifierPart(char c) {
        return isIdentifierStart(c) || (c >= '0' && c <= '9');
    }

    @Override
    public int getPriority() {
        return 1; // High priority - check keywords before identifiers
    }
}