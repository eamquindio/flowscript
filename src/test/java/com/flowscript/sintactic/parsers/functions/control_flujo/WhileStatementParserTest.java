package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.WhileStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for WhileStatementParser.
 *
 * Grammar BNF:
 * WhileStatement ::= 'while' Expression Statement
 */
public class WhileStatementParserTest {

    private WhileStatementParser parser = new WhileStatementParser();

    @Test
    public void testSimpleWhileLoop() throws Exception {
        String input = "while x < 10 { x = x + 1 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getCondition());
        assertNotNull(result.getBody());
        assertEquals("WhileStatement", result.getNodeType());
    }

    @Test
    public void testWhileLoopWithComplexCondition() throws Exception {
        String input = "while x > 0 and y < 100 { procesar() }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getCondition());
        assertNotNull(result.getBody());
    }

    @Test
    public void testWhileLoopWithBooleanLiteral() throws Exception {
        String input = "while true { check_exit() }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getCondition());
    }

    @Test
    public void testWhileLoopWithNotOperator() throws Exception {
        String input = "while not finished { continue_processing() }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getCondition());
    }

    @Test
    public void testWhileLoopWithBlockBody() throws Exception {
        String input = "while count < max { count = count + 1\n total = total + count }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getBody());
    }

    @Test
    public void testNestedWhileLoop() throws Exception {
        String input = "while i < 10 { while j < 5 { j = j + 1 } }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getBody());
    }

    @Test
    public void testWhileLoopWithFunctionCall() throws Exception {
        String input = "while has_more_data() { process_next() }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getCondition());
    }

    @Test
    public void testWhileLoopWithPropertyAccess() throws Exception {
        String input = "while item.status == \"pending\" { update_item(item) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        WhileStatementNode result = parser.parse(context);

        assertNotNull(result);
    }
}
