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
public class PostfixExpressionParser implements IParser<com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode> {

    private final PrimaryExpressionParser primaryParser;
    private final PostfixOperatorParser operatorParser;

    public PostfixExpressionParser() {
        this.primaryParser = new PrimaryExpressionParser();
        this.operatorParser = new PostfixOperatorParser();
    }

    @Override
    public com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // Parse primary expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode primary = primaryParser.parse(context);

        // Check if there are postfix operators
        if (context.getCurrentToken() == null || !isPostfixOperator(context.getCurrentToken())) {
            // No postfix operators, just return the primary expression
            return primary;
        }

        // Create PostfixExpressionNode
        com.flowscript.lexer.Token firstToken = context.getCurrentToken();
        PostfixExpressionNode postfixNode = new PostfixExpressionNode(firstToken, primary);

        // Parse zero or more postfix operators
        while (context.getCurrentToken() != null && isPostfixOperator(context.getCurrentToken())) {
            postfixNode.addOperator(operatorParser.parse(context));
        }

        return postfixNode;
    }

    private boolean isPostfixOperator(com.flowscript.lexer.Token token) {
        com.flowscript.lexer.TokenType type = token.getType();
        return type == com.flowscript.lexer.TokenType.DOT ||
               type == com.flowscript.lexer.TokenType.LEFT_BRACKET ||
               type == com.flowscript.lexer.TokenType.LEFT_PAREN;
    }
}
