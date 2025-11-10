package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;

/**
 * Parser para operadores postfijos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * PostfixOperator ::= '.' IDENTIFIER
 *                   | '[' Expression ']'
 *                   | '(' ArgumentList? ')'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 10: Expresiones - Operadores Postfijos
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Los operadores postfijos incluyen:
 * - Acceso a miembro: .identificador
 * - Indexación: [expresión]
 * - Llamada a función: (argumentos)
 *
 * @see PostfixOperatorNode
 */
public class PostfixOperatorParser implements IParser<PostfixOperatorNode> {

    private final ExpressionParser expressionParser;
    private final com.flowscript.sintactic.parsers.functions.listas_argumentos.ArgumentListParser argumentListParser;

    public PostfixOperatorParser() {
        this.expressionParser = new ExpressionParser();
        this.argumentListParser = new com.flowscript.sintactic.parsers.functions.listas_argumentos.ArgumentListParser();
    }

    @Override
    public PostfixOperatorNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Expected postfix operator but reached end of input");
        }

        // Case 1: '.' IDENTIFIER (property access)
        if (current.getType() == com.flowscript.lexer.TokenType.DOT) {
            com.flowscript.lexer.Token dotToken = context.consume(); // consume '.'
            com.flowscript.lexer.Token propertyToken = context.consume(com.flowscript.lexer.TokenType.IDENTIFIER);
            return new PostfixOperatorNode.PropertyAccessOperatorNode(dotToken, propertyToken.getValue());
        }

        // Case 2: '[' Expression ']' (index access)
        if (current.getType() == com.flowscript.lexer.TokenType.LEFT_BRACKET) {
            com.flowscript.lexer.Token leftBracket = context.consume(); // consume '['
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode index = expressionParser.parse(context);
            context.consume(com.flowscript.lexer.TokenType.RIGHT_BRACKET);
            return new PostfixOperatorNode.IndexAccessOperatorNode(leftBracket, index);
        }

        // Case 3: '(' ArgumentList? ')' (function call)
        if (current.getType() == com.flowscript.lexer.TokenType.LEFT_PAREN) {
            com.flowscript.lexer.Token leftParen = context.consume(); // consume '('

            // Check for empty argument list
            java.util.List<com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode> args = new java.util.ArrayList<>();
            if (context.getCurrentToken() != null &&
                context.getCurrentToken().getType() != com.flowscript.lexer.TokenType.RIGHT_PAREN) {
                com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode argList = argumentListParser.parse(context);
                args = argList.getArguments();
            }

            context.consume(com.flowscript.lexer.TokenType.RIGHT_PAREN);
            return new PostfixOperatorNode.FunctionCallOperatorNode(leftParen, args);
        }

        throw new Parser.ParseException(
            "Expected postfix operator (., [, or () but found '" + current.getValue() +
            "' at line " + current.getLine()
        );
    }
}
