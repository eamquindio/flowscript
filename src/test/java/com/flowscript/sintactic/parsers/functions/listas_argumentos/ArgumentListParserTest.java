package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ArgumentListNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ArgumentListParser.
 *
 * Gramática BNF:
 * ArgumentList ::= Expression ( ',' Expression )*
 */
public class ArgumentListParserTest {

    private ArgumentListParser parser = new ArgumentListParser();

    @Test
    public void testSingleArgument() throws Exception {
        String input = "42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.getArguments().size());
    }

    @Test
    public void testTwoArguments() throws Exception {
        String input = "10, 20";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getArguments().size());
    }

    @Test
    public void testMultipleArguments() throws Exception {
        String input = "1, 2, 3, 4, 5";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(5, result.getArguments().size());
    }

    @Test
    public void testMixedTypeArguments() throws Exception {
        String input = "42, \"mensaje\", verdadero, 3.14, nulo";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(5, result.getArguments().size());
    }

    @Test
    public void testComplexExpressions() throws Exception {
        String input = "x + y, a * b, calcular(10)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.getArguments().size());
    }

    @Test
    public void testNestedFunctionCalls() throws Exception {
        String input = "obtener_datos(), procesar(x, y), guardar(resultado)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.getArguments().size());
    }

    @Test
    public void testObjectLiteralArgument() throws Exception {
        String input = "{ nombre: \"Ana\", edad: 25 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.getArguments().size());
    }

    @Test
    public void testListLiteralArgument() throws Exception {
        String input = "[1, 2, 3], [4, 5, 6]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getArguments().size());
    }

    @Test
    public void testComplexNestedArgument() throws Exception {
        String input = "{ id: 1, datos: [10, 20, 30], activo: verdadero }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ArgumentListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.getArguments().size());
    }

    @Test
    public void testInvalidArguments_TrailingComma() throws Exception {
        String input = "1, 2, 3,";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidArguments_EmptyInput() throws Exception {
        String input = "";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
