package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.NullLiteralNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para NullLiteralParser.
 *
 * Gramática BNF:
 * NullLiteral ::= 'null'
 */
public class NullLiteralParserTest {

    private NullLiteralParser parser = new NullLiteralParser();

    @Test
    public void testNullLiteral() throws Exception {
        String input = "null";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        NullLiteralNode result = parser.parse(context);

        assertNotNull(result);
        assertNull(result.getValue());
    }

    @Test
    public void testInvalidInput_Identifier() throws Exception {
        String input = "nil";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidInput_CapitalizedNull() throws Exception {
        String input = "Null";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidInput_Integer() throws Exception {
        String input = "0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
