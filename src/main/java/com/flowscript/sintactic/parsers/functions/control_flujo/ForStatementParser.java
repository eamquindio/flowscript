package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;

/**
 * Parser para bucles for-each.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ForStatement ::= 'for' 'each' IDENTIFIER 'in' Expression Statement
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // For-each básico
 * for each numero in [1, 2, 3, 4, 5] {
 *     imprimir(numero)
 * }
 *
 * // For-each con variable
 * numeros = [10, 20, 30]
 * for each n in numeros {
 *     imprimir(n * 2)
 * }
 *
 * // For-each con objetos
 * usuarios = [
 *     { nombre: "Juan", edad: 25 },
 *     { nombre: "Ana", edad: 30 }
 * ]
 * for each usuario in usuarios {
 *     imprimir(usuario.nombre + " tiene " + usuario.edad + " años")
 * }
 *
 * // For-each con strings
 * texto = "Hola"
 * for each letra in texto {
 *     imprimir(letra)
 * }
 *
 * // For-each anidado
 * matriz = [[1, 2], [3, 4]]
 * for each fila in matriz {
 *     for each numero in fila {
 *         imprimir(numero)
 *     }
 * }
 *
 * // For-each en tarea
 * task NotificarUsuarios {
 *     action:
 *         for each usuario in entrada.usuarios {
 *             email.send(usuario.email, "Notificación")
 *         }
 *         go_to FinNotificaciones
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ForStatementParser parser = new ForStatementParser();
 * ForStatementNode node = parser.parse(context);
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo estos pasos:
 * <ol>
 *   <li>Consumir 'for' o 'para'</li>
 *   <li>Consumir 'each' o 'cada'</li>
 *   <li>Consumir IDENTIFIER (variable de iteración)</li>
 *   <li>Consumir 'in' o 'en'</li>
 *   <li>Parsear la expresión iterable usando ExpressionParser</li>
 *   <li>Parsear el statement del cuerpo usando StatementParser</li>
 *   <li>Crear y retornar ForStatementNode</li>
 * </ol>
 *
 * @see ForStatementNode
 */
public class ForStatementParser implements IParser<ForStatementNode> {

    private final ExpressionParser expressionParser;
    private final StatementParser statementParser;

    public ForStatementParser() {
        this.expressionParser = new ExpressionParser();
        this.statementParser = new StatementParser();
    }

    @Override
    public ForStatementNode parse(ParserContext context) throws Parser.ParseException {

        Token forToken = context.getCurrentToken();
        if (!(forToken.getValue().equals("for") || forToken.getValue().equals("para"))) {
            throw new Parser.ParseException("Expected 'for' or 'para'");
        }
        context.consume();

        Token eachToken = context.getCurrentToken();
        if (!(eachToken.getValue().equals("each") || eachToken.getValue().equals("cada"))) {
            throw new Parser.ParseException("Expected 'each' or 'cada'");
        }
        context.consume();

        Token iteratorToken = context.getCurrentToken();
        if (iteratorToken.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException("Expected an identifier after 'each'");
        }
        String iteratorName = iteratorToken.getValue();
        context.consume();

        Token inToken = context.getCurrentToken();
        if (!(inToken.getValue().equals("in") || inToken.getValue().equals("en"))) {
            throw new Parser.ParseException("Expected 'in' o 'en'");
        }
        context.consume();

        ExpressionNode iterable = expressionParser.parse(context);

        StatementNode body = statementParser.parse(context);

        return new ForStatementNode(forToken, iteratorName, iterable, body);
    }
}
