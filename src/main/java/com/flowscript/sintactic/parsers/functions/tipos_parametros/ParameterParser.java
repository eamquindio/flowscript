package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;

/**
 * Parser para parámetros individuales de función.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Parameter ::= IDENTIFIER ':' Type
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Parámetros simples
 * x: entero
 * nombre: texto
 * activo: booleano
 *
 * // En contexto de función
 * function procesar(dato: entero, mensaje: texto) {
 *     imprimir(mensaje + ": " + dato)
 * }
 *
 * function crear_usuario(
 *     nombre: texto,
 *     edad: entero,
 *     correo: texto,
 *     activo: booleano
 * ) -> objeto {
 *     return {
 *         nombre: nombre,
 *         edad: edad,
 *         correo: correo,
 *         activo: activo
 *     }
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ParameterParser parser = new ParameterParser();
 * ParameterNode param = parser.parse(context);
 * </pre>
 *
 * @see ParameterNode
 * @see TypeNode
 */
public class ParameterParser implements IParser<ParameterNode> {

    private final TypeParser typeParser;

    public ParameterParser() {
        this.typeParser = new TypeParser();
    }

    @Override
    public ParameterNode parse(ParserContext context) throws Parser.ParseException {
        // Consume IDENTIFIER (nombre del parámetro)
        Token nameToken = context.consume(TokenType.IDENTIFIER);
        String paramName = nameToken.getValue();

        // Consume ':'
        Token colon = context.getCurrentToken();
        if (colon == null || !colon.getValue().equals(":")) {
            throw new Parser.ParseException(
                "Expected ':' after parameter name '" + paramName +
                "' at line " + nameToken.getLine()
            );
        }
        context.consume(); // consume ':'

        // Parse tipo
        TypeNode paramType = typeParser.parse(context);

        return new ParameterNode(paramName, paramType);
    }
}

