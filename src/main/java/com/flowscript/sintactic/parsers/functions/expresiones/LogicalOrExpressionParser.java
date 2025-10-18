package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.LogicalOrExpressionNode;

/**
 * Parser para expresiones lógicas OR.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * LogicalOrExpression ::= LogicalAndExpression ( ( 'or' | 'o' ) LogicalAndExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 2: Expresiones - OR Lógico
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta tanto 'or' (inglés) como 'o' (español).
 * El operador es asociativo por la izquierda.
 *
 * @see LogicalOrExpressionNode
 */
public class LogicalOrExpressionParser implements IParser<LogicalOrExpressionNode> {

    @Override
    public LogicalOrExpressionNode parse(ParserContext context) throws Parser.ParseException {
        LogicalAndExpressionParser logicalAndParser = new LogicalAndExpressionParser();

        ExpressionNode firstOperand = logicalAndParser.parse(context);
        Token firstToken = firstOperand.getToken();

        LogicalOrExpressionNode logicalOrNode = new LogicalOrExpressionNode(firstToken, firstOperand);

        while (true) {
            Token currentToken = context.getCurrentToken();

            if (currentToken != null &&
                    (currentToken.getValue().equalsIgnoreCase("or") || currentToken.getValue().equalsIgnoreCase("o"))) {

                context.consume();

                ExpressionNode nextOperand = logicalAndParser.parse(context);
                logicalOrNode.addOperand(currentToken, nextOperand);
            } else {
                break;
            }
        }

        return (LogicalOrExpressionNode) logicalOrNode.getSimplified();
    }

}
