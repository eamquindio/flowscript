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
public class PrimaryExpressionParser implements IParser<com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode> {

    private final com.flowscript.sintactic.parsers.functions.literales.LiteralParser literalParser;
    private final ExpressionParser expressionParser;

    public PrimaryExpressionParser() {
        this.literalParser = new com.flowscript.sintactic.parsers.functions.literales.LiteralParser();
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Expected primary expression but reached end of input");
        }

        // Case 1: '(' Expression ')'
        if (current.getType() == com.flowscript.lexer.TokenType.LEFT_PAREN) {
            com.flowscript.lexer.Token leftParen = context.consume(); // consume '('
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode expr = expressionParser.parse(context);
            context.consume(com.flowscript.lexer.TokenType.RIGHT_PAREN);
            return new PrimaryExpressionNode.ParenthesizedExpressionNode(leftParen, expr);
        }

        // Case 2: IDENTIFIER (incluyendo keywords que pueden ser usados como identificadores)
        if (current.getType() == com.flowscript.lexer.TokenType.IDENTIFIER || isAcceptableAsIdentifier(current)) {
            com.flowscript.lexer.Token idToken = context.consume();
            return new com.flowscript.sintactic.ast.functions.expresiones.IdentifierNode(idToken);
        }

        // Case 3: Literal
        return literalParser.parse(context);
    }

    /**
     * Verifica si un token keyword puede ser usado como identificador.
     * Esto permite usar keywords como "y", "o", "no" como nombres de variables.
     */
    private boolean isAcceptableAsIdentifier(com.flowscript.lexer.Token token) {
        com.flowscript.lexer.TokenType type = token.getType();
        // Permitir operadores lógicos como identificadores
        return type == com.flowscript.lexer.TokenType.AND ||
               type == com.flowscript.lexer.TokenType.OR ||
               type == com.flowscript.lexer.TokenType.NOT;
    }
}
