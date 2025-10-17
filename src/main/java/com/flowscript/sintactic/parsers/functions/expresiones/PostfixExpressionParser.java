package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixExpressionNode;

/**
 * Parser para expresiones postfijas.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * PostfixExpression ::= PrimaryExpression PostfixOperator*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 9: Expresiones - Postfijas
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Una expresión postfija consiste en una expresión primaria seguida de cero o más operadores postfijos
 * (acceso a miembros, indexación, llamadas a función).
 *
 * @see PostfixExpressionNode
 * @see PostfixOperatorParser
 */
public class PostfixExpressionParser implements IParser<PostfixExpressionNode> {

    @Override
    public PostfixExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        PrimaryExpressionParser primaryParser = new PrimaryExpressionParser();
        ExpressionNode primaryExpr = primaryParser.parse(context);

        Token firstToken = primaryExpr.getToken();
        PostfixExpressionNode postfixNode = new PostfixExpressionNode(firstToken, primaryExpr);

        PostfixOperatorParser operatorParser = new PostfixOperatorParser();
        boolean foundOperator = true;

        while (foundOperator) {
            try {
                PostfixOperatorNode operator = operatorParser.parse(context);
                postfixNode.addOperator(operator);
            } catch (Parser.ParseException e) {
                foundOperator = false;
            }
        }

        return (PostfixExpressionNode) postfixNode.getSimplified();

    }
}
