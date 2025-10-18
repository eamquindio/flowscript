package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import com.flowscript.sintactic.parsers.process.estructura_principal.ProcessDeclarationParser;
/**
 * Parser coordinador para statements en FlowScript.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Statement ::= ExpressionStatement
 *             | IfStatement
 *             | TryStatement
 *             | ThrowStatement
 *             | ReturnStatement
 *             | GotoStatement
 *             | ForStatement
 *             | VariableDeclaration
 *             | Block
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // ExpressionStatement
 * imprimir("Hola")
 * x + y
 *
 * // IfStatement
 * if x > 10 {
 *     imprimir("Grande")
 * } else {
 *     imprimir("Pequeño")
 * }
 *
 * // TryStatement
 * try {
 *     resultado = operacion_riesgosa()
 * } catch (e) {
 *     imprimir("Error: " + e.mensaje)
 * }
 *
 * // ThrowStatement
 * throw { tipo: "Error", mensaje: "Algo salió mal" }
 *
 * // ReturnStatement
 * return x * 2
 *
 * // GotoStatement
 * go_to SiguienteTarea
 *
 * // ForStatement
 * for each item in lista {
 *     imprimir(item)
 * }
 *
 * // VariableDeclaration
 * nombre = "Juan"
 * edad = 25
 *
 * // Block
 * {
 *     x = 10
 *     y = 20
 *     imprimir(x + y)
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * StatementParser parser = new StatementParser();
 * StatementNode statement = parser.parse(context);
 * </pre>
 *
 * @see StatementNode
 */


public class StatementParser implements IParser<StatementNode> {

    // ⚠️ No instanciamos aquí para evitar StackOverflow por dependencias circulares.
    private ProcessDeclarationParser processDeclarationParser;

    public StatementParser() {
        // vacío
    }

    @Override
    public StatementNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current == null) {
            throw new Parser.ParseException("Unexpected end of input while parsing statement");
        }

        // Inicialización diferida (lazy)
        if (processDeclarationParser == null) {
            processDeclarationParser = new ProcessDeclarationParser();
        }

        // Detectar palabra clave 'process'
        if (current.getType() == TokenType.PROCESS || "process".equalsIgnoreCase(current.getValue())) {
            Token processToken = current;

            ProcessDeclarationNode declNode = processDeclarationParser.parse(context);

            return new StatementNode(processToken) {
                @Override
                public String getNodeType() {
                    return "ProcessStatement";
                }

                @Override
                public String toString() {
                    return "ProcessStatement(" + (declNode != null ? declNode.toString() : "null") + ")";
                }
            };
        }

        throw new Parser.ParseException("Unexpected token in StatementParser: '" +
                current.getValue() + "' at line " + current.getLine() + ", column " + current.getColumn());
    }
}