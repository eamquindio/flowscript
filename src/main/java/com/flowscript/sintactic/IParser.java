package com.flowscript.sintactic;

import com.flowscript.sintactic.ast.ASTNode;

/**
 * Interface común para todos los parsers de FlowScript.
 *
 * <p>Esta interface define el contrato que todos los parsers deben cumplir,
 * facilitando la composición y reutilización de parsers siguiendo el principio
 * de "un parser por regla gramatical".</p>
 *
 * @param <T> El tipo de nodo AST que este parser produce
 */
public interface IParser<T extends ASTNode> {

    /**
     * Parsea la entrada según la regla gramatical específica de este parser.
     *
     * @param context El contexto del parser que contiene los tokens y el índice actual
     * @return El nodo AST resultante del parseo
     * @throws Parser.ParseException Si ocurre un error durante el parseo
     */
    T parse(ParserContext context) throws Parser.ParseException;
}
