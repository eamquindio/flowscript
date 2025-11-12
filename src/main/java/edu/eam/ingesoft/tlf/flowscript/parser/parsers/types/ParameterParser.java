package edu.eam.ingesoft.tlf.flowscript.parser.parsers.types;

import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;

public class ParameterParser implements IParser<ParameterNode> {
    private final TypeParser typeParser = new TypeParser();

    @Override
    public ParameterNode parse(ParserContext context) {
        String name = context.expect(TokenType.IDENTIFIER).getLexeme();
        context.expect(TokenType.COLON);
        TypeNode type = typeParser.parse(context);
        
        return new ParameterNode(name, type);
    }
}
