package edu.eam.ingesoft.tlf.flowscript.parser.parsers.statements;

import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import java.util.*;

public class BlockParser implements IParser<BlockNode> {
    private final StatementParser statementParser = new StatementParser();

    @Override
    public BlockNode parse(ParserContext context) {
        context.expect(TokenType.LEFT_BRACE);
        List<StatementNode> statements = new ArrayList<>();

        while (!context.check(TokenType.RIGHT_BRACE) && !context.isAtEnd()) {
            statements.add(statementParser.parse(context));
        }

        context.expect(TokenType.RIGHT_BRACE);
        return new BlockNode(statements);
    }
}
