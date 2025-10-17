package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ProgramParser.
 *
 * Gramática BNF:
 * Program ::= Declaration*
 */
public class ProgramParserTest {

    private ProgramParser parser = new ProgramParser();

    @Test
    public void testEmptyProgram() throws Exception {
        String input = "";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ProgramNode result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.getDeclarations().isEmpty());
    }

    @Test
    public void testProgramWithSingleImport() throws Exception {
        String input = "import \"std/http\" as http";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ProgramNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.getDeclarations().size());
    }

    @Test
    public void testProgramWithSingleFunction() throws Exception {
        String input = "function main() { print(\"Hello\") }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ProgramNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(1, result.getDeclarations().size());
    }

    @Test
    public void testProgramWithMultipleDeclarations() throws Exception {
        String input = "import \"std/http\" as http\n\n" +
                "x = 10\n\n" +
                "function add(a: integer, b: integer) -> integer { return a + b }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ProgramNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.getDeclarations().size());
    }

    @Test
    public void testCompleteProgram() throws Exception {
        String input = "import \"std/http\" as http\n" +
                "import \"std/json\" as json\n\n" +
                "API_URL = \"https://api.example.com\"\n\n" +
                "function get_data(id: integer) -> object {\n" +
                "    resp = http.get(API_URL + \"/items/\" + id)\n" +
                "    return json.parse(resp.body)\n" +
                "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ProgramNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals(4, result.getDeclarations().size());
    }
}
