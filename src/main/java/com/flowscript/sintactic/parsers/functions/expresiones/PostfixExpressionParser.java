package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
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
        PrimaryExpressionParser primaryExpressionParser = new PrimaryExpressionParser();
        ExpressionNode primary = primaryExpressionParser.parse(context);
        PostfixExpressionNode node = new PostfixExpressionNode(primary.getToken(), primary);
        PostfixOperatorParser operatorParser = new PostfixOperatorParser();
        while (true) {
            if (context.checkAny(TokenType.DOT, TokenType.LEFT_BRACKET, TokenType.LEFT_PAREN)) {
                PostfixOperatorNode operatorNode = operatorParser.parse(context);
                node.addOperator(operatorNode);
            } else {
                break;
            }
        }
        return node;
    }
}
