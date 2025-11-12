package edu.eam.ingesoft.tlf.flowscript.parser.parsers.process;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.IParser;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserContext;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserException;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.StartElementNode;

/**
 * Parser para elemento de inicio de proceso.
 *
 * Gramática:
 * StartElement ::= 'inicio' '->' IDENTIFIER
 *
 * Ejemplo:
 * inicio -> PrimeraTarea
 */
public class StartElementParser implements IParser<StartElementNode> {

    @Override
    public StartElementNode parse(ParserContext context) throws ParserException {
        int startLine = context.current().getLine();
        int startColumn = context.current().getColumn();

        // Parse 'inicio'
        context.expect(TokenType.INICIO);

        // Parse '->'
        context.expect(TokenType.ARROW);

        // Parse target task name
        Token targetToken = context.expect(TokenType.IDENTIFIER);
        String targetTaskName = targetToken.getLexeme();

        StartElementNode node = new StartElementNode(targetTaskName);
        return node;
    }
}
