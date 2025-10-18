package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Multiplicativa (*, /, %). Por ahora delega a unaria.
 * TODO: implementar *, /, % si aplica.
 */
public class MultiplicativeExpressionParser implements IParser<ExpressionNode> {

    private final UnaryExpressionParser unaryParser;

    public MultiplicativeExpressionParser(IParser<ExpressionNode> root) {
        this.unaryParser = new UnaryExpressionParser(root);
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        return unaryParser.parse(context);
    }
}
