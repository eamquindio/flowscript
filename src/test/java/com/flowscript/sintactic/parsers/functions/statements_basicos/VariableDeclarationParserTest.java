package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.VariableDeclarationNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para VariableDeclarationParser.
 *
 * Gramática BNF:
 * VariableDeclaration ::= IDENTIFIER '=' Expression
 */
public class VariableDeclarationParserTest {

    private VariableDeclarationParser parser = new VariableDeclarationParser();

    @Test
    public void testSimpleVariableDeclaration() throws Exception {
        String input = "x = 42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("x", result.getVariableName());
        assertNotNull(result.getInitializer());
    }

    @Test
    public void testVariableWithStringValue() throws Exception {
        String input = "name = \"John\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("name", result.getVariableName());
    }

    @Test
    public void testVariableWithExpression() throws Exception {
        String input = "total = a + b * c";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("total", result.getVariableName());
    }

    @Test
    public void testVariableWithFunctionCall() throws Exception {
        String input = "result = calculate(x, y)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("result", result.getVariableName());
    }

    @Test
    public void testVariableWithObjectLiteral() throws Exception {
        String input = "person = { name: \"John\", age: 30 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("person", result.getVariableName());
    }

    @Test
    public void testVariableWithArrayLiteral() throws Exception {
        String input = "items = [1, 2, 3]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("items", result.getVariableName());
    }
}
