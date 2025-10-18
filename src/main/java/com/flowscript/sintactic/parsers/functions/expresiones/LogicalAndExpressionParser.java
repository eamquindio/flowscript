package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalAndExpressionNode;

/**
 * Parser para expresiones lógicas AND.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * LogicalAndExpression ::= EqualityExpression ( ( 'and' | 'y' ) EqualityExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 3: Expresiones - AND Lógico
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta tanto 'and' (inglés) como 'y' (español).
 * El operador es asociativo por la izquierda.
 *
 * @see LogicalAndExpressionNode
 */
public class LogicalAndExpressionParser implements IParser<LogicalAndExpressionNode> {

    @Override
    public LogicalAndExpressionNode parse(ParserContext context) throws Parser.ParseException {
        EqualityExpressionParser equalityExpressionParser = new EqualityExpressionParser();
        ExpressionNode expr = equalityExpressionParser.parse(context);
        LogicalAndExpressionNode root = new LogicalAndExpressionNode(expr.getToken(), expr);
        for (;;) {
            if (!context.checkValue("and") && !context.checkValue("y")) {
            break;
            }
            var op = context.consume();
            ExpressionNode right = equalityExpressionParser.parse(context);
            root.addOperand(op, right);
        }
        return root;
    }
}
