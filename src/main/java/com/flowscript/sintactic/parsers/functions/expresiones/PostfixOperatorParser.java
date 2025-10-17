package com.flowscript.sintactic.parsers.functions.expresiones;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ArgumentListParser;

/**
 * Parser para operadores postfijos.
 *
 * <h3>Gramática BNF:</h3>
 * 
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

    private static final ArgumentListParser ARGUMENT_PARSER = new ArgumentListParser();
    private static final ExpressionParser EXPRESSION_PARSER = new ExpressionParser();

    @Override
    public PostfixOperatorNode parse(ParserContext context) throws ParseException {
        Token currentToken = context.getCurrentToken();

        if (currentToken.getType() == TokenType.DOT) {
            return parsePropertyAccess(context);
        }

        if (currentToken.getType() == TokenType.LEFT_BRACKET) {
            return parseIndexAccess(context);
        }

        if (currentToken.getType() == TokenType.LEFT_PAREN) {
            return parseFunctionCall(context);
        }

        throw new ParseException("Se esperaba un operador postfijo (., [], ())");
    }

    private PostfixOperatorNode parsePropertyAccess(ParserContext context) throws ParseException {
        Token dotToken = context.consume(TokenType.DOT);
        String propertyName = context.consume(TokenType.IDENTIFIER).getValue();

        return new PostfixOperatorNode.PropertyAccessOperatorNode(dotToken, propertyName);
    }

    private PostfixOperatorNode parseIndexAccess(ParserContext context) throws ParseException {
        Token leftBracketToken = context.consume(TokenType.LEFT_BRACKET);
        ExpressionNode index = EXPRESSION_PARSER.parse(context);
        context.consume(TokenType.RIGHT_BRACKET);

        return new PostfixOperatorNode.IndexAccessOperatorNode(leftBracketToken, index);
    }

    private PostfixOperatorNode parseFunctionCall(ParserContext context) throws ParseException {
        Token leftParenToken = context.consume(TokenType.LEFT_PAREN);
        List<ExpressionNode> arguments = new ArrayList<>();

        if (!context.check(TokenType.RIGHT_PAREN)) {
            for (ArgumentListNode arg : ARGUMENT_PARSER.parse(context)) {
                arguments.add(arg.getArgument(0));
            }
        }

        context.consume(TokenType.RIGHT_PAREN);

        return new PostfixOperatorNode.FunctionCallOperatorNode(leftParenToken, arguments);
    }

}
