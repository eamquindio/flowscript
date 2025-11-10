package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ExpressionListNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ExpressionListParser.
 *
 * Gramática BNF:
 * ExpressionList ::= Expression ( ',' Expression )*
 */
public class ExpressionListParserTest {

    private ExpressionListParser parser = new ExpressionListParser();

    @Test
    public void testSingleExpression() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testTwoExpressions() throws Exception {
        String input = "1, 2";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testMultipleExpressions() throws Exception {
        String input = "1, 2, 3, 4, 5";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void testMixedTypes() throws Exception {
        String input = "42, \"texto\", verdadero, 3.14";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(4, result.size());
    }

    @Test
    public void testComplexExpressions() throws Exception {
        String input = "x + y, a * b, verdadero o falso";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testNestedListLiterals() throws Exception {
        String input = "[1, 2], [3, 4]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testFunctionCalls() throws Exception {
        String input = "calcular(10), obtener_nombre()";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ExpressionListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testWithTrailingComma_ShouldFail() throws Exception {
        String input = "1, 2, 3,";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testEmptyInput_ShouldFail() throws Exception {
        String input = "";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
