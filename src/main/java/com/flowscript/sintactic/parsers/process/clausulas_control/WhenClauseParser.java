package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para cláusulas when en gateways exclusivos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * WhenClause ::= 'when' Expression '->' IDENTIFIER
 * </pre>
 */
public class WhenClauseParser implements IParser<WhenClauseNode> {

    private final ExpressionParser expressionParser;

    public WhenClauseParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public WhenClauseNode parse(ParserContext context) throws Parser.ParseException {

        context.consume(TokenType.WHEN);

        ExpressionNode condition = expressionParser.parse(context);

        context.consume(TokenType.ARROW);

        Token targetToken = context.consume(TokenType.IDENTIFIER);

        return new WhenClauseNode(condition, targetToken.getValue());
    }
}

