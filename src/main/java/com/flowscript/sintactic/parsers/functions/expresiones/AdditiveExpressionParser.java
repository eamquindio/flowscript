package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Aditiva (+, -). Por ahora delega a multiplicativa.
 * TODO: implementar suma/resta si aplica.
 */
public class AdditiveExpressionParser implements IParser<ExpressionNode> {

    private final MultiplicativeExpressionParser multiplicativeParser;

    public AdditiveExpressionParser(IParser<ExpressionNode> root) {
        this.multiplicativeParser = new MultiplicativeExpressionParser(root);
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        return multiplicativeParser.parse(context);
    }
}
