package com.flowscript.sintactic.parsers.functions.expresiones;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

public class AdditiveExpressionParser implements IParser<ExpressionNode> {
    private MultiplicativeExpressionParser multiplicativeParser;
    
    private MultiplicativeExpressionParser getMultiplicativeParser() {
        if (multiplicativeParser == null) {
            multiplicativeParser = new MultiplicativeExpressionParser();
        }
        return multiplicativeParser;
    } 
     
    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        ExpressionNode left = getMultiplicativeParser().parse(context);
        AdditiveExpressionNode additive = new AdditiveExpressionNode(
            context.getCurrentToken() != null ? context.getCurrentToken() : context.peek(-1),
            left
        );
              
        while (context.getCurrentToken() != null) {
            TokenType type = context.getCurrentToken().getType();
            
            if (type  == TokenType.PLUS || type == TokenType.MINUS) {
                Token operator = context.consume();
                ExpressionNode right = getMultiplicativeParser().parse(context);
                additive.addOperand(operator, right);
            } else {
                break;
            }
        }
        
        return additive.getSimplified();
    }
}


