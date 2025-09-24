package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.IfStatementNode;
import java.util.List;

/**
 * Parser for IfStatement grammar rule.
 * Grammar: IfStatement ::= 'if' Expression Statement ( 'else_if' Expression Statement )* ( 'else' Statement )?
 */
public class IfStatementParser extends Parser {

    private final ExpressionParser expressionParser;
    private final StatementParser statementParser;

    public IfStatementParser(List<Token> tokens) {
        super(tokens);
        this.expressionParser = new ExpressionParser(tokens);
        this.statementParser = new StatementParser(tokens);
    }

    public IfStatementNode parseIfStatement() throws ParseException {
        Token ifToken = consume(); // consume 'if' or 'si'

        // Parse condition
        expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
        ExpressionNode condition = expressionParser.parseExpression();
        syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

        // Parse then statement
        statementParser.syncTo(getCurrentToken(), getCurrentIndex());
        StatementNode thenStatement = statementParser.parseStatement();
        syncTo(statementParser.getCurrentToken(), statementParser.getCurrentIndex());

        IfStatementNode ifStmt = new IfStatementNode(ifToken, condition, thenStatement);

        // Parse else_if clauses
        while (getCurrentToken().getValue().equals("sino_si")) {
            Token elseIfToken = consume(); // consume 'else_if' or 'sino_si'

            expressionParser.syncTo(getCurrentToken(), getCurrentIndex());
            ExpressionNode elseIfCondition = expressionParser.parseExpression();
            syncTo(expressionParser.getCurrentToken(), expressionParser.getCurrentIndex());

            statementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode elseIfStatement = statementParser.parseStatement();
            syncTo(statementParser.getCurrentToken(), statementParser.getCurrentIndex());

            ifStmt.addElseIfClause(elseIfCondition, elseIfStatement);
        }

        // Parse optional else clause
        if (getCurrentToken().getValue().equals("sino")) {
            consume(); // consume 'else' or 'sino'

            statementParser.syncTo(getCurrentToken(), getCurrentIndex());
            StatementNode elseStatement = statementParser.parseStatement();
            syncTo(statementParser.getCurrentToken(), statementParser.getCurrentIndex());

            ifStmt.setElseStatement(elseStatement);
        }

        return ifStmt;
    }
}