package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ReturnStatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.ast.expressions.ExpressionNode;
/**
 * Parser para statements return.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ReturnStatement ::= 'return' Expression?
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Return sin valor (void)
 * function saludar() {
 *     imprimir("Hola")
 *     return
 * }
 *
 * // Return con valor simple
 * function obtener_pi() -> decimal {
 *     return 3.14159
 * }
 *
 * // Return con expresión
 * function sumar(a: entero, b: entero) -> entero {
 *     return a + b
 * }
 *
 * // Return condicional
 * function es_par(n: entero) -> booleano {
 *     if n % 2 == 0 {
 *         return verdadero
 *     } else {
 *         return falso
 *     }
 * }
 *
 * // Return con objeto
 * function crear_punto(x: decimal, y: decimal) -> objeto {
 *     return { x: x, y: y }
 * }
 *
 * // Return anticipado (early return)
 * function validar_usuario(usuario: objeto) -> booleano {
 *     if usuario == nulo {
 *         return falso
 *     }
 *     if usuario.nombre == "" {
 *         return falso
 *     }
 *     return verdadero
 * }
 *
 * // Return recursivo
 * function factorial(n: entero) -> entero {
 *     if n <= 1 {
 *         return 1
 *     }
 *     return n * factorial(n - 1)
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ReturnStatementParser parser = new ReturnStatementParser();
 * ReturnStatementNode node = parser.parse(context);
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo estos pasos:
 * <ol>
 *   <li>Guardar el token 'return' o 'retornar'</li>
 *   <li>Consumir 'return' o 'retornar'</li>
 *   <li>Verificar si hay una expresión después del return (no es '}', no es EOF)</li>
 *   <li>Si hay expresión, parsearla usando ExpressionParser</li>
 *   <li>Si no hay expresión, dejar como null</li>
 *   <li>Crear y retornar ReturnStatementNode con la expresión opcional</li>
 * </ol>
 *
 * @see ReturnStatementNode
 */
public class ReturnStatementParser implements IParser<ReturnStatementNode> {

    private final ExpressionParser expressionParser;

    public ReturnStatementParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public ReturnStatementNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        // HINT: Seguir los pasos documentados arriba
        // HINT: El return puede no tener expresión (return void)
        Token returnToken;
        if (context.match(TokenType.RETURN)) {
            returnToken = context.consume(TokenType.RETURN);
        } else if (context.match(TokenType.RETORNAR)) {
            returnToken = context.consume(TokenType.RETORNAR);
        } else {
            throw new Parser.ParseException("Expected 'return' or 'retornar' at line " + context.getCurrentLine());
        }

        if (context.match(TokenType.RIGHT_BRACE) || context.isAtEnd()) {
            return new ReturnStatementNode(returnToken); // return void
        }

        ExpressionNode expression = expressionParser.parse(context);

        return new ReturnStatementNode(returnToken, expression);

    }
}
