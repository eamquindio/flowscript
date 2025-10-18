package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
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
        // Conservative implementation: parse a LogicalOrExpression (fallback to Primary)
        // and if a '?' is present parse the true/false branches as Expressions via ExpressionParser.
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode condition =
            new com.flowscript.sintactic.parsers.functions.expresiones.LogicalOrExpressionParser().parse(context);

        if (context.check(com.flowscript.lexer.TokenType.QUESTION)) {
            com.flowscript.lexer.Token question = context.consume(); // consume '?'
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode trueExpr = new ExpressionParser().parse(context);
            context.consume(com.flowscript.lexer.TokenType.COLON);
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode falseExpr = new ExpressionParser().parse(context);
            return new TernaryExpressionNode(condition, question, trueExpr, falseExpr);
        }

        // No ternary operator: return a node using a synthetic token as marker and null branches
        com.flowscript.lexer.Token synthetic = new com.flowscript.lexer.Token(com.flowscript.lexer.TokenType.NULL, "null", -1, -1, 0);
        return new TernaryExpressionNode(condition, synthetic, null, null);
    }
}
