package edu.eam.ingesoft.tlf.flowscript.parser.parsers;

import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.*;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.declarations.FunctionDeclarationParser;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.declarations.ProcessDeclarationParser;
import java.util.*;

public class ProgramParser implements IParser<ProgramNode> {
    private final FunctionDeclarationParser functionParser = new FunctionDeclarationParser();
    private final ProcessDeclarationParser processParser = new ProcessDeclarationParser();

    @Override
    public ProgramNode parse(ParserContext context) {
        List<DeclarationNode> declarations = new ArrayList<>();

        while (!context.isAtEnd()) {
            declarations.add(parseDeclaration(context));
        }

        return new ProgramNode(declarations);
    }

    private DeclarationNode parseDeclaration(ParserContext context) {
        if (context.check(TokenType.FUNCION)) {
            return functionParser.parse(context);
        }

        if (context.check(TokenType.PROCESO)) {
            return processParser.parse(context);
        }

        throw new ParserException("Expected declaration (funcion or proceso)", context.current());
    }
}
