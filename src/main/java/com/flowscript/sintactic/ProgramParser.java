package com.flowscript.sintactic;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import java.util.List;
import java.util.ArrayList;

/**
 * Main parser for FlowScript programs.
 * Coordinates all specialized parsers to build complete AST from token stream.
 */
public class ProgramParser extends Parser {

    private final DeclarationParser declarationParser;

    public ProgramParser(List<Token> tokens) {
        super(tokens);
        this.declarationParser = new DeclarationParser(tokens);
    }

    /**
     * Parses the complete program based on BNF:
     * Program ::= Declaration*
     */
    @Override
    protected ProgramNode parseProgram() throws ParseException {
        List<DeclarationNode> declarations = new ArrayList<>();

        // Skip any initial comments
        while (currentToken != null && checkAny(TokenType.SINGLE_LINE_COMMENT, TokenType.MULTI_LINE_COMMENT)) {
            nextToken();
        }

        // Parse declarations until EOF
        while (currentToken != null && !check(TokenType.EOF)) {
            try {
                // Sync with declaration parser
                declarationParser.syncTo(getCurrentToken(), tokens.indexOf(getCurrentToken()));
                DeclarationNode declaration = declarationParser.parseDeclaration();
                syncFrom(declarationParser);

                declarations.add(declaration);

                // Skip optional semicolons between declarations
                if (check(TokenType.SEMICOLON)) {
                    nextToken();
                }

                // Skip comments
                while (currentToken != null && checkAny(TokenType.SINGLE_LINE_COMMENT, TokenType.MULTI_LINE_COMMENT)) {
                    nextToken();
                }

            } catch (ParseException e) {
                // Error recovery: skip to next declaration
                System.err.println("Parse error: " + e.getMessage());
                skipToRecovery();

                // Skip comments after recovery
                while (currentToken != null && checkAny(TokenType.SINGLE_LINE_COMMENT, TokenType.MULTI_LINE_COMMENT)) {
                    nextToken();
                }
            }
        }

        ProgramNode programNode = new ProgramNode();
        for (DeclarationNode declaration : declarations) {
            programNode.addDeclaration(declaration);
        }
        return programNode;
    }

    /**
     * Synchronizes this parser's position from another parser.
     */
    private void syncFrom(Parser otherParser) {
        this.currentToken = otherParser.getCurrentToken();
        // Find the current index
        for (int i = 0; i < tokens.size(); i++) {
            if (currentToken != null && tokens.get(i).equals(currentToken)) {
                this.currentIndex = i;
                break;
            }
        }
    }

    /**
     * Enhanced recovery that looks for declaration keywords.
     */
    @Override
    protected void skipToRecovery() {
        while (currentToken != null && currentToken.getType() != TokenType.EOF) {
            if (checkAny(TokenType.FUNCTION, TokenType.PROCESS, TokenType.IMPORT,
                        TokenType.IMPORT_JAR, TokenType.IDENTIFIER)) {
                // Don't consume the recovery token, let the next iteration handle it
                break;
            }
            nextToken();
        }
    }

    /**
     * Synchronizes the declaration parser's position.
     */
    private void syncDeclarationParser() {
        declarationParser.syncTo(getCurrentToken(), tokens.indexOf(getCurrentToken()));
    }
}