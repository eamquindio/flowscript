package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.PrimaryExpressionNode;

/**
 * Parser para expresiones primarias.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * PrimaryExpression ::= IDENTIFIER
 *                     | Literal
 *                     | '(' Expression ')'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 11: Expresiones - Primarias (más alta precedencia)
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Las expresiones primarias son los bloques básicos de construcción:
 * - Identificadores (variables, funciones)
 * - Literales (números, cadenas, booleanos, etc.)
 * - Expresiones entre paréntesis
 *
 * @see PrimaryExpressionNode
 */
public class PrimaryExpressionParser implements IParser<PrimaryExpressionNode> {

    @Override
    public PrimaryExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing primary expression");
        }

        if (context.match(TokenType.LEFT_PAREN)) {
            Token leftParen = context.consume(TokenType.LEFT_PAREN);

            ExpressionParser exprParser = new ExpressionParser();
            ExpressionNode innerExpr = exprParser.parse(context);

            if (!context.match(TokenType.RIGHT_PAREN)) {
                throw new Parser.ParseException(
                    "Expected ')' to close parenthesized expression at line " + current.getLine());
            }
            context.consume(TokenType.RIGHT_PAREN);

            return new ParenthesizedExpressionNode(leftParen, innerExpr);
        }

        if (current.getType().isLiteral()) {
            Token literalToken = context.consume();
            return new LiteralExpressionNode(literalToken);
        }

        if (context.match(TokenType.IDENTIFIER)) {
            Token identifierToken = context.consume(TokenType.IDENTIFIER);
            return new VariableReferenceNode(identifierToken);
        }

        throw new Parser.ParseException(
            "Unexpected token '" + current.getValue() + "' while parsing PrimaryExpression at line " + current.getLine());

    }
} 
