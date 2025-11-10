package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.*;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para DeclarationParser.
 *
 * Gramática BNF:
 * Declaration ::= ImportDeclaration | FunctionDeclaration | ProcessDeclaration | VariableDeclaration
 */
public class DeclarationParserTest {

    private DeclarationParser parser = new DeclarationParser();

    @Test
    public void testImportDeclaration() throws Exception {
        String input = "importar \"std/http\" como http";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ImportDeclarationNode);
        ImportDeclarationNode importNode = (ImportDeclarationNode) result;
        assertEquals("std/http", importNode.getModulePath());
        assertEquals("http", importNode.getAlias());
    }

    @Test
    public void testImportJarDeclaration() throws Exception {
        String input = "importar_jar \"libs/crypto.jar\" como crypto";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ImportDeclarationNode);
        ImportDeclarationNode importNode = (ImportDeclarationNode) result;
        assertEquals("libs/crypto.jar", importNode.getModulePath());
        assertEquals("crypto", importNode.getAlias());
        assertTrue(importNode.isJarImport());
    }

    @Test
    public void testFunctionDeclaration() throws Exception {
        String input = "funcion sumar(a: entero, b: entero) -> entero { retornar a + b }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof FunctionDeclarationNode);
        FunctionDeclarationNode funcNode = (FunctionDeclarationNode) result;
        assertEquals("sumar", funcNode.getName());
        assertEquals(2, funcNode.getParameters().size());
    }

    @Test
    public void testProcessDeclaration() throws Exception {
        String input = "proceso MiProceso { inicio -> Fin fin Fin }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof ProcessDeclarationNode);
        ProcessDeclarationNode processNode = (ProcessDeclarationNode) result;
        assertEquals("MiProceso", processNode.getName());
    }

    @Test
    public void testVariableDeclaration() throws Exception {
        String input = "PI = 3.14159";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        DeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result instanceof VariableDeclarationNode);
        VariableDeclarationNode varNode = (VariableDeclarationNode) result;
        assertEquals("PI", varNode.getVariableName());
    }

    @Test
    public void testInvalidDeclaration_UnexpectedToken() throws Exception {
        String input = "123";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidDeclaration_EndOfInput() throws Exception {
        String input = "";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
