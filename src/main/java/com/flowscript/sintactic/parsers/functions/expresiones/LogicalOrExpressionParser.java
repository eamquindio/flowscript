package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode;

/**
 * Parser para expresiones lógicas OR.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * LogicalOrExpression ::= LogicalAndExpression ( ( 'or' | 'o' ) LogicalAndExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 2: Expresiones - OR Lógico
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta tanto 'or' (inglés) como 'o' (español).
 * El operador es asociativo por la izquierda.
 *
 * @see LogicalOrExpressionNode
 */
public class LogicalOrExpressionParser implements IParser<LogicalOrExpressionNode> {

    @Override
    public LogicalOrExpressionNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode left =
            new com.flowscript.sintactic.parsers.functions.expresiones.LogicalAndExpressionParser().parse(context);

        com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode node =
            new com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode(firstToken, left);

        while (context.check(com.flowscript.lexer.TokenType.OR) || context.checkValue("o")) {
            com.flowscript.lexer.Token orToken = context.consume();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode right =
                new com.flowscript.sintactic.parsers.functions.expresiones.LogicalAndExpressionParser().parse(context);
            node.addOperand(orToken, right);
        }

        return node;
    }
}
