package edu.eam.ingesoft.tlf.flowscript.parser.parsers.expressions;

import edu.eam.ingesoft.tlf.flowscript.lexer.*;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.LiteralNode;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.literals.LiteralParser;
import java.util.*;

public class ExpressionParser implements IParser<ExpressionNode> {
    private final LiteralParser literalParser = new LiteralParser();

    @Override
    public ExpressionNode parse(ParserContext context) {
        return parseLogicalOr(context);
    }

    // Expression ::= LogicalOrExpression
    private ExpressionNode parseLogicalOr(ParserContext context) {
        ExpressionNode left = parseLogicalAnd(context);

        while (context.match(TokenType.O)) {
            ExpressionNode right = parseLogicalAnd(context);
            left = new BinaryExpressionNode(BinaryExpressionNode.Operator.OR, left, right);
        }

        return left;
    }

    // LogicalAndExpression ::= EqualityExpression ('y' EqualityExpression)*
    private ExpressionNode parseLogicalAnd(ParserContext context) {
        ExpressionNode left = parseEquality(context);

        while (context.match(TokenType.Y)) {
            ExpressionNode right = parseEquality(context);
            left = new BinaryExpressionNode(BinaryExpressionNode.Operator.AND, left, right);
        }

        return left;
    }

    // EqualityExpression ::= RelationalExpression (('==' | '!=') RelationalExpression)*
    private ExpressionNode parseEquality(ParserContext context) {
        ExpressionNode left = parseRelational(context);

        while (true) {
            if (context.match(TokenType.EQUAL)) {
                ExpressionNode right = parseRelational(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.EQUAL, left, right);
            } else if (context.match(TokenType.NOT_EQUAL)) {
                ExpressionNode right = parseRelational(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.NOT_EQUAL, left, right);
            } else {
                break;
            }
        }

        return left;
    }

    // RelationalExpression ::= AdditiveExpression (('<' | '>' | '<=' | '>=') AdditiveExpression)*
    private ExpressionNode parseRelational(ParserContext context) {
        ExpressionNode left = parseAdditive(context);

        while (true) {
            if (context.match(TokenType.LESS_THAN)) {
                ExpressionNode right = parseAdditive(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.LESS_THAN, left, right);
            } else if (context.match(TokenType.GREATER_THAN)) {
                ExpressionNode right = parseAdditive(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.GREATER_THAN, left, right);
            } else if (context.match(TokenType.LESS_EQUAL)) {
                ExpressionNode right = parseAdditive(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.LESS_EQUAL, left, right);
            } else if (context.match(TokenType.GREATER_EQUAL)) {
                ExpressionNode right = parseAdditive(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.GREATER_EQUAL, left, right);
            } else {
                break;
            }
        }

        return left;
    }

    // AdditiveExpression ::= MultiplicativeExpression (('+' | '-') MultiplicativeExpression)*
    private ExpressionNode parseAdditive(ParserContext context) {
        ExpressionNode left = parseMultiplicative(context);

        while (true) {
            if (context.match(TokenType.PLUS)) {
                ExpressionNode right = parseMultiplicative(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.ADD, left, right);
            } else if (context.match(TokenType.MINUS)) {
                ExpressionNode right = parseMultiplicative(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.SUBTRACT, left, right);
            } else {
                break;
            }
        }

        return left;
    }

    // MultiplicativeExpression ::= UnaryExpression (('*' | '/' | '%') UnaryExpression)*
    private ExpressionNode parseMultiplicative(ParserContext context) {
        ExpressionNode left = parseUnary(context);

        while (true) {
            if (context.match(TokenType.MULTIPLY)) {
                ExpressionNode right = parseUnary(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.MULTIPLY, left, right);
            } else if (context.match(TokenType.DIVIDE)) {
                ExpressionNode right = parseUnary(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.DIVIDE, left, right);
            } else if (context.match(TokenType.MODULO)) {
                ExpressionNode right = parseUnary(context);
                left = new BinaryExpressionNode(BinaryExpressionNode.Operator.MODULO, left, right);
            } else {
                break;
            }
        }

        return left;
    }

    // UnaryExpression ::= ('no' | '-') UnaryExpression | PostfixExpression
    private ExpressionNode parseUnary(ParserContext context) {
        if (context.match(TokenType.NO)) {
            ExpressionNode operand = parseUnary(context);
            return new UnaryExpressionNode(UnaryExpressionNode.Operator.LOGICAL_NOT, operand);
        }

        if (context.match(TokenType.MINUS)) {
            ExpressionNode operand = parseUnary(context);
            return new UnaryExpressionNode(UnaryExpressionNode.Operator.NEGATE, operand);
        }

        return parsePostfix(context);
    }

    // PostfixExpression ::= PrimaryExpression (PostfixOperator)*
    // PostfixOperator ::= '.' IDENTIFIER | '[' Expression ']' | '(' ArgumentList? ')'
    private ExpressionNode parsePostfix(ParserContext context) {
        ExpressionNode expr = parsePrimary(context);

        while (true) {
            if (context.match(TokenType.DOT)) {
                String memberName = context.expect(TokenType.IDENTIFIER).getLexeme();
                expr = new MemberAccessNode(expr, memberName);
            } else if (context.match(TokenType.LEFT_BRACKET)) {
                ExpressionNode index = parse(context);
                context.expect(TokenType.RIGHT_BRACKET);
                expr = new IndexAccessNode(expr, index);
            } else if (context.match(TokenType.LEFT_PAREN)) {
                List<ExpressionNode> arguments = new ArrayList<>();
                if (!context.check(TokenType.RIGHT_PAREN)) {
                    do {
                        arguments.add(parse(context));
                    } while (context.match(TokenType.COMMA));
                }
                context.expect(TokenType.RIGHT_PAREN);
                expr = new FunctionCallNode(expr, arguments);
            } else {
                break;
            }
        }

        return expr;
    }

    // PrimaryExpression ::= Literal | IDENTIFIER | '(' Expression ')'
    private ExpressionNode parsePrimary(ParserContext context) {
        // Parenthesized expression
        if (context.match(TokenType.LEFT_PAREN)) {
            ExpressionNode expr = parse(context);
            context.expect(TokenType.RIGHT_PAREN);
            return expr;
        }

        // Identifier
        if (context.check(TokenType.IDENTIFIER)) {
            String name = context.consume().getLexeme();
            return new IdentifierNode(name);
        }

        // Literal
        return literalParser.parse(context);
    }
}
