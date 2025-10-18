package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * OR lógico. Por ahora delega a AND.
 * TODO: implementar bucle OR (||) si tu gramática lo requiere.
 */
public class LogicalOrExpressionParser implements IParser<ExpressionNode> {

    private final LogicalAndExpressionParser andParser;

    public LogicalOrExpressionParser(IParser<ExpressionNode> root) {
        this.andParser = new LogicalAndExpressionParser(root);
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        return andParser.parse(context);
    }
}
