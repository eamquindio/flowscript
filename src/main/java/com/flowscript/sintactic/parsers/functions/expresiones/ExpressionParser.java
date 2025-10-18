package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

/**
 * Parser principal para manejar expresiones.
 *
 * Básicamente arranca desde la parte más general (OR lógico)
 * y deja que los demás parsers se encarguen de lo demás:
 * AND, comparaciones, sumas, etc.
 */
public class ExpressionParser implements IParser<ExpressionNode> {

    private final LogicalOrExpressionParser logicalOrParser;

    public ExpressionParser() {
        this.logicalOrParser = new LogicalOrExpressionParser();
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Entrada principal para analizar cualquier tipo de expresión
        return logicalOrParser.parse(context);
    }
}
