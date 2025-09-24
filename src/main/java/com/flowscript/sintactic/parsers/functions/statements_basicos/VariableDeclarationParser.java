package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.statements_basicos.VariableDeclarationStatementNode;
import java.util.List;

/**
 * Parser for VariableDeclaration grammar rule.
 * Grammar: VariableDeclaration ::= IDENTIFIER '=' Expression
 */
public class VariableDeclarationParser extends Parser {

    private final ExpressionParser expressionParser;

    public VariableDeclarationParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
    }

    public VariableDeclarationStatementNode parseVariableDeclaration() throws ParseException {
        // Expect identifier
        if (getCurrentToken().getType() != TokenType.IDENTIFIER) {
            throw new ParseException("Expected variable identifier");
        }
        Token identifierToken = consume();

        // Expect '='
        if (!getCurrentToken().getValue().equals("=")) {
            throw new ParseException("Expected '=' after variable identifier");
        }
        consume(); // consume '='

        // Parse initialization expression
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionNode expression = expressionParser.parseExpression();
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        return new VariableDeclarationStatementNode(identifierToken, identifierToken.getValue(), expression);
    }
}