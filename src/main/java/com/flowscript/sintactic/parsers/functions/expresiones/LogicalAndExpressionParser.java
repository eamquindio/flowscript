package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * AND lógico. Por ahora delega a igualdad.
 * TODO: implementar bucle AND (&&) si aplica.
 */
public class LogicalAndExpressionParser implements IParser<ExpressionNode> {

    private final EqualityExpressionParser equalityParser;

    public LogicalAndExpressionParser(IParser<ExpressionNode> root) {
        this.equalityParser = new EqualityExpressionParser(root);
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        return equalityParser.parse(context);
    }
}
