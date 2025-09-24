package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.parsers.functions.statements_basicos.ExpressionStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.IfStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.TryStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ThrowStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ReturnStatementParser;
import com.flowscript.sintactic.parsers.process.navegacion.GotoStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ForStatementParser;
import com.flowscript.sintactic.parsers.functions.statements_basicos.VariableDeclarationParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.BlockParser;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import java.util.List;
import java.util.ArrayList;

/**
 * Specialized parser for FlowScript statements.
 * Delegates to individual statement parsers following the "one class per grammar" architecture.
 */
public class StatementParser extends Parser {

    // Individual statement parsers
    private final ExpressionStatementParser expressionStatementParser;
    private final IfStatementParser ifStatementParser;
    private final TryStatementParser tryStatementParser;
    private final ThrowStatementParser throwStatementParser;
    private final ReturnStatementParser returnStatementParser;
    private final GotoStatementParser gotoStatementParser;
    private final ForStatementParser forStatementParser;
    private final VariableDeclarationParser variableDeclarationParser;
    private final BlockParser blockParser;

    public StatementParser(List<Token> tokens) {
        super(tokens);

        // Initialize individual parsers
        this.expressionStatementParser = new ExpressionStatementParser(tokens);
        this.ifStatementParser = new IfStatementParser(tokens);
        this.tryStatementParser = new TryStatementParser(tokens);
        this.throwStatementParser = new ThrowStatementParser(tokens);
        this.returnStatementParser = new ReturnStatementParser(tokens);
        this.gotoStatementParser = new GotoStatementParser(tokens);
        this.forStatementParser = new ForStatementParser(tokens);
        this.variableDeclarationParser = new VariableDeclarationParser(tokens);
        this.blockParser = new BlockParser(tokens);
    }

    /**
     * Parses any statement based on BNF:
     * Statement ::= ExpressionStatement | IfStatement | TryStatement | ThrowStatement
     *             | ReturnStatement | GotoStatement | ForStatement | VariableDeclaration | Block
     *
     * Delegates to individual parsers following the "one class per grammar" architecture.
     */
    public StatementNode parseStatement() throws ParseException {
        Token currentToken = getCurrentToken();

        if (currentToken.getValue().equals("si") || currentToken.getValue().equals("if")) {
            ifStatementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode result = ifStatementParser.parseIfStatement();
            syncTo(ifStatementParser.getCurrentToken(), ifStatementParser.getCurrentIndex());
            return result;

        } else if (currentToken.getValue().equals("intentar") || currentToken.getValue().equals("try")) {
            tryStatementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode result = tryStatementParser.parseTryStatement();
            syncTo(tryStatementParser.getCurrentToken(), tryStatementParser.getCurrentIndex());
            return result;

        } else if (currentToken.getValue().equals("lanzar") || currentToken.getValue().equals("throw")) {
            throwStatementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode result = throwStatementParser.parseThrowStatement();
            syncTo(throwStatementParser.getCurrentToken(), throwStatementParser.getCurrentIndex());
            return result;

        } else if (currentToken.getValue().equals("retornar") || currentToken.getValue().equals("return")) {
            returnStatementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode result = returnStatementParser.parseReturnStatement();
            syncTo(returnStatementParser.getCurrentToken(), returnStatementParser.getCurrentIndex());
            return result;

        } else if (currentToken.getValue().equals("go_to")) {
            gotoStatementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode result = gotoStatementParser.parseGotoStatement();
            syncTo(gotoStatementParser.getCurrentToken(), gotoStatementParser.getCurrentIndex());
            return result;

        } else if (currentToken.getValue().equals("para") || currentToken.getValue().equals("for")) {
            forStatementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode result = forStatementParser.parseForStatement();
            syncTo(forStatementParser.getCurrentToken(), forStatementParser.getCurrentIndex());
            return result;

        } else if (currentToken.getValue().equals("{")) {
            blockParser.syncTo(getCurrentToken(), getCurrentIndex());
            BlockNode result = blockParser.parseBlock();
            syncTo(blockParser.getCurrentToken(), blockParser.getCurrentIndex());
            return result;

        } else if (isVariableDeclaration()) {
            variableDeclarationParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode result = variableDeclarationParser.parseVariableDeclaration();
            syncTo(variableDeclarationParser.getCurrentToken(), variableDeclarationParser.getCurrentIndex());
            return result;

        } else {
            // Default to expression statement
            expressionStatementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode result = expressionStatementParser.parseExpressionStatement();
            syncTo(expressionStatementParser.getCurrentToken(), expressionStatementParser.getCurrentIndex());
            return result;
        }
    }

    /**
     * Parses statement lists based on BNF:
     * StatementList ::= Statement*
     */
    public List<StatementNode> parseStatementList() throws ParseException {
        List<StatementNode> statements = new ArrayList<>();

        while (getCurrentToken() != null &&
               !getCurrentToken().getValue().equals("}") &&
               getCurrentToken().getType() != TokenType.EOF) {
            statements.add(parseStatement());
        }

        return statements;
    }

    /**
     * Parses blocks by delegating to BlockParser.
     * Block ::= '{' StatementList '}'
     */
    public BlockNode parseBlock() throws ParseException {
        blockParser.syncTo(getCurrentToken(), getCurrentIndex());
        BlockNode result = blockParser.parseBlock();
        syncTo(blockParser.getCurrentToken(), blockParser.getCurrentIndex());
        return result;
    }

    /**
     * Checks if the current position looks like a variable declaration.
     */
    private boolean isVariableDeclaration() {
        return getCurrentToken() != null &&
               getCurrentToken().getType() == TokenType.IDENTIFIER &&
               peekToken(1) != null &&
               peekToken(1).getValue().equals("=");
    }
}