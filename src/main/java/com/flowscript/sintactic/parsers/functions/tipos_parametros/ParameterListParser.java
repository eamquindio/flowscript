package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.sintactic.ast.functions.listas_argumentos.ParameterListNode;
import com.flowscript.sintactic.parsers.functions.programa_declaraciones.ProgramParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;

/**
 * Parser sencillo para obtener una lista de parámetros desde el código fuente.
 *
 * Básicamente:
 *   1. Crea un parser general de funciones.
 *   2. Ejecuta la regla `parameterList`.
 *   3. Convierte el resultado en un nodo AST (ParameterListNode).
 */
public class ParameterListParser {

    /**
     * Parsea el texto fuente recibido y devuelve un nodo con la lista de parámetros.
     */
    public static ParameterListNode parse(String source) {
        // Creamos un parser base a partir del código fuente
        FlowScriptFunctionsParser p = ProgramParser.buildParser(source);

        // Ejecutamos la regla 'parameterList' del parser
        FlowScriptFunctionsParser.ParameterListContext ctx = p.parameterList();

        // Visitamos el contexto y devolvemos el nodo correspondiente
        return (ParameterListNode) new ProgramParser.FunctionsAstVisitor()
                .visitParameterList(ctx);
    }
}
