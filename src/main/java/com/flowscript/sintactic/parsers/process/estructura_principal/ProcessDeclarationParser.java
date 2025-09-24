package com.flowscript.sintactic.parsers.process.estructura_principal;

import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import java.util.List;

/**
 * Parser for process declarations.
 * Handles: process ProcessName { ... }
 */
public class ProcessDeclarationParser extends Parser {

    public ProcessDeclarationParser(List<Token> tokens) {
        super(tokens);
    }

    /**
     * Parses a process declaration.
     * Grammar: 'process' IDENTIFIER '{' ProcessBody '}'
     */
    public ProcessDeclarationNode parseProcessDeclaration() throws ParseException {
        Token processToken = consume(TokenType.PROCESS);
        Token nameToken = consume(TokenType.IDENTIFIER);
        String processName = nameToken.getValue();

        consume(TokenType.LEFT_BRACE);

        // For now, we'll skip parsing the process body
        // In a complete implementation, this would parse the process nodes and flow
        int braceCount = 1;
        while (braceCount > 0 && getCurrentToken() != null) {
            Token token = getCurrentToken();
            if (token.getType() == TokenType.LEFT_BRACE) {
                braceCount++;
            } else if (token.getType() == TokenType.RIGHT_BRACE) {
                braceCount--;
            }
            if (braceCount > 0) {
                nextToken();
            }
        }

        consume(TokenType.RIGHT_BRACE);

        return new ProcessDeclarationNode(processToken, processName);
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