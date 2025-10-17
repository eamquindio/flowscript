package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ImportDeclarationNode;

public class ImportDeclarationParser implements IParser<ImportDeclarationNode> {

  @Override
  public ImportDeclarationNode parse(ParserContext context) throws ParseException {
    Token importToken = context.getCurrentToken();
    TokenType type = importToken.getType();

    boolean isJarImport;
    if (type == TokenType.IMPORT) {
      isJarImport = false;
      context.advance();
    } else if (type == TokenType.IMPORT_JAR) {
      isJarImport = true;
      context.advance();
    } else {
      throw new ParseException("Se esperaba 'import' o 'import_jar', pero se encontró: " + importToken);
    }

    String modulePath = context.consume(TokenType.STRING_LITERAL).getValue();

    String alias = null;
    if (context.check(TokenType.AS)) {
      context.consume(TokenType.AS);
      alias = context.consume(TokenType.IDENTIFIER).getValue();
    }

    return new ImportDeclarationNode(importToken, isJarImport, modulePath, alias);
  }
}