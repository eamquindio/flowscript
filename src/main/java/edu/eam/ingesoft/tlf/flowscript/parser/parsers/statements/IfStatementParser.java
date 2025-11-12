package edu.eam.ingesoft.tlf.flowscript.parser.parsers.statements;

import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.IParser;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserContext;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserException;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.BlockNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.IfStatementNode;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.expressions.ExpressionParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para statements condicionales (si/sino_si/sino).
 *
 * Gramática:
 * IfStatement ::= 'si' '(' Expression ')' Block ('sino_si' '(' Expression ')' Block)* ('sino' Block)?
 *
 * Ejemplo:
 * si (x > 0) {
 *     hacer_algo()
 * } sino_si (x < 0) {
 *     hacer_otra_cosa()
 * } sino {
 *     hacer_default()
 * }
 */
public class IfStatementParser implements IParser<IfStatementNode> {
    private final ExpressionParser expressionParser;
    private final BlockParser blockParser;

    public IfStatementParser() {
        this.expressionParser = new ExpressionParser();
        this.blockParser = new BlockParser();
    }

    @Override
    public IfStatementNode parse(ParserContext context) throws ParserException {
        int startLine = context.current().getLine();
        int startColumn = context.current().getColumn();

        List<IfStatementNode.ConditionalBranch> branches = new ArrayList<>();

        // Parse 'si' branch (required)
        context.expect(TokenType.SI);
        context.expect(TokenType.LEFT_PAREN);
        ExpressionNode condition = expressionParser.parse(context);
        context.expect(TokenType.RIGHT_PAREN);
        BlockNode thenBlock = blockParser.parse(context);

        branches.add(new IfStatementNode.ConditionalBranch(condition, thenBlock));

        // Parse optional 'sino_si' branches
        while (context.match(TokenType.SINO_SI)) {
            // match() already consumed 'sino_si'
            context.expect(TokenType.LEFT_PAREN);
            ExpressionNode sinoSiCondition = expressionParser.parse(context);
            context.expect(TokenType.RIGHT_PAREN);
            BlockNode sinoSiBlock = blockParser.parse(context);

            branches.add(new IfStatementNode.ConditionalBranch(sinoSiCondition, sinoSiBlock));
        }

        // Parse optional 'sino' branch (else branch with null condition)
        if (context.match(TokenType.SINO)) {
            // match() already consumed 'sino'
            BlockNode elseBlock = blockParser.parse(context);

            branches.add(new IfStatementNode.ConditionalBranch(null, elseBlock));
        }

        IfStatementNode node = new IfStatementNode(branches);
        return node;
    }
}
