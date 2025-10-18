package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ParameterListParser.
 *
 * Gramática BNF:
 * ParameterList ::= Parameter ( ',' Parameter )*
 */
public class ParameterListParserTest {

    private ParameterListParser parser = new ParameterListParser();

    @Test
    public void testSingleParameter() throws Exception {
        String input = "x: integer";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ParameterNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("x", result.get(0).getName());
    }

    @Test
    public void testMultipleParameters() throws Exception {
        String input = "x: integer, y: decimal, name: text";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ParameterNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("x", result.get(0).getName());
        assertEquals("y", result.get(1).getName());
        assertEquals("name", result.get(2).getName());
    }

    @Test
    public void testParametersWithDifferentTypes() throws Exception {
        String input = "count: integer, rate: decimal, active: boolean, items: list, config: object";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ParameterNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(5, result.size());
    }

    @Test
    public void testInvalidParameterList_MissingComma() throws Exception {
        String input = "x: integer y: decimal";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
