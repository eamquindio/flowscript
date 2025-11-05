package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.TernaryExpressionNode;

/**
 * Parser para expresiones ternarias (operador condicional).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * TernaryExpression ::= LogicalOrExpression ( '?' Expression ':' Expression )?
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 1: Expresiones - Ternarias (más baja precedencia)
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * El operador ternario tiene la forma: condición ? valor_si_verdadero : valor_si_falso
 *
 * @see TernaryExpressionNode
 */
public class TernaryExpressionParser implements IParser<TernaryExpressionNode> {

    @Override
    public TernaryExpressionNode parse(ParserContext context) throws Parser.ParseException {
        if (context.getCurrentToken() == null) {
            throw new Parser.ParseException("Se esperaba una expresión pero no hay más tokens");
        }
        ExpressionNode condition = new LogicalOrExpressionParser().parse(context);

        if (context.getCurrentToken() == null || !context.checkValue("?")) {
            return new TernaryExpressionNode(condition, condition.getToken(), null, null);
        }
        Token question = context.consumeValue("?");

        if (context.getCurrentToken() == null) {
            throw new Parser.ParseException("Se esperaba una expresión después de '?' en la expresión ternaria");
        }

        ExpressionNode trueExpr = new TernaryExpressionParser().parse(context);


        Token colon;
        if (context.getCurrentToken() == null || !context.checkValue(":")) {
            Token t = context.getCurrentToken();
            if (t == null) {
                t = question;
            }
            throw new Parser.ParseException("Se esperaba ':' en la expresión ternaria (línea " + t.getLine() + ")");
        }

        colon = context.consumeValue(":");
        if (context.getCurrentToken() == null) {
            throw new Parser.ParseException("Se esperaba una expresión después de ':' en la expresión ternaria");
        }

        ExpressionNode falseExpr = new TernaryExpressionParser().parse(context);
        return new TernaryExpressionNode(condition, question, trueExpr, falseExpr);
    }
}
