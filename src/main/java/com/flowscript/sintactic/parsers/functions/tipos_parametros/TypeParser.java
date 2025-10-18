package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

/**
 * Parser para un tipo anotado.
 */
public class TypeParser {

    public static TypeNode parse(String source) {
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);
        FlowScriptFunctionsParser.TypeAnnotationContext ctx = p.typeAnnotation();
        return (TypeNode) new ProgramParser.FunctionsAstVisitor().visitTypeAnnotation(ctx);
    }
}
