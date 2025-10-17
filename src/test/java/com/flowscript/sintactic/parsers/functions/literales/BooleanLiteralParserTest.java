package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.literales.BooleanLiteralNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para BooleanLiteralParser.
 *
 * Gramática BNF:
 * BooleanLiteral ::= 'true' | 'false'
 */
public class BooleanLiteralParserTest {

  private BooleanLiteralParser parser = new BooleanLiteralParser();

  @Test
  public void testTrueLiteral() throws Exception {
    String input = "true";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    BooleanLiteralNode result = parser.parse(context);

    assertNotNull(result);
    assertTrue(result.getValue());
  }

  @Test
  public void testFalseLiteral() throws Exception {
    String input = "false";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    BooleanLiteralNode result = parser.parse(context);

    assertNotNull(result);
    assertFalse(result.getValue());
  }

  @Test
  public void testInvalidInput_Identifier() throws Exception {
    String input = "maybe";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    assertThrows(Parser.ParseException.class, () -> {
      parser.parse(context);
    });
  }

  @Test
  public void testInvalidInput_Integer() throws Exception {
    String input = "1";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    assertThrows(Parser.ParseException.class, () -> {
      parser.parse(context);
    });
  }

  @Test
  public void testInvalidInput_CapitalizedTrue() throws Exception {
    String input = "True";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    assertThrows(Parser.ParseException.class, () -> {
      parser.parse(context);
    });
  }
}
