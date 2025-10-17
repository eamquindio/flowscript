package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ThrowStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ThrowStatementParser.
 *
 * Gramática BNF:
 * ThrowStatement ::= 'throw' Expression
 */
public class ThrowStatementParserTest {

    private ThrowStatementParser parser = new ThrowStatementParser();

    @Test
    public void testThrowWithObject() throws Exception {
        String input = "throw { type: \"Error\", message: \"Something went wrong\" }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ThrowStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getExpression());
    }

    @Test
    public void testThrowWithString() throws Exception {
        String input = "throw \"Error occurred\"";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ThrowStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getExpression());
    }

    @Test
    public void testThrowWithVariable() throws Exception {
        String input = "throw error";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ThrowStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getExpression());
    }
}
