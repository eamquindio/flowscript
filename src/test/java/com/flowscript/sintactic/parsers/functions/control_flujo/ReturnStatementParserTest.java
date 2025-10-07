package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ReturnStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ReturnStatementParser.
 *
 * Gramática BNF:
 * ReturnStatement ::= 'return' Expression?
 */
public class ReturnStatementParserTest {

    private ReturnStatementParser parser = new ReturnStatementParser();

    @Test
    public void testReturnWithValue() throws Exception {
        String input = "return 42";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ReturnStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getExpression());
    }

    @Test
    public void testReturnWithExpression() throws Exception {
        String input = "return x + y";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ReturnStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getExpression());
    }

    @Test
    public void testReturnWithFunctionCall() throws Exception {
        String input = "return calculate(a, b)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ReturnStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getExpression());
    }

    @Test
    public void testReturnVoid() throws Exception {
        String input = "return";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ReturnStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNull(result.getExpression());
    }
}
