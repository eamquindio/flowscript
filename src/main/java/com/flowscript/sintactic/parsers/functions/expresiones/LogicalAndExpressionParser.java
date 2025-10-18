package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalAndExpressionNode;

/**
 * Parser para expresiones lógicas AND.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * LogicalAndExpression ::= EqualityExpression ( ( 'and' | 'y' ) EqualityExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 3: Expresiones - AND Lógico
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta tanto 'and' (inglés) como 'y' (español).
 * El operador es asociativo por la izquierda.
 *
 * @see LogicalAndExpressionNode
 */
public class LogicalAndExpressionParser implements IParser<LogicalAndExpressionNode> {

    @Override
    public LogicalAndExpressionNode parse(ParserContext context) throws Parser.ParseException {
        EqualityExpressionParser equalityParser = new EqualityExpressionParser();

        ExpressionNode firstOperand = equalityParser.parse(context);
        Token firstToken = firstOperand.getToken();

        LogicalAndExpressionNode logicalAndNode = new LogicalAndExpressionNode(firstToken, firstOperand);

        while (true) {
            Token currentToken = context.getCurrentToken();

            if (currentToken != null &&
                    (currentToken.getValue().equalsIgnoreCase("and") || currentToken.getValue().equalsIgnoreCase("y"))) {

                context.consume();

                ExpressionNode nextOperand = equalityParser.parse(context);
                logicalAndNode.addOperand(currentToken, nextOperand);
            } else {
                break;
            }
        }

        return (LogicalAndExpressionNode) logicalAndNode.getSimplified();
    }
}
