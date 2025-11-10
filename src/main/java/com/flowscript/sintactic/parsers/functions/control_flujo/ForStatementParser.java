package com.flowscript.sintactic.parsers.functions.control_flujo;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForRangeStatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;

/**
 * Parser para bucles for (both for-each and for-range).
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ForStatement ::= ForEachStatement | ForRangeStatement
 * ForEachStatement ::= 'for' 'each' IDENTIFIER 'in' Expression Statement
 * ForRangeStatement ::= 'for' IDENTIFIER 'from' Expression 'to' Expression ('step' Expression)? Statement
 * </pre>
 *
 * Note: This parser returns StatementNode (which can be either ForStatementNode or ForRangeStatementNode)
 * to handle both for-each and for-range syntaxes.
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
 * @see ForRangeStatementNode
 */
public class ForStatementParser implements IParser<StatementNode> {

    private final ExpressionParser expressionParser;
    private StatementParser statementParser;

    public ForStatementParser() {
        this.expressionParser = new ExpressionParser();
        // Lazy initialization to avoid circular dependency
    }

    private StatementParser getStatementParser() {
        if (statementParser == null) {
            statementParser = new StatementParser();
        }
        return statementParser;
    }

    @Override
    public StatementNode parse(ParserContext context) throws Parser.ParseException {
        // 1. Consume 'for'
        Token forToken = context.consume(TokenType.FOR);

        // 2. Look ahead to distinguish between for-each and for-range
        Token lookahead = context.getCurrentToken();

        if (lookahead.getType() == TokenType.EACH) {
            // This is a for-each loop
            return parseForEach(context, forToken);
        } else if (lookahead.getType() == TokenType.IDENTIFIER) {
            // This is a for-range loop (for variable from ... to ...)
            return parseForRange(context, forToken);
        } else {
            throw new Parser.ParseException(
                "Expected 'each' or identifier after 'for', got " + lookahead.getType() +
                " at line " + lookahead.getLine()
            );
        }
    }

    /**
     * Parse for-each loop: for each item in collection { ... }
     */
    private ForStatementNode parseForEach(ParserContext context, Token forToken) throws Parser.ParseException {
        // 1. Consume 'each'
        context.consume(TokenType.EACH);

        // 2. Consume IDENTIFIER (iterator variable)
        Token iteratorToken = context.consume(TokenType.IDENTIFIER);
        String iteratorVariable = iteratorToken.getValue();

        // 3. Consume 'in'
        context.consume(TokenType.IN);

        // 4. Parse iterable expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode iterable =
            expressionParser.parse(context);

        // 5. Parse body statement
        StatementNode body = getStatementParser().parse(context);

        // 6. Create and return ForStatementNode
        return new ForStatementNode(forToken, iteratorVariable, iterable, body);
    }

    /**
     * Parse for-range loop: for i from 1 to 10 step 2 { ... }
     */
    private ForRangeStatementNode parseForRange(ParserContext context, Token forToken) throws Parser.ParseException {
        // 1. Consume IDENTIFIER (loop variable)
        Token varToken = context.consume(TokenType.IDENTIFIER);
        String loopVariable = varToken.getValue();

        // 2. Consume 'from'
        context.consume(TokenType.FROM);

        // 3. Parse from expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode fromExpression =
            expressionParser.parse(context);

        // 4. Consume 'to'
        context.consume(TokenType.TO);

        // 5. Parse to expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode toExpression =
            expressionParser.parse(context);

        // 6. Optionally parse step expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode stepExpression = null;
        Token current = context.getCurrentToken();
        if (current != null && current.getType() == TokenType.STEP) {
            context.consume(TokenType.STEP);
            stepExpression = expressionParser.parse(context);
        }

        // 7. Parse body statement
        StatementNode body = getStatementParser().parse(context);

        // 8. Create and return ForRangeStatementNode
        return new ForRangeStatementNode(forToken, loopVariable, fromExpression,
                                          toExpression, stepExpression, body);
    }
}
