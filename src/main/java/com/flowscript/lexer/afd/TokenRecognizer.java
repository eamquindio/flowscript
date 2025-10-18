package com.flowscript.lexer.afd;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

/**
 * Interface for token recognizers using finite automata.
 */
public interface TokenRecognizer {
    /**
     * Attempts to recognize a token from the input starting at the given position.
     *
     * @param input The input string
     * @param startPos Starting position in the input
     * @param line Current line number
     * @param column Current column number
     * @return A Token if recognized, null otherwise
     */
    Token recognize(String input, int startPos, int line, int column);

    /**
     * Gets the priority of this recognizer (lower number = higher priority).
     * This is used to resolve conflicts when multiple recognizers can match.
     */
    int getPriority();
}