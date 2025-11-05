package com.flowscript.sintactic.parsers.functions.expresiones;

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
import com.flowscript.sintactic.parsers.functions.literales.ListLiteralParser;
import com.flowscript.sintactic.parsers.functions.literales.LiteralParser;
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
public class PrimaryExpressionParser implements IParser<ExpressionNode> {

    private final LiteralParser literalParser = new LiteralParser();
    private ListLiteralParser listLiteralParser;
    private ObjectLiteralParser objectLiteralParser;

    private ListLiteralParser getListLiteralParser() {
        if (listLiteralParser == null) {
            listLiteralParser = new ListLiteralParser();
        }
        return listLiteralParser;
    }

    private ObjectLiteralParser getObjectLiteralParser() {
        if (objectLiteralParser == null) {
            objectLiteralParser = new ObjectLiteralParser();
        }
        return objectLiteralParser;
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();
        if (token == null) {
            throw new Parser.ParseException("Se esperaba una expresión, pero no hay más tokens.");
        }
        TokenType type = token.getType();

        if (type == TokenType.IDENTIFIER) {
            return new IdentifierNode(context.consume(TokenType.IDENTIFIER));

        } else if (type == TokenType.LEFT_PAREN) {
            context.consume(TokenType.LEFT_PAREN);
            ExpressionNode inner = new ExpressionParser().parse(context);
            context.consume(TokenType.RIGHT_PAREN);
            return inner;

        } else if (type == TokenType.LEFT_BRACKET) {
            return getListLiteralParser().parse(context);

        } else if (type == TokenType.LEFT_BRACE) {
            return getObjectLiteralParser().parse(context);

        } else if (type == TokenType.INTEGER_LITERAL || type == TokenType.DECIMAL_LITERAL || type == TokenType.STRING_LITERAL || type == TokenType.TRUE || type == TokenType.FALSE || type == TokenType.NULL) {
            return literalParser.parse(context);

        } else {
            throw new Parser.ParseException("Token inesperado en expresión primaria: " + type);
        }
    }
}
