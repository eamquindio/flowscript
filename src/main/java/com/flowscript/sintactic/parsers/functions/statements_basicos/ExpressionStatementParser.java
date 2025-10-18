package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.sintactic.ast.functions.statements_basicos.ExpressionStatementNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

public class ExpressionStatementParser {

    // Método principal: toma el código fuente y lo parsea a un nodo de statement de expresión
    public static ExpressionStatementNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.ExpressionStatementContext ctx = p.expressionStatement();
        return (ExpressionStatementNode) new ProgramParser.FunctionsAstVisitor().visitExpressionStatement(ctx);
    }
}
