package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.TryStatementNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.BlockParser;

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

        Token tryToken = context.getCurrentToken();
        if (!tryToken.getValue().equals("try") && !tryToken.getValue().equals("intentar")) {
            throw new Parser.ParseException("Expected 'try' or 'intentar'");
        }
        context.consume();

        var tryBlock = blockParser.parse(context);

        Token catchToken = context.getCurrentToken();
        if (!catchToken.getValue().equals("catch") && !catchToken.getValue().equals("capturar")) {
            throw new Parser.ParseException("Expected 'catch' or 'capturar'");
        }
        context.consume();

        if (!context.getCurrentToken().getValue().equals("(")) {
            throw new Parser.ParseException("Expected '(' after 'catch'");
        }
        context.consume();

        Token identifierToken = context.getCurrentToken();
        if (identifierToken.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException("Expected identifier inside catch parentheses");
        }
        String exceptionVarName = identifierToken.getValue();
        context.consume();

        if (!context.getCurrentToken().getValue().equals(")")) {
            throw new Parser.ParseException("Expected ')' after exception identifier");
        }
        context.consume();

        var catchBlock = blockParser.parse(context);

        return new TryStatementNode(tryToken, tryBlock, exceptionVarName, catchBlock);
    }
}
