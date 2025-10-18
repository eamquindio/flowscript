package com.flowscript.sintactic.parsers.functions.expresiones;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ArgumentListParser;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

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

    @Override
    public PostfixOperatorNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("fin de entrada inesperado en operador postfijo");
        }

        if (context.check(TokenType.DOT)) {
            Token dotToken = context.consume(TokenType.DOT);
            Token identifier = context.getCurrentToken();
            if (identifier == null || identifier.getType() != TokenType.IDENTIFIER) {
                throw new Parser.ParseException("se esperaba un identificador despues de '.' en la linea " +
                        dotToken.getLine() + ", columna " + dotToken.getColumn());
            }
            context.advance();
            return new PostfixOperatorNode.PropertyAccessOperatorNode(dotToken, identifier.getValue());
        }

        else if (context.check(TokenType.LEFT_BRACKET)) {
            Token leftBracket = context.consume(TokenType.LEFT_BRACKET);
            ExpressionParser exprParser = new ExpressionParser();
            ExpressionNode indexExpr = exprParser.parse(context);
            context.consume(TokenType.RIGHT_BRACKET);
            return new PostfixOperatorNode.IndexAccessOperatorNode(leftBracket, indexExpr);
        }

        else if (context.check(TokenType.LEFT_PAREN)) {
            Token leftParen = context.consume(TokenType.LEFT_PAREN);

            List<ExpressionNode> args = new ArrayList<>();

            if (!context.check(TokenType.RIGHT_PAREN)) {
                ArgumentListParser argParser = new ArgumentListParser();
                List<ArgumentListNode> parsedArgs = argParser.parse(context);

                for (ArgumentListNode argListNode : parsedArgs) {
                    args.addAll(argListNode.getArguments());
                }
            }

            context.consume(TokenType.RIGHT_PAREN);
            return new PostfixOperatorNode.FunctionCallOperatorNode(leftParen, args);
        }

        throw new Parser.ParseException("token inesperado en operador postfijo: "+ current.getValue());
    }
}
