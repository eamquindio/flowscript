package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.RelationalExpressionNode;

/**
 * Parser para expresiones relacionales.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * RelationalExpression ::= AdditiveExpression ( ( '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ) AdditiveExpression )*
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 5: Expresiones - Relacionales
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Soporta operadores &lt;, &gt;, &lt;=, &gt;= (menor, mayor, menor o igual, mayor o igual).
 * El operador es asociativo por la izquierda.
 *
 * @see RelationalExpressionNode
 */
public class RelationalExpressionParser implements IParser<RelationalExpressionNode> {

    @Override
    public RelationalExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        throw new UnsupportedOperationException("RelationalExpressionParser no implementado - Tarea del estudiante");
    }
}
