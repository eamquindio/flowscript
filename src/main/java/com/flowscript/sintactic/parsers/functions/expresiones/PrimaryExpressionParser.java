package com.flowscript.sintactic.parsers.functions.expresiones;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.IdentifierNode;
import com.flowscript.sintactic.ast.functions.expresiones.PrimaryExpressionNode;
import com.flowscript.sintactic.parsers.functions.literales.LiteralParser;

public class PrimaryExpressionParser implements IParser<ExpressionNode> {
    private LiteralParser literalParser;
    
    private LiteralParser getLiteralParser() {
         if (literalParser == null) {
            literalParser = new LiteralParser();
        }
        return literalParser;
    } 

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input");
        }
        
        TokenType type = current.getType();
        if (type == TokenType.INTEGER_LITERAL || type == TokenType.DECIMAL_LITERAL ||
            type == TokenType.STRING_LITERAL || type == TokenType.TRUE || 
            type == TokenType.FALSE || type == TokenType.NULL ||
            type == TokenType.LEFT_BRACKET || type == TokenType.LEFT_BRACE) {
            
            return getLiteralParser().parse(context);
        }

        if (type == TokenType.IDENTIFIER) {
            Token idToken = context.consume();
            return new IdentifierNode(idToken);
        }
        
        if (type == TokenType.LEFT_PAREN) {
            Token leftParen = context.consume(); // consume '('
            ExpressionParser exprParser = new ExpressionParser();
            ExpressionNode expr = exprParser.parse(context);
            context.consume(TokenType.RIGHT_PAREN); // consume ')'
            return new PrimaryExpressionNode.ParenthesizedExpressionNode(leftParen, expr);
        }
        
        throw new Parser.ParseException("Expected primary expression but got " + type);
    }
}



