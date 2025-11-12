package edu.eam.ingesoft.tlf.flowscript.parser.parsers.process;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.IParser;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserContext;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserException;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.EndElementNode;

/**
 * Parser para elemento de fin de proceso.
 *
 * Gramática:
 * EndElement ::= 'fin' IDENTIFIER
 *
 * Ejemplo:
 * fin ProcesoCompletado
 */
public class EndElementParser implements IParser<EndElementNode> {

    @Override
    public EndElementNode parse(ParserContext context) throws ParserException {
        int startLine = context.current().getLine();
        int startColumn = context.current().getColumn();

        // Parse 'fin'
        context.expect(TokenType.FIN);

        // Parse end element name
        Token nameToken = context.expect(TokenType.IDENTIFIER);
        String endName = nameToken.getLexeme();

        EndElementNode node = new EndElementNode(endName);
        return node;
    }
}
