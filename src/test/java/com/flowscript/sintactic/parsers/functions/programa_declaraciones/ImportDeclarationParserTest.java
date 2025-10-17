package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ImportDeclarationNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ImportDeclarationParser.
 *
 * Gramática BNF:
 * ImportDeclaration ::= 'import' STRING_LITERAL ( 'as' IDENTIFIER )?
 * | 'import_jar' STRING_LITERAL 'as' IDENTIFIER
 */
public class ImportDeclarationParserTest {

  private ImportDeclarationParser parser = new ImportDeclarationParser();

  @Test
  public void testSimpleImport() throws Exception {
    String input = "import \"std/http\"";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    ImportDeclarationNode result = parser.parse(context);

    assertNotNull(result);
    assertEquals("\"std/http\"", result.getModulePath());
    assertNull(result.getAlias());
  }

  @Test
  public void testImportWithAlias() throws Exception {
    String input = "import \"std/http\" as http";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    ImportDeclarationNode result = parser.parse(context);

    assertNotNull(result);
    assertEquals("\"std/http\"", result.getModulePath());
    assertEquals("http", result.getAlias());
  }

  @Test
  public void testImportJar() throws Exception {
    String input = "import_jar \"libs/guava.jar\" as guava";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    ImportDeclarationNode result = parser.parse(context);

    assertNotNull(result);
    assertEquals("\"libs/guava.jar\"", result.getModulePath());
    assertEquals("guava", result.getAlias());
    assertTrue(result.isJarImport());
  }

  @Test
  public void testMultipleImports() throws Exception {
    String input = "import \"std/json\" as json\nimport \"std/db\" as db";
    Lexer lexer = new Lexer(input);
    List<Token> tokens = lexer.tokenize();
    ParserContext context = new ParserContext(tokens);

    ImportDeclarationNode result1 = parser.parse(context);
    assertNotNull(result1);
    assertEquals("json", result1.getAlias());

    ImportDeclarationNode result2 = parser.parse(context);
    assertNotNull(result2);
    assertEquals("db", result2.getAlias());
  }
}
