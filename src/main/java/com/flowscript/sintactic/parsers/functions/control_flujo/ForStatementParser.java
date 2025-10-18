package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

public class ForStatementParser {

    // Método principal: toma el código fuente y lo parsea a un nodo de for
    public static ForStatementNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.ForStatementContext ctx = p.forStatement();
        return (ForStatementNode) new ProgramParser.FunctionsAstVisitor().visitForStatement(ctx);
    }
}
