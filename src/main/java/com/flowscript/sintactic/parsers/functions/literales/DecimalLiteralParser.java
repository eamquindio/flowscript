package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.literales.DecimalLiteralNode;
import com.flowscript.sintactic.ParserContext;

public class DecimalLiteralParser extends Parser {  
    public DecimalLiteralNode parse(ParserContext ctx) throws ParseException {
        Token  tk = ctx.getCurrentToken();
        if (tk == null || tk.getType() != TokenType.DECIMAL_LITERAL) {
            throw new ParseException("Expected DECIMAL");
        }
        ctx.advance();
        return new DecimalLiteralNode(tk);
 
    }

}  
