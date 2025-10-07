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
        // Structure Keywords
        KEYWORDS.put("process", TokenType.PROCESS);
        KEYWORDS.put("function", TokenType.FUNCTION);
        KEYWORDS.put("import", TokenType.IMPORT);
        KEYWORDS.put("import_jar", TokenType.IMPORT_JAR);
        KEYWORDS.put("as", TokenType.AS);
        KEYWORDS.put("return", TokenType.RETURN);

        // Flow Control Keywords
        KEYWORDS.put("start", TokenType.START);
        KEYWORDS.put("end", TokenType.END);
        KEYWORDS.put("task", TokenType.TASK);
        KEYWORDS.put("gateway", TokenType.GATEWAY);
        KEYWORDS.put("goto", TokenType.GOTO);
        KEYWORDS.put("when", TokenType.WHEN);
        KEYWORDS.put("branch", TokenType.BRANCH);
        KEYWORDS.put("join", TokenType.JOIN);
        KEYWORDS.put("else", TokenType.ELSE);
        KEYWORDS.put("parallel", TokenType.PARALLEL);
        KEYWORDS.put("action", TokenType.ACTION);

        // Control Flow Keywords
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("else_if", TokenType.ELSE_IF);
        KEYWORDS.put("try", TokenType.TRY);
        KEYWORDS.put("catch", TokenType.CATCH);
        KEYWORDS.put("throw", TokenType.THROW);
        KEYWORDS.put("while", TokenType.WHILE);
        KEYWORDS.put("for", TokenType.FOR);
        KEYWORDS.put("each", TokenType.EACH);
        KEYWORDS.put("in", TokenType.IN);
        KEYWORDS.put("from", TokenType.FROM);
        KEYWORDS.put("to", TokenType.TO);
        KEYWORDS.put("step", TokenType.STEP);
        KEYWORDS.put("break", TokenType.BREAK);
        KEYWORDS.put("continue", TokenType.CONTINUE);

        // Data Type Keywords
        KEYWORDS.put("integer", TokenType.INTEGER_TYPE);
        KEYWORDS.put("decimal", TokenType.DECIMAL_TYPE);
        KEYWORDS.put("boolean", TokenType.BOOLEAN_TYPE);
        KEYWORDS.put("text", TokenType.TEXT_TYPE);
        KEYWORDS.put("list", TokenType.LIST_TYPE);
        KEYWORDS.put("object", TokenType.OBJECT_TYPE);
        KEYWORDS.put("void", TokenType.VOID);

        // Literal Values
        KEYWORDS.put("null", TokenType.NULL);
        KEYWORDS.put("true", TokenType.TRUE);
        KEYWORDS.put("false", TokenType.FALSE);

        // Logical Operators (as keywords)
        KEYWORDS.put("and", TokenType.AND);
        KEYWORDS.put("or", TokenType.OR);
        KEYWORDS.put("not", TokenType.NOT);

        // Special
        KEYWORDS.put("input", TokenType.INPUT);
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