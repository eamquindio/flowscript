package com.flowscript.sintactic.parsers.functions.literales;
import com.flowscript.sintactic.Parser;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ParserContext;

public class LiteralParser extends Parser {
    public ExpressionNode parse(ParserContext ctx) throws ParseException {
        Token tk = ctx.getCurrentToken();
        
        if (tk == null) {
            throw new ParseException("Unexpected end of input");

        }
        TokenType tt = tk.getType();
        
        if (tt == TokenType.INTEGER_LITERAL) {
            IntegerLiteralParser ip = new IntegerLiteralParser();
            return ip.parse(ctx);
        } else if (tt == TokenType.DECIMAL_LITERAL) {
            DecimalLiteralParser dp = new DecimalLiteralParser();
            return dp.parse(ctx);
        } else if (tt == TokenType.STRING_LITERAL) {
            StringLiteralParser sp = new StringLiteralParser();
            return sp.parse(ctx);
        } else if (tt == TokenType.TRUE || tt == TokenType.FALSE) {
            BooleanLiteralParser bp = new BooleanLiteralParser();
            return bp.parse(ctx);
        } else if (tt == TokenType.NULL) {
            NullLiteralParser np = new NullLiteralParser();
            return np.parse(ctx);
        } else if (tt == TokenType.LEFT_BRACKET) {
            ListLiteralParser lp = new ListLiteralParser();
            return lp.parse(ctx);
        } else if (tt == TokenType.LEFT_BRACE) {
            ObjectLiteralParser op = new ObjectLiteralParser();
            return op.parse(ctx);
        } else {
            throw new ParseException("Expected literal but got " + tt);
        }

    }
}  
