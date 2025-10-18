package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode.ObjectMemberNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import java.util.ArrayList;
import java.util.List;

public class ObjectLiteralParser extends Parser {
    private ExpressionParser expressionParser;

    private ExpressionParser getExpressionParser() {
        if (expressionParser == null) {
            expressionParser = new ExpressionParser();
        }
         return expressionParser;
    }

    public ObjectLiteralNode parse(ParserContext ctx) throws ParseException {
         Token leftBrace = ctx.getCurrentToken();

        if (leftBrace == null || leftBrace.getType() != TokenType.LEFT_BRACE) {
            throw new ParseException("Expected {");
        }
        ctx.advance();
        if (ctx.getCurrentToken() != null && ctx.getCurrentToken().getType() == TokenType.RIGHT_BRACE) {
            ctx.advance();
            return new ObjectLiteralNode(leftBrace);
        }
        List<ObjectMemberNode> members = new ArrayList<>();
        members.add(parseMember(ctx));
        while (ctx.getCurrentToken() != null && ctx.getCurrentToken().getType() == TokenType.COMMA) {
            ctx.advance(); // consume comma
            members.add(parseMember(ctx));
        }
        if (ctx.getCurrentToken() == null || ctx.getCurrentToken().getType() != TokenType.RIGHT_BRACE) {
            throw new ParseException("Expected }");
        }
        ctx.advance();
        ObjectMemberListNode memberList = new ObjectMemberListNode(leftBrace, members);
        return new ObjectLiteralNode(leftBrace, memberList);
    }


    private ObjectMemberNode parseMember(ParserContext ctx) throws ParseException {
        Token keyToken = ctx.getCurrentToken();

        if (keyToken == null || (keyToken.getType() != TokenType.STRING_LITERAL && keyToken.getType() != TokenType.IDENTIFIER)) {
            throw new ParseException("Expected property key");
        }

        String key;
        if (keyToken.getType() == TokenType.STRING_LITERAL) {
            String raw = keyToken.getValue();
            key = raw.substring(1, raw.length() - 1);
        } else {
            key = keyToken.getValue(); 
        }
        ctx.advance();
  
        if (ctx.getCurrentToken() == null || ctx.getCurrentToken().getType() != TokenType.COLON) {
            throw new ParseException("Expected :");
        }  
        ctx.advance();

        ExpressionNode value = getExpressionParser().parse(ctx);

        return new ObjectMemberNode(keyToken, key, value);
    }
}




