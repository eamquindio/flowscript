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

    public PrimaryExpressionParser() {
        this.literalParser = new com.flowscript.sintactic.parsers.functions.literales.LiteralParser();
    }

    @Override
    public com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        com.flowscript.lexer.Token token = context.getCurrentToken();
        
        if (token == null) {
            throw new Parser.ParseException("Expected expression but reached end of input");
        }
        
        com.flowscript.lexer.TokenType type = token.getType();
        
        if (type == com.flowscript.lexer.TokenType.IDENTIFIER) {
            com.flowscript.lexer.Token idToken = context.consume(com.flowscript.lexer.TokenType.IDENTIFIER);
            return new com.flowscript.sintactic.ast.functions.expresiones.IdentifierNode(idToken);
        } else if (type == com.flowscript.lexer.TokenType.LEFT_PAREN) {
            context.consume(com.flowscript.lexer.TokenType.LEFT_PAREN);
            ExpressionParser exprParser = new ExpressionParser();
            com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode expr = exprParser.parse(context);
            context.consume(com.flowscript.lexer.TokenType.RIGHT_PAREN);
            return expr;
        } else {
            return literalParser.parse(context);
        }
    }
}
