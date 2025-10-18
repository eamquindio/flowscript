package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.PrimaryExpressionNode;

/**
 * Parser para expresiones primarias.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * PrimaryExpression ::= IDENTIFIER
 *                     | Literal
 *                     | '(' Expression ')'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 11: Expresiones - Primarias (más alta precedencia)
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Las expresiones primarias son los bloques básicos de construcción:
 * - Identificadores (variables, funciones)
 * - Literales (números, cadenas, booleanos, etc.)
 * - Expresiones entre paréntesis
 *
 * @see PrimaryExpressionNode
 */
public class PrimaryExpressionParser implements IParser<PrimaryExpressionNode> {

    @Override
    public PrimaryExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        throw new UnsupportedOperationException("PrimaryExpressionParser no implementado - Tarea del estudiante");
    }
}