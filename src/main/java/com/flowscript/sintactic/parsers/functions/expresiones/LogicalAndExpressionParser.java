package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
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
        // TODO: Implementar este método
        EqualityExpressionParser equalityParser = new EqualityExpressionParser();
        ExpressionNode firstOperand = equalityParser.parse(context);

        Token firstToken = context.getPreviousToken(); 
        LogicalAndExpressionNode node = new LogicalAndExpressionNode(firstToken, firstOperand);

        while (context.match(TokenType.AND) || context.match(TokenType.IDENTIFIER, "y")) {
            Token andToken = context.getCurrentToken();
            context.consume(); 

            ExpressionNode nextOperand = equalityParser.parse(context);

            node.addOperand(andToken, nextOperand);
        }

        if (node.isSingleOperand()) {
            return (LogicalAndExpressionNode) node.getSimplified();
        }

        return node;


    }
}
