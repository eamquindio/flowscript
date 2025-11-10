package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.statements_basicos.VariableDeclarationStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para VariableDeclarationStatementParser.
 *
 * Gramática BNF:
 * VariableDeclaration ::= IDENTIFIER '=' Expression
 */
public class VariableDeclarationStatementParserTest {

    private VariableDeclarationStatementParser parser = new VariableDeclarationStatementParser();

    @Test
    public void testSimpleIntegerAssignment() throws Exception {
        String input = "x = 42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("x", result.getVariableName());
        assertNotNull(result.getInitializer());
    }

    @Test
    public void testStringAssignment() throws Exception {
        String input = "nombre = \"Juan\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("nombre", result.getVariableName());
    }

    @Test
    public void testBooleanAssignment() throws Exception {
        String input = "activo = verdadero";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("activo", result.getVariableName());
    }

    @Test
    public void testExpressionAssignment() throws Exception {
        String input = "resultado = a + b * c";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("resultado", result.getVariableName());
        assertNotNull(result.getInitializer());
    }

    @Test
    public void testFunctionCallAssignment() throws Exception {
        String input = "datos = obtener_datos()";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("datos", result.getVariableName());
    }

    @Test
    public void testObjectLiteralAssignment() throws Exception {
        String input = "usuario = { nombre: \"Ana\", edad: 25 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("usuario", result.getVariableName());
    }

    @Test
    public void testListLiteralAssignment() throws Exception {
        String input = "numeros = [1, 2, 3, 4, 5]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("numeros", result.getVariableName());
    }

    @Test
    public void testPropertyAccessAssignment() throws Exception {
        String input = "valor = obj.propiedad";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("valor", result.getVariableName());
    }

    @Test
    public void testIndexAccessAssignment() throws Exception {
        String input = "elemento = items[0]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("elemento", result.getVariableName());
    }

    @Test
    public void testTernaryExpressionAssignment() throws Exception {
        String input = "mensaje = x > 0 ? \"positivo\" : \"negativo\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        VariableDeclarationStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("mensaje", result.getVariableName());
    }

    @Test
    public void testInvalidDeclaration_MissingEquals() throws Exception {
        String input = "x 42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidDeclaration_MissingExpression() throws Exception {
        String input = "x =";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
