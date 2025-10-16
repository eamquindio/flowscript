package com.flowscript.sintactic;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

import java.util.List;

/**
 * Contexto compartido entre todos los parsers.
 *
 * <p>Mantiene el estado del análisis sintáctico incluyendo la lista de tokens
 * y el índice actual. Provee métodos de utilidad para navegar y consumir tokens.</p>
 */
public class ParserContext {
    private final List<Token> tokens;
    private int currentIndex;

    public ParserContext(List<Token> tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
    }

    public ParserContext(List<Token> tokens, int startIndex) {
        this.tokens = tokens;
        this.currentIndex = startIndex;
    }

    /**
     * Obtiene el token actual sin consumirlo.
     */
    public Token getCurrentToken() {
        if (currentIndex < tokens.size()) {
            return tokens.get(currentIndex);
        }
        return null;
    }

    /**
     * Obtiene el índice actual en la lista de tokens.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Avanza al siguiente token.
     */
    public void advance() {
        if (currentIndex < tokens.size()) {
            currentIndex++;
        }
    }

    /**
     * Consume el token actual y avanza al siguiente.
     *
     * @return El token consumido
     * @throws Parser.ParseException Si no hay más tokens
     */
    public Token consume() throws Parser.ParseException {
        Token current = getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input");
        }
        advance();
        return current;
    }

    /**
     * Consume un token esperando un tipo específico.
     *
     * @param expectedType El tipo de token esperado
     * @return El token consumido
     * @throws Parser.ParseException Si el token actual no es del tipo esperado
     */
    public Token consume(TokenType expectedType) throws Parser.ParseException {
        Token current = getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("Expected " + expectedType + " but reached end of input");
        }
        if (current.getType() != expectedType) {
            throw new Parser.ParseException(
                "Expected " + expectedType + " but found " + current.getType() +
                " at line " + current.getLine() + ", column " + current.getColumn()
            );
        }
        advance();
        return current;
    }

    /**
     * Consume un token esperando un valor específico.
     *
     * @param expectedValue El valor del token esperado
     * @return El token consumido
     * @throws Parser.ParseException Si el token actual no tiene el valor esperado
     */
    public Token consumeValue(String expectedValue) throws Parser.ParseException {
        Token current = getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("Expected '" + expectedValue + "' but reached end of input");
        }
        if (!current.getValue().equals(expectedValue)) {
            throw new Parser.ParseException(
                "Expected '" + expectedValue + "' but found '" + current.getValue() +
                "' at line " + current.getLine() + ", column " + current.getColumn()
            );
        }
        advance();
        return current;
    }

    /**
     * Verifica si el token actual es del tipo especificado sin consumirlo.
     */
    public boolean check(TokenType type) {
        Token current = getCurrentToken();
        return current != null && current.getType() == type;
    }

    /**
     * Verifica si el token actual tiene el valor especificado sin consumirlo.
     */
    public boolean checkValue(String value) {
        Token current = getCurrentToken();
        return current != null && current.getValue().equals(value);
    }

    /**
     * Verifica si el token actual es de alguno de los tipos especificados.
     */
    public boolean checkAny(TokenType... types) {
        Token current = getCurrentToken();
        if (current == null) return false;

        for (TokenType type : types) {
            if (current.getType() == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mira adelante N tokens sin consumir.
     *
     * @param offset Cuántos tokens mirar adelante (1 = siguiente token)
     * @return El token en la posición actual + offset, o null si está fuera de rango
     */
    public Token peek(int offset) {
        int index = currentIndex + offset;
        if (index >= 0 && index < tokens.size()) {
            return tokens.get(index);
        }
        return null;
    }

    /**
     * Establece el índice actual.
     */
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

    /**
     * Obtiene la lista completa de tokens.
     */
    public List<Token> getTokens() {
        return tokens;
    }

    /**
     * Verifica si hay más tokens disponibles.
     */
    public boolean hasMoreTokens() {
        return currentIndex < tokens.size() && getCurrentToken().getType() != TokenType.EOF;
    }
}

