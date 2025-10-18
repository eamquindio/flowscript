package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para FunctionDeclarationParser.
 *
 * Gramática BNF:
 * FunctionDeclaration ::= 'function' IDENTIFIER '(' ParameterList? ')' ( '->' Type )? Block
 */
public class FunctionDeclarationParserTest {

    private FunctionDeclarationParser parser = new FunctionDeclarationParser();

    @Test
    public void testFunctionWithoutParameters() throws Exception {
        String input = "function main() { print(\"Hello\") }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        FunctionDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("main", result.getName());
        assertTrue(result.getParameters().isEmpty());
        assertNull(result.getReturnType());
    }

    @Test
    public void testFunctionWithReturnType() throws Exception {
        String input = "function get_value() -> integer { return 42 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        FunctionDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("get_value", result.getName());
        assertNotNull(result.getReturnType());
        assertEquals("integer", result.getReturnType().getTypeName());
    }

    @Test
    public void testFunctionWithSingleParameter() throws Exception {
        String input = "function double(x: integer) -> integer { return x * 2 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        FunctionDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("double", result.getName());
        assertEquals(1, result.getParameters().size());
        assertEquals("x", result.getParameters().get(0).getName());
    }

    @Test
    public void testFunctionWithMultipleParameters() throws Exception {
        String input = "function add(a: integer, b: integer) -> integer { return a + b }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        FunctionDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("add", result.getName());
        assertEquals(2, result.getParameters().size());
    }

    @Test
    public void testFunctionWithComplexBody() throws Exception {
        String input = "function calculate(x: decimal) -> decimal { result = x * 2\n if result > 10 { return 10 }\n return result }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        FunctionDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("calculate", result.getName());
    }
}
