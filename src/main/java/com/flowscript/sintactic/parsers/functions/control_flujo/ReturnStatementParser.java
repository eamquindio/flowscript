package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_flujo.ReturnStatementNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

public class ReturnStatementParser {

    // Método principal: agarra el código fuente y lo convierte en un nodo de return
    public static ReturnStatementNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.ReturnStatementContext ctx = p.returnStatement();
        return (ReturnStatementNode) new ProgramParser.FunctionsAstVisitor().visitReturnStatement(ctx);
    }
}
