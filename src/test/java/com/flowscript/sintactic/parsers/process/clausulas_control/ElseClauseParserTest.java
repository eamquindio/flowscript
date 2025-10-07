package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ElseClauseNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ElseClauseParser.
 *
 * Gramática BNF:
 * ElseClause ::= 'else' '->' IDENTIFIER
 */
public class ElseClauseParserTest {

    private ElseClauseParser parser = new ElseClauseParser();

    @Test
    public void testSimpleElseClause() throws Exception {
        String input = "else -> DefaultTask";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ElseClauseNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("DefaultTask", result.getTargetTask());
    }

    @Test
    public void testElseClauseWithLongIdentifier() throws Exception {
        String input = "else -> HandleExceptionCase";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        ElseClauseNode result = parser.parse(context);

        assertNotNull(result);
        assertEquals("HandleExceptionCase", result.getTargetTask());
    }

    @Test
    public void testInvalidElseClause_MissingArrow() throws Exception {
        String input = "else DefaultTask";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }

    @Test
    public void testInvalidElseClause_MissingTarget() throws Exception {
        String input = "else ->";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        assertThrows(Parser.ParseException.class, () -> {
            parser.parse(context);
        });
    }
}
