package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ThrowStatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para statements throw (lanzamiento de excepciones).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ThrowStatement ::= 'throw' Expression
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Throw con objeto literal
 * throw { tipo: "ErrorValidacion", mensaje: "Email inválido" }
 *
 * // Throw con variable
 * error = { tipo: "ErrorDB", mensaje: "No se pudo conectar" }
 * throw error
 *
 * // Throw en función de validación
 * function validar_edad(edad: entero) {
 *     if edad < 0 {
 *         throw { tipo: "ErrorValidacion", mensaje: "Edad no puede ser negativa" }
 *     }
 *     if edad > 150 {
 *         throw { tipo: "ErrorValidacion", mensaje: "Edad inválida" }
 *     }
 *     return verdadero
 * }
 *
 * // Throw en flujo condicional
 * function dividir(a: decimal, b: decimal) -> decimal {
 *     if b == 0 {
 *         throw { tipo: "ErrorMatematico", mensaje: "División por cero" }
 *     }
 *     return a / b
 * }
 *
 * // Throw con expresión compleja
 * if not usuario.activo {
 *     throw {
 *         tipo: "ErrorAutorizacion",
 *         mensaje: "Usuario " + usuario.nombre + " está inactivo",
 *         codigo: 403
 *     }
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ThrowStatementParser parser = new ThrowStatementParser();
 * ThrowStatementNode node = parser.parse(context);
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo estos pasos:
 * <ol>
 *   <li>Consumir 'throw' o 'lanzar'</li>
 *   <li>Parsear la expresión de excepción usando ExpressionParser</li>
 *   <li>Crear y retornar ThrowStatementNode con la expresión</li>
 * </ol>
 *
 * @see ThrowStatementNode
 */
public class ThrowStatementParser implements IParser<ThrowStatementNode> {

    private final ExpressionParser expressionParser;

    public ThrowStatementParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public ThrowStatementNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        // HINT: Seguir los pasos documentados arriba
        Token throwToken = context.getCurrentToken();
        context.consume();

        ExpressionNode expression = expressionParser.parse(context);
        return new ThrowStatementNode(throwToken, expression);
    }
}
