package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Igualdad (==, !=). Por ahora delega a relacional.
 * TODO: implementar comparación si tu AST la soporta.
 */
public class EqualityExpressionParser implements IParser<ExpressionNode> {

    private final RelationalExpressionParser relationalParser;

    public EqualityExpressionParser(IParser<ExpressionNode> root) {
        this.relationalParser = new RelationalExpressionParser(root);
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        return relationalParser.parse(context);
    }
}
