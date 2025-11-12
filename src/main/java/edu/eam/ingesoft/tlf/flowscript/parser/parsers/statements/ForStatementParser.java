package edu.eam.ingesoft.tlf.flowscript.parser.parsers.statements;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.IParser;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserContext;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserException;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.BlockNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.ForStatementNode;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.expressions.ExpressionParser;

/**
 * Parser para statements de iteración (para...en).
 *
 * Gramática:
 * ForStatement ::= 'para' IDENTIFIER 'en' Expression Block
 *
 * Ejemplo:
 * para elemento en lista {
 *     imprimir(elemento)
 * }
 */
public class ForStatementParser implements IParser<ForStatementNode> {
    private final ExpressionParser expressionParser;
    private final BlockParser blockParser;

    public ForStatementParser() {
        this.expressionParser = new ExpressionParser();
        this.blockParser = new BlockParser();
    }

    @Override
    public ForStatementNode parse(ParserContext context) throws ParserException {
        int startLine = context.current().getLine();
        int startColumn = context.current().getColumn();

        // Parse 'para'
        context.expect(TokenType.PARA);

        // Parse iterator variable (IDENTIFIER)
        Token iteratorToken = context.expect(TokenType.IDENTIFIER);
        String iteratorVariable = iteratorToken.getLexeme();

        // Parse 'en'
        context.expect(TokenType.EN);

        // Parse iterable expression
        ExpressionNode iterable = expressionParser.parse(context);

        // Parse body block
        BlockNode body = blockParser.parse(context);

        ForStatementNode node = new ForStatementNode(iteratorVariable, iterable, body);
        return node;
    }
}
