package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.AdditiveExpressionNode;

/**
 * Parser para expresiones aditivas (suma y resta).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * AdditiveExpression ::= MultiplicativeExpression ( ( '+' | '-' ) MultiplicativeExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 6: Expresiones - Aditivas
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores + (suma) y - (resta).
 * El operador es asociativo por la izquierda.
 *
 * @see AdditiveExpressionNode
 */
public class AdditiveExpressionParser implements IParser<AdditiveExpressionNode> {

    @Override
    public AdditiveExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        throw new UnsupportedOperationException("AdditiveExpressionParser no implementado - Tarea del estudiante");
    }
}
