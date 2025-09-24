package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import java.util.List;
import java.util.ArrayList;

/**
 * Parser for function parameter lists.
 * Handles: param1: type1, param2: type2, ...
 */
public class ParameterListParser extends Parser {

    public ParameterListParser(List<Token> tokens) {
        super(tokens);
    }

    /**
     * Parses a parameter list.
     * Grammar: Parameter ( ',' Parameter )*
     */
    public List<ParameterNode> parseParameterList() throws ParseException {
        List<ParameterNode> parameters = new ArrayList<>();

        // Check for empty parameter list
        if (!check(TokenType.RIGHT_PAREN)) {
            // Parse first parameter
            parameters.add(parseParameter());

            // Parse remaining parameters
            while (check(TokenType.COMMA)) {
                consume(TokenType.COMMA);
                parameters.add(parseParameter());
            }
        }

        return parameters;
    }

    /**
     * Parses a single parameter.
     * Grammar: IDENTIFIER ':' Type
     */
    private ParameterNode parseParameter() throws ParseException {
        Token nameToken = consume(TokenType.IDENTIFIER);
        consume(TokenType.COLON);
        TypeNode type = parseType();

        return new ParameterNode(nameToken.getValue(), type);
    }

    /**
     * Parses a type.
     * Grammar: 'integer' | 'decimal' | 'boolean' | 'text' | 'list' | 'object' | 'void'
     */
    private TypeNode parseType() throws ParseException {
        if (checkAny(TokenType.INTEGER_TYPE, TokenType.DECIMAL_TYPE, TokenType.BOOLEAN_TYPE,
                    TokenType.TEXT_TYPE, TokenType.LIST_TYPE, TokenType.OBJECT_TYPE, TokenType.VOID)) {
            Token typeToken = getCurrentToken();
            nextToken();
            return new TypeNode(typeToken);
        }

        throw new ParseException("Expected type but found " + getCurrentToken().getType() +
                                " at line " + getCurrentToken().getLine());
    }

    /**
     * Synchronizes this parser's position with another parser.
     */
    public void syncTo(Token token, int index) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals(token)) {
                this.currentIndex = i;
                this.currentToken = token;
                break;
            }
        }
    }
}