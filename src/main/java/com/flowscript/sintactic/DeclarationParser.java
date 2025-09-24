package com.flowscript.sintactic;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ImportDeclarationNode;
import com.flowscript.sintactic.parsers.process.estructura_principal.ProcessDeclarationParser;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.FunctionDeclarationParser;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import java.util.List;

/**
 * Specialized parser for FlowScript declarations.
 * Handles ImportDeclaration, FunctionDeclaration, ProcessDeclaration, and VariableDeclaration
 * according to BNF grammar.
 */
public class DeclarationParser extends Parser {

    private final ProcessDeclarationParser processParser;
    private final FunctionDeclarationParser functionParser;

    public DeclarationParser(List<Token> tokens) {
        super(tokens);
        this.processParser = new ProcessDeclarationParser(tokens);
        this.functionParser = new FunctionDeclarationParser(tokens);
    }

    /**
     * Parses any declaration based on BNF:
     * Declaration ::= ImportDeclaration | FunctionDeclaration | ProcessDeclaration | VariableDeclaration
     */
    public DeclarationNode parseDeclaration() throws ParseException {
        if (check(TokenType.IMPORT)) {
            return parseImportDeclaration();
        } else if (check(TokenType.IMPORT_JAR)) {
            return parseImportJarDeclaration();
        } else if (check(TokenType.FUNCTION)) {
            return parseFunctionDeclaration();
        } else if (check(TokenType.PROCESS)) {
            return parseProcessDeclaration();
        } else {
            // Try to parse as variable declaration
            return parseVariableDeclaration();
        }
    }

    /**
     * Parses import declarations based on BNF:
     * ImportDeclaration ::= 'import' STRING_LITERAL ( 'as' IDENTIFIER )?
     */
    private ImportDeclarationNode parseImportDeclaration() throws ParseException {
        Token importToken = consume(TokenType.IMPORT);
        Token pathToken = consume(TokenType.STRING_LITERAL);
        String path = pathToken.getValue();

        String alias = null;
        if (check(TokenType.AS)) {
            consume(TokenType.AS);
            Token aliasToken = consume(TokenType.IDENTIFIER);
            alias = aliasToken.getValue();
        }

        return new ImportDeclarationNode(importToken, false, path, alias);
    }

    /**
     * Parses import jar declarations based on BNF:
     * ImportDeclaration ::= 'import_jar' STRING_LITERAL 'as' IDENTIFIER
     */
    private ImportDeclarationNode parseImportJarDeclaration() throws ParseException {
        Token importJarToken = consume(TokenType.IMPORT_JAR);
        Token pathToken = consume(TokenType.STRING_LITERAL);
        String path = pathToken.getValue();

        consume(TokenType.AS);
        Token aliasToken = consume(TokenType.IDENTIFIER);
        String alias = aliasToken.getValue();

        return new ImportDeclarationNode(importJarToken, true, path, alias);
    }

    /**
     * Parses function declarations using FunctionDeclarationParser.
     */
    private FunctionDeclarationNode parseFunctionDeclaration() throws ParseException {
        functionParser.syncTo(getCurrentToken(), tokens.indexOf(getCurrentToken()));
        FunctionDeclarationNode functionNode = functionParser.parseFunctionDeclaration();
        syncFrom(functionParser);
        return functionNode;
    }


    /**
     * Parses process declarations using ProcessDeclarationParser.
     */
    private ProcessDeclarationNode parseProcessDeclaration() throws ParseException {
        processParser.syncTo(getCurrentToken(), tokens.indexOf(getCurrentToken()));
        ProcessDeclarationNode processNode = processParser.parseProcessDeclaration();
        syncFrom(processParser);
        return processNode;
    }

    /**
     * Parses variable declarations based on BNF:
     * VariableDeclaration ::= IDENTIFIER '=' Expression
     */
    private DeclarationNode parseVariableDeclaration() throws ParseException {
        Token nameToken = consume(TokenType.IDENTIFIER);
        consume(TokenType.ASSIGN);

        // For now, create a simple variable declaration node
        // This would need to be integrated with ExpressionParser for the right side
        Token valueToken = getCurrentToken();
        nextToken(); // consume the value token for now

        // This is a placeholder implementation
        // In a complete implementation, this should parse the expression properly
        // For now create a simple variable declaration - this should be a proper VariableDeclarationNode
        return new ImportDeclarationNode(nameToken, false, nameToken.getValue(), null);
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