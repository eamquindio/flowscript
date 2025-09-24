package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import java.util.List;

/**
 * Parser for Parameter grammar rule.
 * Grammar: Parameter ::= IDENTIFIER ':' Type
 */
public class ParameterParser extends Parser {

    private final TypeParser typeParser;

    public ParameterParser(List<Token> tokens) {
        super(tokens);
        this.typeParser = new TypeParser(tokens);
    }

    public ParameterNode parseParameter() throws ParseException {
        // Expect identifier
        if (getCurrentToken().getType() != TokenType.IDENTIFIER) {
            throw new ParseException("Expected parameter name identifier");
        }
        Token identifierToken = consume();

        // Expect ':'
        if (!":".equals(getCurrentToken().getValue())) {
            throw new ParseException("Expected ':' after parameter name");
        }
        consume(); // consume ':'

        // Parse type
        typeParser.syncTo(getCurrentToken(), getCurrentIndex());
        TypeNode type = typeParser.parseType();
        syncTo(typeParser.getCurrentToken(), typeParser.getCurrentIndex());

        return new ParameterNode(identifierToken.getValue(), type);
    }
}