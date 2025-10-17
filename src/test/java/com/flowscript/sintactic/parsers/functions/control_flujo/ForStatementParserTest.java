package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ForStatementParser.
 *
 * Gramática BNF:
 * ForStatement ::= 'for' 'each' IDENTIFIER 'in' Expression Statement
 */
public class ForStatementParserTest {

  private ForStatementParser parser = new ForStatementParser();

  @Test
  public void testSimpleForLoop() throws Exception {
    String input = "for each item in items { print(item) }";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    ForStatementNode result = parser.parse(context);

    assertNotNull(result);
    assertEquals("item", result.getIteratorVariable());
    assertNotNull(result.getIterable());
    assertNotNull(result.getBody());
  }

  @Test
  public void testForLoopWithArray() throws Exception {
    String input = "for each x in [1, 2, 3] { sum = sum + x }";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    ForStatementNode result = parser.parse(context);

    assertNotNull(result);
    assertEquals("x", result.getIteratorVariable());
  }

  @Test
  public void testForLoopWithMultipleStatements() throws Exception {
    String input = "for each person in people { name = person.name\n print(name) }";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    ForStatementNode result = parser.parse(context);

    assertNotNull(result);
  }

  @Test
  public void testNestedForLoop() throws Exception {
    String input = "for each row in matrix { for each col in row { print(col) } }";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    ForStatementNode result = parser.parse(context);

    assertNotNull(result);
  }
}
