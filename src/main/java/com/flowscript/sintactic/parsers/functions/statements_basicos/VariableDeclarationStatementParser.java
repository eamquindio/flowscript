package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.statements_basicos.VariableDeclarationStatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para declaración y asignación de variables dentro de funciones/procesos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * VariableDeclaration ::= IDENTIFIER '=' Expression
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Variables locales en función
 * function calcular_area(radio: decimal) -> decimal {
 *     pi = 3.14159
 *     area = pi * radio * radio
 *     return area
 * }
 *
 * // Variables en tareas
 * task ProcesarDatos {
 *     action:
 *         datos = entrada.valores
 *         resultado = procesar(datos)
 *         imprimir(resultado)
 *         go_to Fin
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * VariableDeclarationStatementParser parser = new VariableDeclarationStatementParser();
 * VariableDeclarationStatementNode node = parser.parse(context);
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo estos pasos:
 * <ol>
 *   <li>Consumir IDENTIFIER (nombre de la variable)</li>
 *   <li>Consumir '='</li>
 *   <li>Parsear la expresión de valor usando ExpressionParser</li>
 *   <li>Crear y retornar VariableDeclarationStatementNode con nombre y expresión</li>
 * </ol>
 *
 * @see VariableDeclarationStatementNode
 */
public class VariableDeclarationStatementParser implements IParser<VariableDeclarationStatementNode> {

    private final ExpressionParser expressionParser;

    public VariableDeclarationStatementParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public VariableDeclarationStatementNode parse(ParserContext context) throws Parser.ParseException {
        Token identifierToken = context.consume(TokenType.IDENTIFIER);
        String variableName = identifierToken.getValue();

        context.consumeValue("=");

        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode initializer = expressionParser.parse(context);

        return new VariableDeclarationStatementNode(identifierToken, variableName, initializer);
    }
}