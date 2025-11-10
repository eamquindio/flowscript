package com.flowscript.sintactic.parsers.functions.statements_basicos;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.VariableDeclarationNode;
import com.flowscript.sintactic.ast.functions.statements_basicos.VariableDeclarationStatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para declaración y asignación de variables.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * VariableDeclaration ::= IDENTIFIER '=' Expression
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Variables simples
 * x = 10
 * nombre = "Juan"
 * activo = verdadero
 *
 * // Variables con expresiones
 * suma = a + b
 * mensaje = "Hola " + nombre
 * edad_futura = edad + 5
 *
 * // Variables con llamadas a función
 * resultado = calcular(10, 20)
 * usuario = db.get("usuarios", 123)
 * datos = http.get("api/datos")
 *
 * // Variables con objetos y listas
 * punto = { x: 10, y: 20 }
 * numeros = [1, 2, 3, 4, 5]
 * matriz = [[1, 2], [3, 4]]
 *
 * // Variables globales (top-level)
 * API_KEY = "secret-123"
 * MAX_RETRIES = 3
 * BASE_URL = "https://api.ejemplo.com"
 *
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
 *
 * // Reasignación (sobrescribe variable existente)
 * x = 10
 * x = x + 5  // x ahora vale 15
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * VariableDeclarationParser parser = new VariableDeclarationParser();
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
 * @see VariableDeclarationNode
 */
public class VariableDeclarationParser implements IParser<VariableDeclarationNode> {

    private final ExpressionParser expressionParser;

    public VariableDeclarationParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public VariableDeclarationNode parse(ParserContext context) throws Parser.ParseException {
        // 1. Consume IDENTIFIER (permitir keywords como "y", "o", "no" como nombres de variables)
        Token identifierToken = context.getCurrentToken();
        if (identifierToken == null) {
            throw new Parser.ParseException("Expected variable name but found end of file");
        }

        // Aceptar IDENTIFIER o keywords como nombres de variables
        if (identifierToken.getType() != TokenType.IDENTIFIER && !isAcceptableAsIdentifier(identifierToken)) {
            throw new Parser.ParseException(
                "Expected IDENTIFIER but found " + identifierToken.getType() +
                " at line " + identifierToken.getLine() + ", column " + identifierToken.getColumn()
            );
        }

        context.consume(); // consume el token
        String variableName = identifierToken.getValue();

        // 2. Consume '='
        context.consume(TokenType.ASSIGN);

        // 3. Parse expression
        com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode initializer = expressionParser.parse(context);

        // 4. Create and return VariableDeclarationNode (top-level)
        return new VariableDeclarationNode(identifierToken, variableName, initializer);
    }

    /**
     * Verifica si un token keyword puede ser usado como identificador.
     * Esto permite usar keywords como "y", "o", "no" como nombres de variables.
     */
    private boolean isAcceptableAsIdentifier(Token token) {
        TokenType type = token.getType();
        // Permitir operadores lógicos como identificadores
        return type == TokenType.AND || type == TokenType.OR || type == TokenType.NOT;
    }
}
