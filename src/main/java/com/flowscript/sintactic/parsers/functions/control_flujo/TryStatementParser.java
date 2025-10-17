package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.TryStatementNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.BlockParser;
import com.flowscript.sintactic.ast.expressions.ExpressionNode;
/**
 * Parser para statements try/catch (manejo de excepciones).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * TryStatement ::= 'try' Block 'catch' '(' IDENTIFIER ')' Block
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Try-catch básico
 * try {
 *     resultado = dividir(10, 0)
 * } catch (e) {
 *     imprimir("Error: " + e.mensaje)
 * }
 *
 * // Try-catch con múltiples operaciones
 * try {
 *     archivo = abrir("datos.txt")
 *     contenido = archivo.leer()
 *     datos = json.parse(contenido)
 *     archivo.cerrar()
 * } catch (error) {
 *     imprimir("Error al procesar archivo: " + error.mensaje)
 *     return nulo
 * }
 *
 * // Try-catch en proceso
 * task ProcesarPago {
 *     action:
 *         try {
 *             pago = http.post("api/pagos", entrada)
 *             confirmacion = http.get("api/confirmar/" + pago.id)
 *             go_to FinExitoso
 *         } catch (ex) {
 *             log.error("Pago fallido: " + ex.mensaje)
 *             go_to ReversarPago
 *         }
 * }
 *
 * // Try-catch anidado
 * try {
 *     conexion = db.conectar()
 *     try {
 *         resultado = conexion.query("SELECT * FROM usuarios")
 *     } catch (e) {
 *         imprimir("Error en query")
 *     }
 *     conexion.cerrar()
 * } catch (e) {
 *     imprimir("Error de conexión")
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * TryStatementParser parser = new TryStatementParser();
 * TryStatementNode node = parser.parse(context);
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo estos pasos:
 * <ol>
 *   <li>Consumir 'try' o 'intentar'</li>
 *   <li>Parsear el bloque try usando BlockParser</li>
 *   <li>Consumir 'catch' o 'capturar'</li>
 *   <li>Consumir '('</li>
 *   <li>Consumir IDENTIFIER (nombre de la variable de excepción)</li>
 *   <li>Consumir ')'</li>
 *   <li>Parsear el bloque catch usando BlockParser</li>
 *   <li>Crear y retornar TryStatementNode con los bloques parseados</li>
 * </ol>
 *
 * @see TryStatementNode
 */
public class TryStatementParser implements IParser<TryStatementNode> {

    private final BlockParser blockParser;

    public TryStatementParser() {
        this.blockParser = new BlockParser();
    }

    @Override
    public TryStatementNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        // HINT: Seguir los pasos documentados arriba

        Token tryToken;
        if (context.match(TokenType.TRY)) {
            tryToken = context.consume(TokenType.TRY);
        } else if (context.match(TokenType.INTENTAR)) {
            tryToken = context.consume(TokenType.INTENTAR);
        } else {
            throw new Parser.ParseException("Expected 'try' or 'intentar' at line " + context.getCurrentLine());
        }

        BlockNode tryBlock = blockParser.parse(context);

        if (!(context.match(TokenType.CATCH) || context.match(TokenType.CAPTURAR))) {
            throw new Parser.ParseException("Expected 'catch' or 'capturar' after try block at line " + context.getCurrentLine());
        }
        context.consume(context.match(TokenType.CATCH) ? TokenType.CATCH : TokenType.CAPTURAR);

        context.expect(TokenType.LEFT_PAREN, "Expected '(' after 'catch'");

        Token variableToken = context.consume(TokenType.IDENTIFIER, "Expected exception variable inside catch parentheses");
        String catchVariable = variableToken.getValue();

        context.expect(TokenType.RIGHT_PAREN, "Expected ')' after catch variable");

        BlockNode catchBlock = blockParser.parse(context);

        return new TryStatementNode(tryToken, tryBlock, catchVariable, catchBlock);
    }
}
