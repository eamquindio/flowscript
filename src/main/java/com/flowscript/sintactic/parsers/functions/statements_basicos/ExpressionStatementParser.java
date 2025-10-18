package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.statements_basicos.ExpressionStatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para statements que consisten en una expresión.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ExpressionStatement ::= Expression
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Llamada a función
 * imprimir("Hola mundo")
 * log.info("Mensaje de log")
 * db.insert("usuarios", usuario)
 *
 * // Operaciones matemáticas (efecto secundario implícito)
 * x + y
 * contador * 2
 *
 * // Acceso a propiedades
 * usuario.nombre
 * lista[0]
 *
 * // Expresiones compuestas
 * usuario.enviar_email("Notificación")
 * http.post("api/endpoint", { dato: valor })
 *
 * // En contexto
 * function procesar() {
 *     imprimir("Inicio")           // ExpressionStatement
 *     resultado = calcular(10)     // VariableDeclaration (no ExpressionStatement)
 *     imprimir(resultado)          // ExpressionStatement
 * }
 *
 * task EnviarNotificacion {
 *     action:
 *         email.send(entrada.destino, entrada.mensaje)  // ExpressionStatement
 *         log.info("Email enviado")                      // ExpressionStatement
 *         go_to Fin                                      // GotoStatement (no ExpressionStatement)
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ExpressionStatementParser parser = new ExpressionStatementParser();
 * ExpressionStatementNode node = parser.parse(context);
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo estos pasos:
 * <ol>
 *   <li>Parsear la expresión usando ExpressionParser</li>
 *   <li>Crear y retornar ExpressionStatementNode con la expresión parseada</li>
 * </ol>
 *
 * @see ExpressionStatementNode
 */
public class ExpressionStatementParser implements IParser<ExpressionStatementNode> {

    private final ExpressionParser expressionParser;

    public ExpressionStatementParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public ExpressionStatementNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        // HINT: Simplemente parsear la expresión y envolver en ExpressionStatementNode
        throw new UnsupportedOperationException("ExpressionStatementParser no implementado - Tarea del estudiante");
    }
}
