package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.literales.StringLiteralNode;
import com.flowscript.sintactic.ParserContext;

public class StringLiteralParser extends Parser {   
    public StringLiteralNode parse(ParserContext ctx) throws ParseException {
        Token tk = ctx.getCurrentToken();
        if (tk == null || tk.getType() != TokenType.STRING_LITERAL) {
            throw new ParseException("Expected STRING");
        }
        ctx.advance();
         return new StringLiteralNode(tk);
      }
}  
