package edu.eam.ingesoft.tlf.flowscript.parser.parsers.statements;

import edu.eam.ingesoft.tlf.flowscript.lexer.*;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.expressions.ExpressionParser;

public class StatementParser implements IParser<StatementNode> {
    private final ExpressionParser expressionParser = new ExpressionParser();

    @Override
    public StatementNode parse(ParserContext context) {
        // IfStatement
        if (context.check(TokenType.SI)) {
            IfStatementParser ifStatementParser = new IfStatementParser();
            return ifStatementParser.parse(context);
        }

        // ForStatement
        if (context.check(TokenType.PARA)) {
            ForStatementParser forStatementParser = new ForStatementParser();
            return forStatementParser.parse(context);
        }

        // GotoStatement (only valid in process context)
        if (context.check(TokenType.IR_A)) {
            return parseGotoStatement(context);
        }

        // ReturnStatement
        if (context.check(TokenType.RETORNAR)) {
            return parseReturnStatement(context);
        }

        // Block
        if (context.check(TokenType.LEFT_BRACE)) {
            BlockParser blockParser = new BlockParser();
            return blockParser.parse(context);
        }

        // Try to determine if it's a variable declaration or assignment/expression
        int savedPos = context.mark();
        if (context.check(TokenType.IDENTIFIER)) {
            context.advance(); // consume identifier

            if (context.check(TokenType.ASSIGN)) {
                // Could be variable declaration or assignment
                context.reset(savedPos);
                return parseVariableDeclarationOrAssignment(context);
            }

            // It's an expression statement
            context.reset(savedPos);
            return parseExpressionStatement(context);
        }

        // Default: expression statement
        return parseExpressionStatement(context);
    }

    private ReturnStatementNode parseReturnStatement(ParserContext context) {
        context.expect(TokenType.RETORNAR);

        ExpressionNode value = null;
        if (!context.check(TokenType.RIGHT_BRACE) && !context.isAtEnd()) {
            // Check if there's an expression to return
            int saved = context.mark();
            try {
                value = expressionParser.parse(context);
            } catch (ParserException e) {
                // No expression, just return
                context.reset(saved);
            }
        }

        return new ReturnStatementNode(value);
    }

    private StatementNode parseVariableDeclarationOrAssignment(ParserContext context) {
        ExpressionNode target = expressionParser.parse(context);

        if (!context.match(TokenType.ASSIGN)) {
            // Not an assignment, must be expression statement
            return new ExpressionStatementNode(target);
        }

        ExpressionNode value = expressionParser.parse(context);

        // Check if target is a simple identifier (variable declaration)
        if (target instanceof IdentifierNode) {
            String name = ((IdentifierNode) target).getName();
            return new VariableDeclarationStatementNode(name, value);
        }

        // Otherwise, it's an assignment to a member/index
        return new AssignmentStatementNode(target, value);
    }

    private ExpressionStatementNode parseExpressionStatement(ParserContext context) {
        ExpressionNode expression = expressionParser.parse(context);
        return new ExpressionStatementNode(expression);
    }

    private GotoStatementNode parseGotoStatement(ParserContext context) {
        context.expect(TokenType.IR_A);
        Token targetToken = context.expect(TokenType.IDENTIFIER);
        String targetName = targetToken.getLexeme();
        return new GotoStatementNode(targetName);
    }
}
