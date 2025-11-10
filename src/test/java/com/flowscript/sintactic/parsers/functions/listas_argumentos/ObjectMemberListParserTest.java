package com.flowscript.sintactic.parsers.functions.listas_argumentos;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ObjectMemberListParser.
 *
 * Gramática BNF:
 * ObjectMemberList ::= ObjectMember ( ',' ObjectMember )*
 */
public class ObjectMemberListParserTest {

    private ObjectMemberListParser parser = new ObjectMemberListParser();

    @Test
    public void testSingleMember() throws Exception {
        String input = "nombre: \"Ana\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.getMembers().size());
    }

    @Test
    public void testTwoMembers() throws Exception {
        String input = "nombre: \"Ana\", edad: 25";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getMembers().size());
    }

    @Test
    public void testMultipleMembers() throws Exception {
        String input = "id: 1, nombre: \"Ana\", edad: 25, activo: verdadero, saldo: 1500.50";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(5, result.getMembers().size());
    }

    @Test
    public void testMixedValueTypes() throws Exception {
        String input = "numero: 42, mensaje: \"mensaje\", activo: verdadero, precio: 3.14, vac: nulo";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(5, result.getMembers().size());
    }

    @Test
    public void testNestedObject() throws Exception {
        String input = "nombre: \"Ana\", direccion: { calle: \"Principal\", numero: 123 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getMembers().size());
    }

    @Test
    public void testListAsMember() throws Exception {
        String input = "nombre: \"Ana\", telefonos: [\"123\", \"456\", \"789\"]";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getMembers().size());
    }

    @Test
    public void testExpressionAsValue() throws Exception {
        String input = "suma: a + b, producto: x * y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getMembers().size());
    }

    @Test
    public void testFunctionCallAsValue() throws Exception {
        String input = "datos: obtener_datos(), fecha: ahora()";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getMembers().size());
    }

    @Test
    public void testComplexNestedStructure() throws Exception {
        String input = "usuario: { nombre: \"Ana\", datos: { edad: 25, ciudad: \"Bogotá\" } }, activo: verdadero";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ObjectMemberListNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.getMembers().size());
    }

    @Test
    public void testInvalidMembers_TrailingComma() throws Exception {
        String input = "nombre: \"Ana\", edad: 25,";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidMembers_MissingValue() throws Exception {
        String input = "nombre:";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
