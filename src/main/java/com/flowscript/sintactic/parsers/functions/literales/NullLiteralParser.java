package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.literales.NullLiteralNode;
import com.flowscript.sintactic.ParserContext;

public class NullLiteralParser extends Parser {
    public NullLiteralNode parse(ParserContext ctx) throws ParseException {
        Token tk = ctx.getCurrentToken();
        if (tk == null || tk.getType() != TokenType.NULL) {
            throw new ParseException("Expected NULL");
         }
        ctx.advance();
        return new NullLiteralNode(tk);
     }
}




