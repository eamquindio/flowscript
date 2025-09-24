package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;
import com.flowscript.sintactic.parsers.functions.tipos_parametros.ParameterListParser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import java.util.List;

/**
 * Parser for function declarations.
 * Handles: function name(param: type, ...) -> returnType { ... }
 */
public class FunctionDeclarationParser extends Parser {
    private final ParameterListParser parameterParser;
    private final StatementParser statementParser;

    public FunctionDeclarationParser(List<Token> tokens) {
        super(tokens);
        this.parameterParser = new ParameterListParser(tokens);
        this.statementParser = new StatementParser(tokens);
    }

    /**
     * Parses a function declaration.
     * Grammar: 'function' IDENTIFIER '(' ParameterList? ')' ( '->' Type )? Block
     */
    public FunctionDeclarationNode parseFunctionDeclaration() throws ParseException {
        Token functionToken = consume(TokenType.FUNCTION);
        Token nameToken = consume(TokenType.IDENTIFIER);
        String functionName = nameToken.getValue();

        consume(TokenType.LEFT_PAREN);

        // Parse parameters
        parameterParser.syncTo(getCurrentToken(), tokens.indexOf(getCurrentToken()));
        List<ParameterNode> parameters = parameterParser.parseParameterList();
        syncFrom(parameterParser);

        consume(TokenType.RIGHT_PAREN);

        // Parse optional return type
        TypeNode returnType = null;
        if (check(TokenType.ARROW)) {
            consume(TokenType.ARROW);
            returnType = parseType();
        }

        // Parse function body
        statementParser.syncTo(getCurrentToken(), tokens.indexOf(getCurrentToken()));
        BlockNode body = statementParser.parseBlock();
        syncFrom(statementParser);

        return new FunctionDeclarationNode(functionToken, functionName, parameters, returnType, body);
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

    /**
     * Synchronizes this parser's position from another parser.
     */
    private void syncFrom(Parser otherParser) {
        this.currentToken = otherParser.getCurrentToken();
        // Find the current index
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals(currentToken)) {
                this.currentIndex = i;
                break;
            }
        }
    }
}