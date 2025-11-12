package edu.eam.ingesoft.tlf.flowscript.parser;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;

import java.util.List;

/**
 * Contexto compartido durante el análisis sintáctico.
 * Mantiene el estado del parser incluyendo la lista de tokens y la posición actual.
 *
 * Esta clase sigue el patrón de diseño "Context Object" y proporciona métodos
 * de utilidad para navegar por los tokens de forma segura.
 */
public class ParserContext {
    private final List<Token> tokens;
    private int position;

    public ParserContext(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    /**
     * Obtiene el token en la posición actual sin avanzar.
     */
    public Token current() {
        if (position >= tokens.size()) {
            return tokens.get(tokens.size() - 1); // Retorna EOF
        }
        return tokens.get(position);
    }

    /**
     * Obtiene el token en la posición actual y avanza.
     */
    public Token consume() {
        Token token = current();
        if (!isAtEnd()) {
            position++;
        }
        return token;
    }

    /**
     * Avanza al siguiente token.
     */
    public void advance() {
        if (!isAtEnd()) {
            position++;
        }
    }

    /**
     * Mira el siguiente token sin avanzar (lookahead).
     *
     * @param offset Número de tokens a mirar adelante (1 = siguiente token)
     */
    public Token peek(int offset) {
        int targetPos = position + offset;
        if (targetPos >= tokens.size()) {
            return tokens.get(tokens.size() - 1); // Retorna EOF
        }
        return tokens.get(targetPos);
    }

    /**
     * Mira el siguiente token sin avanzar.
     */
    public Token peek() {
        return peek(1);
    }

    /**
     * Verifica si el token actual es del tipo especificado.
     */
    public boolean check(TokenType type) {
        return current().getType() == type;
    }

    /**
     * Verifica si el token actual es de alguno de los tipos especificados.
     */
    public boolean checkAny(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Si el token actual es del tipo especificado, lo consume y retorna true.
     */
    public boolean match(TokenType type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    /**
     * Si el token actual es de alguno de los tipos especificados, lo consume y retorna true.
     */
    public boolean matchAny(TokenType... types) {
        for (TokenType type : types) {
            if (match(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Consume el token actual verificando que sea del tipo esperado.
     * Lanza ParserException si no coincide.
     */
    public Token expect(TokenType expected) {
        if (!check(expected)) {
            throw new ParserException(
                    "Unexpected token",
                    current(),
                    expected.toString()
            );
        }
        return consume();
    }

    /**
     * Verifica que el token actual sea de alguno de los tipos esperados y lo consume.
     */
    public Token expectAny(TokenType... expected) {
        for (TokenType type : expected) {
            if (check(type)) {
                return consume();
            }
        }

        StringBuilder expectedStr = new StringBuilder();
        for (int i = 0; i < expected.length; i++) {
            if (i > 0) expectedStr.append(" or ");
            expectedStr.append(expected[i]);
        }

        throw new ParserException(
                "Unexpected token",
                current(),
                expectedStr.toString()
        );
    }

    /**
     * Verifica si hemos llegado al final del archivo.
     */
    public boolean isAtEnd() {
        return check(TokenType.EOF);
    }

    /**
     * Obtiene el token anterior.
     */
    public Token previous() {
        if (position > 0) {
            return tokens.get(position - 1);
        }
        return tokens.get(0);
    }

    /**
     * Guarda la posición actual (para backtracking).
     */
    public int mark() {
        return position;
    }

    /**
     * Restaura una posición guardada (para backtracking).
     */
    public void reset(int savedPosition) {
        this.position = savedPosition;
    }

    /**
     * Obtiene la posición actual.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Obtiene la lista completa de tokens.
     */
    public List<Token> getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return String.format("ParserContext{position=%d/%d, current=%s}",
                position, tokens.size(), current());
    }
}
