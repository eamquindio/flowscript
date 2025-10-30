package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.literales.IntegerLiteralNode;
import com.flowscript.sintactic.ParserContext;

public class IntegerLiteralParser extends Parser {
    public IntegerLiteralNode parse(ParserContext ctx) throws ParseException {
        Token tk = ctx.getCurrentToken();  
        if (tk == null || tk.getType() != TokenType.INTEGER_LITERAL) {
            throw new ParseException("Expected INTEGER");
        }
        ctx.advance(); 
        return new IntegerLiteralNode(tk);

    } 
}   
