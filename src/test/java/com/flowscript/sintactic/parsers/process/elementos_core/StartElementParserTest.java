package com.flowscript.sintactic.parsers.process.elementos_core;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.StartElementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para StartElementParser.
 *
 * Gramática BNF:
 * StartElement ::= 'start' '->' IDENTIFIER
 */
public class StartElementParserTest {

    @Test
    public void testSimpleStart() throws Exception {
        String input = "start -> FirstTask";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // Note: You'll need to create the actual parser
        // StartElementParser parser = new StartElementParser();
        // StartElementNode result = parser.parse(context);

        // assertNotNull(result);
        // assertEquals("FirstTask", result.getTarget());
    }

    @Test
    public void testStartWithUnderscoreInName() throws Exception {
        String input = "start -> Validate_Input";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // StartElementParser parser = new StartElementParser();
        // StartElementNode result = parser.parse(context);

        // assertNotNull(result);
        // assertEquals("Validate_Input", result.getTarget());
    }
}
