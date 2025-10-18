package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.literales.BooleanLiteralNode;
import com.flowscript.sintactic.ParserContext;

public class BooleanLiteralParser extends Parser {
    public BooleanLiteralNode parse(ParserContext ctx) throws ParseException {
        Token tk = ctx.getCurrentToken();
        if (tk == null || (tk.getType() != TokenType.TRUE && tk.getType() != TokenType.FALSE)) {
             throw new ParseException("Expected BOOLEAN");
        }  
        ctx.advance();
        return new BooleanLiteralNode(tk);
    }
}    

  
