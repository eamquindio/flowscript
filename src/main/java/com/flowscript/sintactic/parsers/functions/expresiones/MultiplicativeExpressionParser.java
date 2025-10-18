package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.TokenType;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode;

/**
 * Parser para expresiones multiplicativas.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * MultiplicativeExpression ::= UnaryExpression ( ( '*' | '/' | '%' ) UnaryExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 7: Expresiones - Multiplicativas
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores * (multiplicación), / (división) y % (módulo).
 * El operador es asociativo por la izquierda.
 *
 * @see MultiplicativeExpressionNode
 */
public class MultiplicativeExpressionParser implements IParser<MultiplicativeExpressionNode> {

    @Override
    public MultiplicativeExpressionNode parse(ParserContext context) throws Parser.ParseException {
        UnaryExpressionParser unaryExpressionParser = new UnaryExpressionParser();
        ExpressionNode left = unaryExpressionParser.parse(context);
        MultiplicativeExpressionNode node = new MultiplicativeExpressionNode(context.getCurrentToken(), left);
        while (context.checkAny(TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.MODULO)) {
            Token operator = context.consume();
            ExpressionNode right = unaryExpressionParser.parse(context);
            node.addOperand(operator, right);
        }

        return node;
    }
}
