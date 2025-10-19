package com.flowscript.sintactic.parsers.process.elementos_core;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.EndElementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para EndElementParser.
 *
 * Gramática BNF:
 * EndElement ::= 'end' IDENTIFIER
 */
public class EndElementParserTest {

    @Test
    public void testSimpleEnd() throws Exception {
        String input = "end Success";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);
    }

    @Test
    public void testEndWithUnderscoreInName() throws Exception {
        String input = "end Error_Occurred";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);
    }

    @Test
    public void testMultipleEnds() throws Exception {
        String input = "end Success";
        Lexer lexer = new Lexer(input + "\n" + "end Failure");
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);
    }
}
