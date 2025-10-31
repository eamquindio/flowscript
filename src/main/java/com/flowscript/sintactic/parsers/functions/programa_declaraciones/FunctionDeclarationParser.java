package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

public class FunctionDeclarationParser {

    public static FunctionDeclarationNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.FunctionDeclarationContext ctx = p.functionDeclaration();
        return (FunctionDeclarationNode) new ProgramParser.FunctionsAstVisitor().visitFunctionDeclaration(ctx);
    }
}
