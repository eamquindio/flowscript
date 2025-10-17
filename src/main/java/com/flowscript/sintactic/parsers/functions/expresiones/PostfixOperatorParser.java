package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.expresiones.PostfixOperatorNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ArgumentListParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para operadores postfijos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * PostfixOperator ::= '.' IDENTIFIER
 *                   | '[' Expression ']'
 *                   | '(' ArgumentList? ')'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 10: Expresiones - Operadores Postfijos
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Los operadores postfijos incluyen:
 * - Acceso a miembro: .identificador
 * - Indexación: [expresión]
 * - Llamada a función: (argumentos)
 *
 * @see PostfixOperatorNode
 */
public class PostfixOperatorParser implements IParser<PostfixOperatorNode> {

    @Override
    public PostfixOperatorNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token current = context.getCurrentToken();


        if (context.checkValue(".")){
            Token dot = context.consume();

            Token next = context.getCurrentToken();
            if (next == null || next.getType() != TokenType.IDENTIFIER) {
                throw new Parser.ParseException("Se esperaba un identificador después de '.'");
            }
            Token identifier = context.consume();
            return new PostfixOperatorNode.PropertyAccessOperatorNode(dot, identifier.getValue());
        }


        if (context.checkValue("[")){
            Token leftBracket = context.consume();
            ExpressionParser exprParser = new ExpressionParser();
            ExpressionNode indexExpr = exprParser.parse(context);

            if (!context.checkValue("]")){
                throw new Parser.ParseException("Se esperaba ']' al cerrar el índice");
            }

            context.consume();
            return new PostfixOperatorNode.IndexAccessOperatorNode(leftBracket, indexExpr);
        }


        if (context.checkValue("(")) {
            Token leftParen = context.consume();

            List<ArgumentListNode> argsLists = new ArrayList<>();

            if (!context.checkValue(")")) {
                ArgumentListParser argsParser = new ArgumentListParser();
                argsLists = argsParser.parse(context);
            }
            if (!context.checkValue(")")) {
                throw new Parser.ParseException("Se esperaba ')' al cerrar la lista de argumentos");
            }

            context.consume();
            List<ExpressionNode> argExprs = new ArrayList<>();
            for (ArgumentListNode argList : argsLists) {
                argExprs.addAll(argList.getArguments());
            }
            return new PostfixOperatorNode.FunctionCallOperatorNode(leftParen, argExprs);
        }

        throw new Parser.ParseException("Operador postfijo inesperado: " + (current != null ? current.getValue() : "EOF"));
    }
}
