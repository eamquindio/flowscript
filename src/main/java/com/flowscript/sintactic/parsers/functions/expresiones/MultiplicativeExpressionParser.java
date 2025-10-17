package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.MultiplicativeExpressionNode;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.expressions.ExpressionNode;
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
        // TODO: Implementar este método
        UnaryExpressionParser unaryParser = new UnaryExpressionParser();
        ExpressionNode firstOperand = unaryParser.parse(context);

        Token firstToken = context.getPreviousToken();
        MultiplicativeExpressionNode node = new MultiplicativeExpressionNode(firstToken, firstOperand);

        while (context.match(TokenType.MULTIPLY) ||
               context.match(TokenType.DIVIDE) ||
               context.match(TokenType.MODULO)) {

            Token operatorToken = context.getCurrentToken();
            context.consume(); 

            ExpressionNode nextOperand = unaryParser.parse(context);

            node.addOperand(operatorToken, nextOperand);
        }

        if (node.isSingleOperand()) {
            return (MultiplicativeExpressionNode) node.getSimplified();
        }

        return node;

    }
}
