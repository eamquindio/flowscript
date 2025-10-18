package com.flowscript.sintactic.parsers.process.estructura_principal;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.ASTNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ProcessBodyParser.
 *
 * Gramática BNF:
 * ProcessBody ::= ProcessElement*
 * ProcessElement ::= StartElement | TaskElement | GatewayElement | EndElement
 */
public class ProcessBodyParserTest {

    private ProcessBodyParser parser = new ProcessBodyParser();

    @Test
    public void testEmptyProcessBody() throws Exception {
        String input = "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ASTNode> result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testProcessBodyWithStartAndEnd() throws Exception {
        String input = "start -> Task1\n end Task1";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ASTNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testProcessBodyWithStartTaskEnd() throws Exception {
        String input = "start -> Init\n task Init { action: go_to Finish }\n end Finish";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ASTNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testProcessBodyWithMultipleTasks() throws Exception {
        String input = "start -> T1\n task T1 { action: go_to T2 }\n task T2 { action: go_to End }\n end End";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ASTNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(4, result.size());
    }

    @Test
    public void testProcessBodyWithGateway() throws Exception {
        String input = "start -> Check\n" +
                      "task Check { action: gateway G { when x > 0 -> Pos\n else -> Neg } }\n" +
                      "end Pos\n" +
                      "end Neg";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ASTNode> result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.size() >= 3);
    }

    @Test
    public void testProcessBodyWithParallelGateway() throws Exception {
        String input = "start -> Fork\n" +
                      "gateway Fork parallel { branch -> T1\n branch -> T2\n join -> Merge }\n" +
                      "end T1\n" +
                      "end T2\n" +
                      "task Merge { action: go_to End }\n" +
                      "end End";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ASTNode> result = parser.parse(context);

        assertNotNull(result);
        assertTrue(result.size() >= 4);
    }

    @Test
    public void testProcessBodyWithSpanishKeywords() throws Exception {
        String input = "inicio -> Tarea1\n tarea Tarea1 { accion: go_to Fin }\n fin Fin";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        List<ASTNode> result = parser.parse(context);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testInvalidProcessBody_UnexpectedToken() throws Exception {
        String input = "invalid_keyword";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
