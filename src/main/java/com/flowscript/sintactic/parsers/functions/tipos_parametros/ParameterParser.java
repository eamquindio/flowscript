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
        // Permitir keywords como identificadores en este contexto
        Token nameToken = context.getCurrentToken();
        if (nameToken == null) {
            throw new Parser.ParseException("Expected parameter name but found end of file");
        }

        // Aceptar IDENTIFIER o keywords como nombres de parámetros
        if (nameToken.getType() != TokenType.IDENTIFIER && !isAcceptableAsIdentifier(nameToken)) {
            throw new Parser.ParseException(
                "Expected IDENTIFIER but found " + nameToken.getType() +
                " at line " + nameToken.getLine() + ", column " + nameToken.getColumn()
            );
        }

        context.consume(); // consume el token (IDENTIFIER o keyword)
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

    /**
     * Verifica si un token keyword puede ser usado como identificador.
     * Esto permite usar keywords como "y", "o", "no" como nombres de variables/parámetros.
     */
    private boolean isAcceptableAsIdentifier(Token token) {
        TokenType type = token.getType();
        // Permitir operadores lógicos como identificadores
        return type == TokenType.AND || type == TokenType.OR || type == TokenType.NOT;
    }
}
