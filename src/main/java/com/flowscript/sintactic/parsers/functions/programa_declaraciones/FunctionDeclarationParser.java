package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

public class FunctionDeclarationParser {

    // Método principal: toma el código fuente y lo parsea directo a un nodo de declaración de función
    public static FunctionDeclarationNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.FunctionDeclarationContext ctx = p.functionDeclaration();
        return (FunctionDeclarationNode) new ProgramParser.FunctionsAstVisitor().visitFunctionDeclaration(ctx);
    }
}
