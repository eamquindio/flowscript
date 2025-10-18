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
        LogicalOrExpressionParser logicalOrParser = new LogicalOrExpressionParser();
        ExpressionParser expressionParser = new ExpressionParser();
        ExpressionNode condition = logicalOrParser.parse(context);
        Token token = context.getCurrentToken();
        if (token == null || !token.getValue().equals("?")) {
            return new TernaryExpressionNode(condition, condition.getToken(), null, null);
        }else{
            Token questionToken = context.consumeValue("?");
            ExpressionNode trueExpr = expressionParser.parse(context);
            context.consumeValue(":");
            ExpressionNode falseExpr = expressionParser.parse(context);
            return new TernaryExpressionNode(condition, questionToken, trueExpr, falseExpr);
        }
    }
}
