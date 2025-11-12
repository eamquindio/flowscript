package edu.eam.ingesoft.tlf.flowscript.parser.parsers.declarations;

import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.FunctionDeclarationNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.BlockNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.types.*;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.statements.BlockParser;
import java.util.*;

public class FunctionDeclarationParser implements IParser<FunctionDeclarationNode> {
    private final ParameterParser parameterParser = new ParameterParser();
    private final TypeParser typeParser = new TypeParser();
    private final BlockParser blockParser = new BlockParser();

    @Override
    public FunctionDeclarationNode parse(ParserContext context) {
        context.expect(TokenType.FUNCION);
        String name = context.expect(TokenType.IDENTIFIER).getLexeme();
        context.expect(TokenType.LEFT_PAREN);

        List<ParameterNode> parameters = new ArrayList<>();
        if (!context.check(TokenType.RIGHT_PAREN)) {
            do {
                parameters.add(parameterParser.parse(context));
            } while (context.match(TokenType.COMMA));
        }

        context.expect(TokenType.RIGHT_PAREN);
        context.expect(TokenType.ARROW);

        TypeNode returnType = typeParser.parse(context);
        BlockNode body = blockParser.parse(context);

        return new FunctionDeclarationNode(name, parameters, returnType, body);
    }
}
