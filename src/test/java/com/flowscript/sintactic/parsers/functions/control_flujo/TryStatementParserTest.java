package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.TryStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para TryStatementParser.
 *
 * Gramática BNF:
 * TryStatement ::= 'try' Block 'catch' '(' IDENTIFIER ')' Block
 */
public class TryStatementParserTest {

    private TryStatementParser parser = new TryStatementParser();

    @Test
    public void testSimpleTryCatch() throws Exception {
        String input = "try { x = risky_operation() } catch (e) { print(e) }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TryStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertNotNull(result.getTryBlock());
        assertNotNull(result.getCatchBlock());
        assertEquals("e", result.getCatchVariable());
    }

    @Test
    public void testTryCatchWithMultipleStatements() throws Exception {
        String input = "try { x = 1\n y = 2 } catch (error) { log(error)\n return false }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TryStatementNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("error", result.getCatchVariable());
    }

    @Test
    public void testNestedTryCatch() throws Exception {
        String input = "try { try { x = 1 } catch (e1) { y = 2 } } catch (e2) { z = 3 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        TryStatementNode result = parser.parse(context);

        assertNotNull(result);
    }

    @Test
    public void testInvalidTry_MissingCatch() throws Exception {
        String input = "try { x = 1 }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
