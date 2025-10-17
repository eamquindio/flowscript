package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.parsers.functions.tipos_parametros.ParameterListParser;
import com.flowscript.sintactic.parsers.functions.tipos_parametros.TypeParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.BlockParser;

import java.util.List;

/**
 * Parser para declaraciones de función en FlowScript.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * FunctionDeclaration ::= 'function' IDENTIFIER '(' ParameterList? ')' ( '->' Type )? Block
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Función sin parámetros ni retorno
 * function saludar() {
 *     imprimir("Hola mundo")
 * }
 *
 * // Función con parámetros y retorno
 * function sumar(a: entero, b: entero) -> entero {
 *     return a + b
 * }
 *
 * // Función con tipos complejos
 * function procesar_usuario(datos: objeto, opciones: lista) -> booleano {
 *     if datos.nombre == "" {
 *         return falso
 *     }
 *     return verdadero
 * }
 *
 * // Función recursiva
 * function factorial(n: entero) -> entero {
 *     if n <= 1 {
 *         return 1
 *     }
 *     return n * factorial(n - 1)
 * }
 *
 * // Función con lógica compleja
 * function validar_email(email: texto) -> booleano {
 *     if no email.contains("@") {
 *         return falso
 *     }
 *     partes = email.split("@")
 *     return partes.length == 2
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * FunctionDeclarationParser parser = new FunctionDeclarationParser();
 * FunctionDeclarationNode node = parser.parse(context);
 * </pre>
 *
 * @see FunctionDeclarationNode
 */
public class FunctionDeclarationParser implements IParser<FunctionDeclarationNode> {

    private final ParameterListParser parameterListParser;
    private final TypeParser typeParser;
    private final BlockParser blockParser;

    public FunctionDeclarationParser() {
        this.parameterListParser = new ParameterListParser();
        this.typeParser = new TypeParser();
        this.blockParser = new BlockParser();
    }

    @Override
    public FunctionDeclarationNode parse(ParserContext context) throws Parser.ParseException {
        Token functionToken = context.consume(TokenType.FUNCTION);

        Token nameToken = context.consume(TokenType.IDENTIFIER);
        String functionName = nameToken.getValue();

        context.consume(TokenType.LEFT_PAREN);

        List<ParameterNode> parameters = null;
        if (!context.check(TokenType.RIGHT_PAREN)) {
            parameters = parameterListParser.parse(context);
        }

        context.consume(TokenType.RIGHT_PAREN);

        TypeNode returnType = null;
        if (context.checkValue("->")) {
            context.consumeValue("->");
            returnType = typeParser.parse(context);
        }

        BlockNode body = blockParser.parse(context);

        return new FunctionDeclarationNode(functionToken, functionName, parameters, returnType, body);
    }
}
