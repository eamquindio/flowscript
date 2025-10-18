package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

/**
 * Parser para un solo parámetro.
 */
public class ParameterParser {

    public static ParameterNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.ParameterContext ctx = p.parameter();
        return (ParameterNode) new ProgramParser.FunctionsAstVisitor().visitParameter(ctx);
    }
}
