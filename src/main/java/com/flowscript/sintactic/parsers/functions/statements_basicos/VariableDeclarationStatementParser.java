package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.sintactic.ast.functions.statements_basicos.VariableDeclarationStatementNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

public class VariableDeclarationStatementParser {

    public static VariableDeclarationStatementNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.VariableDeclarationStatementContext ctx = p.variableDeclarationStatement();
        return (VariableDeclarationStatementNode) new ProgramParser.FunctionsAstVisitor().visitVariableDeclarationStatement(ctx);
    }
}
