package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para StatementListParser.
 *
 * Gramática BNF:
 * StatementList ::= Statement*
 */
public class StatementListParserTest {

    private StatementListParser parser = new StatementListParser();

    @Test
    public void testEmptyStatementList() throws Exception {
        String input = "}";  // Just closing brace, no statements
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testSingleStatement() throws Exception {
        String input = "x = 42 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testMultipleStatements() throws Exception {
        String input = "x = 10 y = 20 z = x + y }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testMixedStatementTypes() throws Exception {
        String input = "nombre = \"Juan\" imprimir(nombre) si nombre != \"\" { x = 1 } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testVariableDeclarations() throws Exception {
        String input = "a = 1 b = 2 c = 3 d = 4 e = 5 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void testFunctionCallStatements() throws Exception {
        String input = "imprimir(\"Uno\") imprimir(\"Dos\") imprimir(\"Tres\") }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testIfStatements() throws Exception {
        String input = "si x > 0 { y = 1 } si y > 0 { z = 1 } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testForStatements() throws Exception {
        String input = "para cada item en items { imprimir(item) } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testReturnStatement() throws Exception {
        String input = "x = 42 retornar x }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testTryStatement() throws Exception {
        String input = "intentar { x = 1 } capturar (e) { imprimir(e) } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testNestedBlocks() throws Exception {
        // Un bloque completo { a = 1 { b = 2 } c = 3 } es UN SOLO BlockStatement
        String input = "{ a = 1 { b = 2 } c = 3 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        // El parser trata el bloque completo como UN SOLO statement
        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof BlockNode);
    }

    @Test
    public void testComplexStatementSequence() throws Exception {
        String input = """
            datos = obtener_datos()
            si datos != nulo {
                para cada item en datos {
                    imprimir(item)
                }
            }
            retornar verdadero
            }
            """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testStopsAtEOF() throws Exception {
        String input = "x = 1 y = 2";  // No closing brace, should stop at EOF
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<StatementNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
