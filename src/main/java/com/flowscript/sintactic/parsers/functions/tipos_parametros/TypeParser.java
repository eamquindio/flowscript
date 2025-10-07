package com.flowscript.sintactic.parsers.functions.tipos_parametros;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;

/**
 * Parser para tipos de datos en FlowScript.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Type ::= 'integer' | 'decimal' | 'boolean' | 'text' | 'list' | 'object' | 'void'
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Tipos primitivos
 * x: entero        // integer - números enteros
 * y: decimal       // decimal - números con decimales
 * activo: booleano // boolean - verdadero/falso
 * nombre: texto    // text - cadenas de caracteres
 *
 * // Tipos de colección
 * numeros: lista   // list - arrays/listas
 * usuario: objeto  // object - objetos/mapas
 *
 * // Tipo especial
 * nada: vacio      // void - sin retorno
 *
 * // En contexto de función
 * function calcular(a: entero, b: decimal) -> decimal {
 *     return a + b
 * }
 *
 * function procesar(datos: objeto) -> lista {
 *     return [datos.nombre, datos.edad]
 * }
 *
 * function notificar(mensaje: texto) -> vacio {
 *     imprimir(mensaje)
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * TypeParser parser = new TypeParser();
 * TypeNode type = parser.parse(context);
 * </pre>
 *
 * @see TypeNode
 */
public class TypeParser implements IParser<TypeNode> {

    @Override
    public TypeNode parse(ParserContext context) throws Parser.ParseException {
        Token typeToken = context.getCurrentToken();

        if (typeToken == null) {
            throw new Parser.ParseException("Expected type but reached end of input");
        }

        // Verificar si es un tipo válido
        if (!isValidType(typeToken)) {
            throw new Parser.ParseException(
                "Expected type (entero, decimal, booleano, texto, lista, objeto, vacio) " +
                "but found '" + typeToken.getValue() + "' at line " + typeToken.getLine()
            );
        }

        context.consume(); // consume el tipo
        return new TypeNode(typeToken);
    }

    /**
     * Verifica si el token representa un tipo válido.
     */
    private boolean isValidType(Token token) {
        TokenType type = token.getType();
        String value = token.getValue();

        // Verificar por TokenType
        if (type == TokenType.INTEGER_TYPE || type == TokenType.DECIMAL_TYPE ||
            type == TokenType.BOOLEAN_TYPE || type == TokenType.TEXT_TYPE ||
            type == TokenType.LIST_TYPE || type == TokenType.OBJECT_TYPE ||
            type == TokenType.VOID) {
            return true;
        }

        // Verificar por valor (español e inglés)
        return value.equals("entero") || value.equals("integer") ||
               value.equals("decimal") ||
               value.equals("booleano") || value.equals("boolean") ||
               value.equals("texto") || value.equals("text") ||
               value.equals("lista") || value.equals("list") ||
               value.equals("objeto") || value.equals("object") ||
               value.equals("vacio") || value.equals("void");
    }
}
