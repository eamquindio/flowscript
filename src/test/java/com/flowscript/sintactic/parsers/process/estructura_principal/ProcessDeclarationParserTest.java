package com.flowscript.sintactic.parsers.process.estructura_principal;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ProcessDeclarationParser.
 *
 * Gramática BNF:
 * ProcessDeclaration ::= 'process' IDENTIFIER '{' ProcessBody '}'
 */
public class ProcessDeclarationParserTest {

    private ProcessDeclarationParser parser = new ProcessDeclarationParser();

    @Test
    public void testSimpleProcess() throws Exception {
        String input = "process MyProcess { start -> Task1\n task Task1 { action: go_to End }\n end End }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ProcessDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("MyProcess", result.getName());
        assertNotNull(result.getProcessElements());
    }

    @Test
    public void testProcessWithMultipleElements() throws Exception {
        String input = "process OrderValidation {\n" +
                      "    start -> ValidateInput\n" +
                      "    task ValidateInput { action: go_to CheckInventory }\n" +
                      "    task CheckInventory { action: go_to Success }\n" +
                      "    end Success\n" +
                      "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ProcessDeclarationNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("OrderValidation", result.getName());
    }

    @Test
    public void testProcessWithGateway() throws Exception {
        String input = "process Decision {\n" +
                      "    start -> Check\n" +
                      "    task Check { action: gateway G { when x > 0 -> Positive\n else -> Negative } }\n" +
                      "    end Positive\n" +
                      "    end Negative\n" +
                      "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ProcessDeclarationNode result = parser.parse(context);

        assertNotNull(result);
    }
}
