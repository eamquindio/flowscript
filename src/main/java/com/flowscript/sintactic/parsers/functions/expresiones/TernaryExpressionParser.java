package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.TernaryExpressionNode;

/**
 * Parser para expresiones ternarias (operador condicional).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * TernaryExpression ::= LogicalOrExpression ( '?' Expression ':' Expression )?
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 1: Expresiones - Ternarias (más baja precedencia)
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * El operador ternario tiene la forma: condición ? valor_si_verdadero : valor_si_falso
 *
 * @see TernaryExpressionNode
 */
public class TernaryExpressionParser implements IParser<TernaryExpressionNode> {

    @Override
    public TernaryExpressionNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        throw new UnsupportedOperationException("TernaryExpressionParser HACE PARTE DE FUCTIONS NO CORRESPONDE" +
                "SI SIGO SE ALARGARA A MUCHO MAS ARCHIVOS");
    }
}
