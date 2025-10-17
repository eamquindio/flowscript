package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.UnaryExpressionNode;

/**
 * Parser para expresiones multiplicativas.
 *
 * <h3>Gramática BNF:</h3>
 * 
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
    private static final UnaryExpressionParser UNARY_PARSER = new UnaryExpressionParser();

    @Override
    public MultiplicativeExpressionNode parse(ParserContext context) throws ParseException {
        UnaryExpressionNode firstOperand = UNARY_PARSER.parse(context);
        MultiplicativeExpressionNode node = new MultiplicativeExpressionNode(firstOperand.getToken(), firstOperand);

        while (isMultiplicativeOperator(context.getCurrentToken().getType())) {
            Token operator = context.getCurrentToken();
            context.advance();
            node.addOperand(operator, UNARY_PARSER.parse(context));
        }

        return node;
    }

    private boolean isMultiplicativeOperator(TokenType type) {
        return type == TokenType.MULTIPLY || type == TokenType.DIVIDE || type == TokenType.MODULO;
    }
}
