package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
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
        var left = new EqualityExpressionParser().parse(context);
        var node = new LogicalAndExpressionNode(left.getToken(), left);
        while (context.checkValue("and") || context.checkValue("y")) {
            Token op = context.consume();
            var otro = new EqualityExpressionParser().parse(context);
            node.addOperand(op, otro);
        }

        var tmp = node.getSimplified();
        if (tmp != null){
            if (tmp.getClass() == LogicalAndExpressionNode.class){
                return (LogicalAndExpressionNode) tmp;
            } else {
                return new LogicalAndExpressionNode(tmp.getToken(), tmp);
            }
        }
        return node;
    }
}
