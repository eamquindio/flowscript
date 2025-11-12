package edu.eam.ingesoft.tlf.flowscript.parser;

/**
 * Interfaz común para todos los parsers del lenguaje FlowScript.
 *
 * Sigue el patrón de diseño Strategy, donde cada parser implementa
 * una estrategia específica para analizar una construcción gramatical.
 *
 * @param <T> Tipo del nodo AST que produce este parser
 */
@FunctionalInterface
public interface IParser<T> {
    /**
     * Analiza sintácticamente usando el contexto proporcionado y produce un nodo AST.
     *
     * @param context Contexto del parser que contiene los tokens y la posición actual
     * @return Nodo AST resultante del análisis
     * @throws ParserException Si se encuentra un error sintáctico
     */
    T parse(ParserContext context) throws ParserException;
}
