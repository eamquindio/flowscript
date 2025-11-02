package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.ListLiteralNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser para literales de lista.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ListLiteral ::= '[' ExpressionList? ']'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 19: Literales - Listas
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer literales de lista como: [1, 2, 3], ["a", "b"], [1, "texto", verdadero]
 * Una lista vacía [] también es válida.
 *
 * @see ListLiteralNode
 * @see com.flowscript.sintactic.parsers.functions.listas_argumentos.ExpressionListParser
 */
public class ListLiteralParser implements IParser<ListLiteralNode> {

    private ExpressionParser expressionParser;

    public ListLiteralParser() {
        // Lazy initialization to avoid circular dependency
    }
    
    private ExpressionParser getExpressionParser() {
        if (expressionParser == null) {
            expressionParser = new ExpressionParser();
        }
        return expressionParser;
    }

    @Override
    public ListLiteralNode parse(ParserContext context) throws Parser.ParseException {
        Token leftBracket = context.consume(TokenType.LEFT_BRACKET);
        
        if (context.check(TokenType.RIGHT_BRACKET)) {
            context.consume(TokenType.RIGHT_BRACKET);
            return new ListLiteralNode(leftBracket);
        }
        
        List<ExpressionNode> expressions = new ArrayList<>();
        expressions.add(getExpressionParser().parse(context));
        
        while (context.checkValue(",")) {
            context.consume();
            expressions.add(getExpressionParser().parse(context));
        }
        
        context.consume(TokenType.RIGHT_BRACKET);
        
        ExpressionListNode expressionList = new ExpressionListNode(leftBracket, expressions);
        return new ListLiteralNode(leftBracket, expressionList);
    }
}
