package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Relacional (<, <=, >, >=). Por ahora delega a aditiva.
 * TODO: implementar operadores relacionales.
 */
public class RelationalExpressionParser implements IParser<ExpressionNode> {

    private final AdditiveExpressionParser additiveParser;

    public RelationalExpressionParser(IParser<ExpressionNode> root) {
        this.additiveParser = new AdditiveExpressionParser(root);
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        return additiveParser.parse(context);
    }
}
