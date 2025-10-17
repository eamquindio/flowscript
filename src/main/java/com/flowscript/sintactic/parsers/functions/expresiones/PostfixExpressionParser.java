package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PrimaryExpressionNode;

/**
 * Parser para expresiones postfijas.
 *
 * <h3>Gramática BNF:</h3>
 * 
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
 * Una expresión postfija consiste en una expresión primaria seguida de cero o
 * más operadores postfijos
 * (acceso a miembros, indexación, llamadas a función).
 *
 * @see PostfixExpressionNode
 * @see PostfixOperatorParser
 */
public class PostfixExpressionParser implements IParser<PostfixExpressionNode> {

    private static final PrimaryExpressionParser PRIMARY_PARSER = new PrimaryExpressionParser();
    private static final PostfixOperatorParser POSTFIX_PARSER = new PostfixOperatorParser();

    @Override
    public PostfixExpressionNode parse(ParserContext context) throws ParseException {
        Token firstToken = context.getCurrentToken();
        PrimaryExpressionNode primary = PRIMARY_PARSER.parse(context);
        PostfixExpressionNode node = new PostfixExpressionNode(firstToken, primary);

        while (isPostfixOperator(context.getCurrentToken())) {
            node.addOperator(POSTFIX_PARSER.parse(context));
        }
        return node;
    }

    private boolean isPostfixOperator(Token token) {
        return token != null && (token.getType() == TokenType.DOT ||
                token.getType() == TokenType.LEFT_BRACKET ||
                token.getType() == TokenType.LEFT_PAREN);
    }
}
