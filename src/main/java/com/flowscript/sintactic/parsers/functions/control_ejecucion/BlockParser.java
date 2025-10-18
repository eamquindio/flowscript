package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

/**
 * Parser para un bloque de código.
 */
public class BlockParser {

    public static BlockNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.BlockContext ctx = p.block();
        return (BlockNode) new ProgramParser.FunctionsAstVisitor().visitBlock(ctx);
    }
}
