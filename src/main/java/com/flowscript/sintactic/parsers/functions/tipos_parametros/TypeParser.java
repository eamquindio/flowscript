package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * Parser for Type grammar rule.
 * Grammar: Type ::= 'integer' | 'decimal' | 'boolean' | 'text' | 'list' | 'object' | 'void'
 */
public class TypeParser extends Parser {

    private static final Set<String> VALID_TYPES = new HashSet<>(Arrays.asList(
        "entero", "integer",      // Integer type
        "decimal",                // Decimal type
        "booleano", "boolean",    // Boolean type
        "texto", "text",          // Text type
        "lista", "list",          // List type
        "objeto", "object",       // Object type
        "vacio", "void"           // Void type
    ));

    public TypeParser(List<Token> tokens) {
        super(tokens);
    }

    public TypeNode parseType() throws ParseException {
        Token typeToken = getCurrentToken();

        if (typeToken == null || !VALID_TYPES.contains(typeToken.getValue())) {
            throw new ParseException("Expected valid type, but found: " +
                (typeToken != null ? typeToken.getValue() : "null"));
        }

        consume(); // consume the type token

        // Create a new token with normalized type name if needed
        String normalizedType = normalizeType(typeToken.getValue());
        if (!normalizedType.equals(typeToken.getValue())) {
            // If normalization occurred, we need to create the node with the original token
            // but the TypeNode will get the type name from the token value
            return new TypeNode(typeToken);
        }

        return new TypeNode(typeToken);
    }

    /**
     * Normalizes Spanish type names to English equivalents.
     */
    private String normalizeType(String type) {
        switch (type) {
            case "entero": return "integer";
            case "booleano": return "boolean";
            case "texto": return "text";
            case "lista": return "list";
            case "objeto": return "object";
            case "vacio": return "void";
            default: return type; // Already in English
        }
    }
}