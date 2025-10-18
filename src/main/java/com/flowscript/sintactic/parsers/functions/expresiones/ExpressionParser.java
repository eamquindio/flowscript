package com.flowscript.sintactic.parsers.functions.expresiones;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

public class ExpressionParser implements IParser<ExpressionNode> {
    private AdditiveExpressionParser additiveParser;
    
    private AdditiveExpressionParser getAdditiveParser() {
        if (additiveParser == null) {
            additiveParser = new AdditiveExpressionParser();
        }

        return additiveParser;
    }   
    
    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        ExpressionNode expr = getAdditiveParser().parse(context);
        
        if (expr instanceof com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode) {
            return ((com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode) expr).getSimplified();
        }

        if (expr instanceof com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode) {
            return ((com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode) expr).getSimplified();
        }
        return expr;
    }
}    
