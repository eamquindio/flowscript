package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import java.util.ArrayList;
import java.util.List;

public class ListLiteralParser extends Parser {
    private ExpressionParser expressionParser;

    private ExpressionParser getExpressionParser() {
        if (expressionParser == null) {
            expressionParser = new ExpressionParser();

        }
         return expressionParser;
    }

    public ListLiteralNode parse(ParserContext ctx) throws ParseException {
        Token leftBracket = ctx.getCurrentToken();
        if (leftBracket == null || leftBracket.getType() != TokenType.LEFT_BRACKET) {
            throw new ParseException("Expected [");
        }
        ctx.advance();

        if (ctx.getCurrentToken() != null && ctx.getCurrentToken().getType() == TokenType.RIGHT_BRACKET) {
            ctx.advance();
             return new ListLiteralNode(leftBracket);
        }

        List<ExpressionNode> expressions = new ArrayList<>();
        expressions.add(getExpressionParser().parse(ctx));
        while (ctx.getCurrentToken() != null && ctx.getCurrentToken().getType() == TokenType.COMMA) {
            ctx.advance();
            expressions.add(getExpressionParser().parse(ctx));
        }

        if (ctx.getCurrentToken() == null || ctx.getCurrentToken().getType() != TokenType.RIGHT_BRACKET) {
            throw new ParseException("Expected ]");
        }
        ctx.advance();

        ExpressionListNode expressionList = new ExpressionListNode(leftBracket, expressions);
        return new ListLiteralNode(leftBracket, expressionList);
     }
}  

