package com.flowscript.sintactic.parsers.functions.expresiones;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.IdentifierNode;
import com.flowscript.sintactic.ast.functions.expresiones.LiteralNode;
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
        if (current == null)
            throw new Parser.ParseException("fin de entrada inesperado en ExpresionPrimaria");

        TokenType type = current.getType();

        if (type == TokenType.ASSIGN) {
            context.advance(); 
            Token next = context.getCurrentToken();
            if (next == null)
                throw new Parser.ParseException("Se esperaba una expresión válida después de '='");

            ExpressionParser exprParser = new ExpressionParser();
            ExpressionNode expr = exprParser.parse(context);
            return new PrimaryExpressionNode(next) {
                @Override
                public String getExpressionType() { return "assignment_right"; }
                @Override
                public String toString() { return expr.toString(); }
            };
        }

        if (type == TokenType.LEFT_BRACE) {
            ObjectLiteralParser objParser = new ObjectLiteralParser();
            ObjectLiteralNode obj = objParser.parse(context);
            return new PrimaryExpressionNode(obj.getToken()) {
                @Override
                public String getExpressionType() { return "object"; }
                @Override
                public String toString() { return obj.toString(); }
            };
        }

        if (type == TokenType.LEFT_BRACKET) {
            ListLiteralParser listParser = new ListLiteralParser();
            ListLiteralNode list = listParser.parse(context);
            return new PrimaryExpressionNode(list.getToken()) {
                @Override
                public String getExpressionType() { return "list"; }
                @Override
                public String toString() { return list.toString(); }
            };
        }

        if (type == TokenType.IDENTIFIER) {
            context.advance();
            return new PrimaryExpressionNode(current) {
                private final IdentifierNode idNode = new IdentifierNode(current);
                @Override
                public String getExpressionType() { return idNode.getExpressionType(); }
                @Override
                public String toString() { return idNode.toString(); }
            };
        }

        if (type.isLiteral()) {
            context.advance();
            return new PrimaryExpressionNode(current) {
                private final LiteralNode litNode = new LiteralNode(current);
                @Override
                public String getExpressionType() { return litNode.getExpressionType(); }
                @Override
                public String toString() { return litNode.toString(); }
            };
        }

        if (type == TokenType.LEFT_PAREN) {
            Token leftParen = context.consume(TokenType.LEFT_PAREN);
            ExpressionParser exprParser = new ExpressionParser();
            ExpressionNode inner = exprParser.parse(context);
            context.consume(TokenType.RIGHT_PAREN);
            return new PrimaryExpressionNode.ParenthesizedExpressionNode(leftParen, inner);
        }

        throw new Parser.ParseException(
            "Token inesperado en ExpresionPrimaria en línea " + current.getLine() +
            ", valor: " + current.getValue()
        );
    }
}
