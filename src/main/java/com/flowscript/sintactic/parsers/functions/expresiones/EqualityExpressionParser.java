package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.EqualityExpressionNode;

/**
 * Parser para expresiones de igualdad.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * EqualityExpression ::= RelationalExpression ( ( '==' | '!=' ) RelationalExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3> 
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 4: Expresiones - Igualdad
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores == (igual) y != (distinto).
 * El operador es asociativo por la izquierda.
 *
 * @see EqualityExpressionNode
 */
public class EqualityExpressionParser implements IParser<EqualityExpressionNode> {

    @Override
    public EqualityExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        RelationalExpressionParser relationalParser = new RelationalExpressionParser();
        ExpressionNode firstOperand = relationalParser.parse(context);

        Token firstToken = context.getPreviousToken();
        EqualityExpressionNode node = new EqualityExpressionNode(firstToken, firstOperand);

        while (context.match(TokenType.EQUAL) || context.match(TokenType.NOT_EQUAL)) {
            Token operatorToken = context.getCurrentToken();
            context.consume(); 

            ExpressionNode nextOperand = relationalParser.parse(context);

            node.addOperand(operatorToken, nextOperand);
        }
        
        if (node.isSingleOperand()) {
            return (EqualityExpressionNode) node.getSimplified();
        }

        return node;
    }
}
