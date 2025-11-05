package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.BinaryExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.TernaryExpressionNode;

/**
 * Parser coordinador para expresiones (punto de entrada del sistema de expresiones).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Expression ::= TernaryExpression
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Expresiones simples
 * 42
 * "Hola"
 * verdadero
 *
 * // Expresiones aritméticas
 * 10 + 20
 * x * (y + z)
 *
 * // Expresiones lógicas
 * x > 10 and y < 20
 * not activo or modo == "prueba"
 *
 * // Expresiones ternarias
 * x > 0 ? "positivo" : "negativo"
 *
 * // Llamadas a función
 * calcular(10, 20)
 * usuario.obtener_nombre()
 *
 * // Acceso a propiedades
 * usuario.nombre
 * lista[0]
 * matriz[i][j]
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ExpressionParser parser = new ExpressionParser();
 * ExpressionNode expr = parser.parse(context);
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} delegando a TernaryExpressionParser.
 *
 * @see ExpressionNode
 */
public class ExpressionParser implements IParser<ExpressionNode> {

    private final PrimaryExpressionParser primaryParser;

    public ExpressionParser() {
        this.primaryParser = new PrimaryExpressionParser();
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        return parseAdditiveExpression(context);
    }

    private ExpressionNode parseAdditiveExpression(ParserContext context) throws Parser.ParseException {
        ExpressionNode left = parseMultiplicativeExpression(context);

        while (context.getCurrentToken() != null &&
                (context.checkValue("+") || context.checkValue("-"))) {
            Token operator = context.getCurrentToken();
            context.advance();
            ExpressionNode right = parseMultiplicativeExpression(context);
            left = new BinaryExpressionNode(left, operator, right);
        }

        return left;
    }

    private ExpressionNode parseMultiplicativeExpression(ParserContext context) throws Parser.ParseException {
        ExpressionNode left = primaryParser.parse(context);

        while (context.getCurrentToken() != null &&
                (context.checkValue("*") || context.checkValue("/") || context.checkValue("%"))) {
            Token operator = context.getCurrentToken();
            context.advance();
            ExpressionNode right = primaryParser.parse(context);
            left = new BinaryExpressionNode(left, operator, right);
        }

        return left;
    }
}
