package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PrimaryExpressionNode;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.parsers.functions.literales.ListLiteralParser;
import com.flowscript.sintactic.parsers.functions.literales.ObjectLiteralParser;

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
        Token current = context.getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("fin de entrada inesperado en ExpresionPrimaria");
        }

        TokenType type = current.getType();

        if (type == TokenType.LEFT_BRACE) {
            ObjectLiteralParser objectParser = new ObjectLiteralParser();
            ObjectLiteralNode objNode = objectParser.parse(context);
            return new PrimaryExpressionNode(objNode.getToken()) {
                @Override
                public String getExpressionType() { return "object"; }
                @Override
                public String toString() { return objNode.toString(); }
            };
        }

        if (type == TokenType.LEFT_BRACKET) {
            ListLiteralParser listParser = new ListLiteralParser();
            ListLiteralNode listNode = listParser.parse(context);
            return new PrimaryExpressionNode(listNode.getToken()) {
                @Override
                public String getExpressionType() { return "list"; }
                @Override
                public String toString() { return listNode.toString(); }
            };
        }

        if (type == TokenType.IDENTIFIER) {
            context.advance();
            return new PrimaryExpressionNode(current) {
                @Override
                public String getExpressionType() { return "variable"; }
                @Override
                public String toString() { return "Identifier(" + current.getValue() + ")"; }
            };
        }

        if (type.isLiteral()) {
            context.advance();
            return new PrimaryExpressionNode(current) {
                @Override
                public String getExpressionType() { return "literal"; }
                @Override
                public String toString() { return "Literal(" + current.getValue() + ")"; }
            };
        }

        if (type == TokenType.LEFT_PAREN) {
            Token leftParen = context.consume(TokenType.LEFT_PAREN);
            ExpressionParser exprParser = new ExpressionParser();
            ExpressionNode innerExpr = exprParser.parse(context);
            context.consume(TokenType.RIGHT_PAREN);
            return new PrimaryExpressionNode.ParenthesizedExpressionNode(leftParen, innerExpr);
        }

        throw new Parser.ParseException(
            "token inesperado en ExpresionPrimaria: " + current.getValue() +
            " at line " + current.getLine() + ", column " + current.getColumn()
        );
    }
}
