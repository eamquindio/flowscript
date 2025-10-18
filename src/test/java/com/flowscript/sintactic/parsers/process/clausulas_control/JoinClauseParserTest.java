package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.JoinClauseNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para JoinClauseParser.
 *
 * Gramática BNF:
 * JoinClause ::= 'join' '->' IDENTIFIER
 */
public class JoinClauseParserTest {

    private JoinClauseParser parser = new JoinClauseParser();

    @Test
    public void testSimpleJoinClause() throws Exception {
        String input = "join -> ContinueTask";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        JoinClauseNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("ContinueTask", result.getTargetTask());
    }

    @Test
    public void testJoinClauseWithComplexName() throws Exception {
        String input = "join -> ConsolidateResults";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        JoinClauseNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("ConsolidateResults", result.getTargetTask());
    }

    @Test
    public void testInvalidJoinClause_MissingArrow() throws Exception {
        String input = "join NextTask";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidJoinClause_MissingTarget() throws Exception {
        String input = "join ->";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
