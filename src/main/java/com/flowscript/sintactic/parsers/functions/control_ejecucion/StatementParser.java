package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.parsers.functions.control_flujo.ForStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.IfStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ReturnStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.ThrowStatementParser;
import com.flowscript.sintactic.parsers.functions.control_flujo.TryStatementParser;
import com.flowscript.sintactic.parsers.functions.statements_basicos.ExpressionStatementParser;
import com.flowscript.sintactic.parsers.functions.statements_basicos.VariableDeclarationStatementParser;
import com.flowscript.sintactic.parsers.process.navegacion.GotoStatementParser;

public class StatementParser implements IParser<StatementNode> {
  private static final IfStatementParser IF_PARSER = new IfStatementParser();
  private static final TryStatementParser TRY_PARSER = new TryStatementParser();
  private static final ThrowStatementParser THROW_PARSER = new ThrowStatementParser();
  private static final ReturnStatementParser RETURN_PARSER = new ReturnStatementParser();
  private static final GotoStatementParser GOTO_PARSER = new GotoStatementParser();
  private static final ForStatementParser FOR_PARSER = new ForStatementParser();
  private static final VariableDeclarationStatementParser VAR_PARSER = new VariableDeclarationStatementParser();
  private static final BlockParser BLOCK_PARSER = new BlockParser();
  private static final ExpressionStatementParser EXPR_PARSER = new ExpressionStatementParser();

  @Override
  public StatementNode parse(ParserContext context) throws ParseException {
    if (context.check(TokenType.LEFT_BRACE))
      return BLOCK_PARSER.parse(context);
    if (context.check(TokenType.IF))
      return IF_PARSER.parse(context);
    if (context.check(TokenType.TRY))
      return TRY_PARSER.parse(context);
    if (context.check(TokenType.THROW))
      return THROW_PARSER.parse(context);
    if (context.check(TokenType.RETURN))
      return RETURN_PARSER.parse(context);
    if (context.check(TokenType.GOTO))
      return GOTO_PARSER.parse(context);
    if (context.check(TokenType.FOR))
      return FOR_PARSER.parse(context);
    if (context.check(TokenType.IDENTIFIER)) {
      Token nextToken = context.peek(1);
      if (nextToken.getType() == TokenType.ASSIGN) {
        return VAR_PARSER.parse(context);
      }
    }

    return EXPR_PARSER.parse(context);
  }
}