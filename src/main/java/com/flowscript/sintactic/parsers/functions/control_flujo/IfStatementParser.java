package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_flujo.IfStatementNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

public class IfStatementParser {

    // Método principal: toma el código fuente y lo parsea directo a un nodo de if
    public static IfStatementNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.IfStatementContext ctx = p.ifStatement();
        return (IfStatementNode) new ProgramParser.FunctionsAstVisitor().visitIfStatement(ctx);
    }
}