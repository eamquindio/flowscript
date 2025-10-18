package com.flowscript.sintactic.parsers.functions.expresiones;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

public class MultiplicativeExpressionParser implements IParser<ExpressionNode> {
    private PrimaryExpressionParser primaryParser;
         
    private PrimaryExpressionParser getPrimaryParser() {
        if (primaryParser == null) {
             primaryParser = new PrimaryExpressionParser();
        }
        return primaryParser;
    }
    
    @Override  
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        ExpressionNode left = getPrimaryParser().parse(context);
        MultiplicativeExpressionNode multiplicative = new MultiplicativeExpressionNode(
            context.getCurrentToken() != null ? context.getCurrentToken() : context.peek(-1),
            left
        ); 
        
        while (context.getCurrentToken() != null) {
            TokenType type = context.getCurrentToken().getType();
            if (type == TokenType.MULTIPLY || type == TokenType.DIVIDE || type == TokenType.MODULO) {
                Token operator = context.consume();
                ExpressionNode right = getPrimaryParser().parse(context);
                multiplicative.addOperand(operator, right);
            } else {
                break;
            }

        }
        
        return multiplicative.getSimplified();
    }


}
