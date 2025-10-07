package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para listas de parámetros de función.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ParameterList ::= Parameter ( ',' Parameter )*
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Un solo parámetro
 * x: entero
 *
 * // Múltiples parámetros
 * x: entero, y: decimal, nombre: texto
 *
 * // En contexto de funciones
 * function sumar(a: entero, b: entero) -> entero {
 *     return a + b
 * }
 *
 * function crear_reporte(
 *     titulo: texto,
 *     datos: lista,
 *     incluir_graficos: booleano,
 *     formato: texto
 * ) -> objeto {
 *     return {
 *         titulo: titulo,
 *         contenido: datos,
 *         graficos: incluir_graficos,
 *         tipo: formato
 *     }
 * }
 *
 * // Función con muchos parámetros
 * function procesar_transaccion(
 *     id: entero,
 *     usuario: objeto,
 *     monto: decimal,
 *     moneda: texto,
 *     descripcion: texto,
 *     metadata: objeto
 * ) -> booleano {
 *     // lógica de procesamiento
 *     return verdadero
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ParameterListParser parser = new ParameterListParser();
 * List&lt;ParameterNode&gt; params = parser.parse(context);
 * </pre>
 *
 * <h3>Nota:</h3>
 * Este parser no implementa IParser porque retorna una lista, no un nodo AST.
 *
 * @see ParameterNode
 * @see ParameterParser
 */
public class ParameterListParser {

    private final ParameterParser parameterParser;

    public ParameterListParser() {
        this.parameterParser = new ParameterParser();
    }

    public List<ParameterNode> parse(ParserContext context) throws Parser.ParseException {
        List<ParameterNode> parameters = new ArrayList<>();

        // Parse primer parámetro
        ParameterNode firstParam = parameterParser.parse(context);
        parameters.add(firstParam);

        // Parse parámetros adicionales (separados por coma)
        while (context.checkValue(",")) {
            context.consume(); // consume ','
            ParameterNode param = parameterParser.parse(context);
            parameters.add(param);
        }

        return parameters;
    }
}
