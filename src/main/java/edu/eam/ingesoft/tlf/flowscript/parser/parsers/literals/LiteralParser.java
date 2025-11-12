package edu.eam.ingesoft.tlf.flowscript.parser.parsers.literals;

import edu.eam.ingesoft.tlf.flowscript.lexer.*;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.expressions.ExpressionParser;
import java.util.*;

public class LiteralParser implements IParser<LiteralNode> {

    @Override
    public LiteralNode parse(ParserContext context) {
        Token current = context.current();

        // INTEGER_LITERAL
        if (context.match(TokenType.INTEGER_LITERAL)) {
            long value = Long.parseLong(current.getLexeme());
            return new IntegerLiteralNode(value);
        }

        // DECIMAL_LITERAL
        if (context.match(TokenType.DECIMAL_LITERAL)) {
            double value = Double.parseDouble(current.getLexeme());
            return new DecimalLiteralNode(value);
        }

        // BOOLEAN_LITERAL
        if (context.match(TokenType.VERDADERO)) {
            return new BooleanLiteralNode(true);
        }
        if (context.match(TokenType.FALSO)) {
            return new BooleanLiteralNode(false);
        }

        // STRING_LITERAL
        if (context.match(TokenType.STRING_LITERAL)) {
            String value = current.getLexeme();
            // Remove quotes
            value = value.substring(1, value.length() - 1);
            return new StringLiteralNode(value);
        }

        // NULL
        if (context.match(TokenType.NULO)) {
            return new NullLiteralNode();
        }

        // LIST_LITERAL
        if (context.check(TokenType.LEFT_BRACKET)) {
            return parseListLiteral(context);
        }

        // OBJECT_LITERAL
        if (context.check(TokenType.LEFT_BRACE)) {
            return parseObjectLiteral(context);
        }

        throw new ParserException("Expected literal", context.current());
    }

    private ListLiteralNode parseListLiteral(ParserContext context) {
        context.expect(TokenType.LEFT_BRACKET);
        List<ExpressionNode> elements = new ArrayList<>();

        if (!context.check(TokenType.RIGHT_BRACKET)) {
            ExpressionParser exprParser = new ExpressionParser();
            do {
                elements.add(exprParser.parse(context));
            } while (context.match(TokenType.COMMA));
        }

        context.expect(TokenType.RIGHT_BRACKET);
        return new ListLiteralNode(elements);
    }

    private ObjectLiteralNode parseObjectLiteral(ParserContext context) {
        context.expect(TokenType.LEFT_BRACE);
        List<ObjectLiteralNode.ObjectMember> members = new ArrayList<>();

        if (!context.check(TokenType.RIGHT_BRACE)) {
            ExpressionParser exprParser = new ExpressionParser();
            do {
                String key = context.expect(TokenType.IDENTIFIER).getLexeme();
                context.expect(TokenType.COLON);
                ExpressionNode value = exprParser.parse(context);
                members.add(new ObjectLiteralNode.ObjectMember(key, value));
            } while (context.match(TokenType.COMMA));
        }

        context.expect(TokenType.RIGHT_BRACE);
        return new ObjectLiteralNode(members);
    }
}
