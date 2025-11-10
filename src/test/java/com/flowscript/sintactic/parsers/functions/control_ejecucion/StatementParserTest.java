package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.ast.functions.control_flujo.*;
import com.flowscript.sintactic.ast.functions.statements_basicos.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para StatementParser.
 *
 * Gramática BNF:
 * Statement ::= ExpressionStatement | IfStatement | TryStatement | ThrowStatement
 *             | ReturnStatement | GotoStatement | ForStatement | VariableDeclaration | Block
 */
public class StatementParserTest {

    private StatementParser parser = new StatementParser();

    @Test
    public void testIfStatement() throws Exception {
        String input = "si x > 10 { imprimir(\"grande\") }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof IfStatementNode);
    }

    @Test
    public void testIfElseStatement() throws Exception {
        String input = "si x > 10 { y = 1 } sino { y = 0 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof IfStatementNode);
        IfStatementNode ifStmt = (IfStatementNode) result;
        assertNotNull(ifStmt.getElseStatement());
    }

    @Test
    public void testTryStatement() throws Exception {
        String input = "intentar { x = 1 } capturar (e) { imprimir(e) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof TryStatementNode);
    }

    @Test
    public void testThrowStatement() throws Exception {
        String input = "lanzar { tipo: \"Error\", mensaje: \"Fallo\" }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ThrowStatementNode);
    }

    @Test
    public void testReturnStatement() throws Exception {
        String input = "retornar x * 2";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ReturnStatementNode);
    }

    @Test
    public void testReturnStatementWithoutValue() throws Exception {
        String input = "retornar";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ReturnStatementNode);
    }

    @Test
    public void testGotoStatement() throws Exception {
        String input = "ir_a SiguienteTarea";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        // GotoStatement is process-specific
        assertEquals("GotoStatement", result.getNodeType());
    }

    @Test
    public void testForStatement() throws Exception {
        String input = "para cada item en items { imprimir(item) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ForStatementNode);
    }

    @Test
    public void testVariableDeclaration() throws Exception {
        String input = "x = 42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof VariableDeclarationStatementNode);
        VariableDeclarationStatementNode varDecl = (VariableDeclarationStatementNode) result;
        assertEquals("x", varDecl.getVariableName());
    }

    @Test
    public void testBlock() throws Exception {
        String input = "{ x = 1 y = 2 z = 3 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof BlockNode);
        BlockNode block = (BlockNode) result;
        assertEquals(3, block.getStatements().size());
    }

    @Test
    public void testExpressionStatement_FunctionCall() throws Exception {
        String input = "imprimir(\"Hola\")";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ExpressionStatementNode);
    }

    @Test
    public void testExpressionStatement_PropertyAccess() throws Exception {
        String input = "usuario.nombre";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ExpressionStatementNode);
    }

    @Test
    public void testExpressionStatement_ArithmeticExpression() throws Exception {
        String input = "x + y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        StatementNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ExpressionStatementNode);
    }

    @Test
    public void testDistinguishVariableDeclarationFromExpressionStatement() throws Exception {
        // Variable declaration
        String input1 = "x = 42";
        Lexer lexer1 = new Lexer(input1);
        List<Token> tokens1 = lexer1.tokenize();
        ParserContext context1 = new ParserContext(tokens1);
        StatementNode result1 = parser.parse(context1);
        assertTrue(result1 instanceof VariableDeclarationStatementNode);

        // Expression statement (function call)
        String input2 = "calcular(42)";
        Lexer lexer2 = new Lexer(input2);
        List<Token> tokens2 = lexer2.tokenize();
        ParserContext context2 = new ParserContext(tokens2);
        StatementNode result2 = parser.parse(context2);
        assertTrue(result2 instanceof ExpressionStatementNode);
    }

    @Test
    public void testInvalidStatement_EndOfInput() throws Exception {
        String input = "";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
