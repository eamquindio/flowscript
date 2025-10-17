package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;

public class StatementListParser {
  private static final StatementParser STMT_PARSER = new StatementParser();

  public List<StatementNode> parse(ParserContext context) throws ParseException {
    List<StatementNode> stmts = new ArrayList<>();

    while (context.hasMoreTokens() && !isBlockEnd(context)) {
      stmts.add(STMT_PARSER.parse(context));
    }

    return stmts;
  }

  private boolean isBlockEnd(ParserContext context) {
    return context.check(TokenType.RIGHT_BRACE) || context.check(TokenType.EOF);
  }
}
