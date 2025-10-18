package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Root de expresiones: delega al nivel de menor precedencia (OR).
 */
public class ExpressionParser implements IParser<ExpressionNode> {

    private final LogicalOrExpressionParser logicalOrParser;

    public ExpressionParser() {
        this.logicalOrParser = new LogicalOrExpressionParser(this);
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        return logicalOrParser.parse(context);
    }
}
