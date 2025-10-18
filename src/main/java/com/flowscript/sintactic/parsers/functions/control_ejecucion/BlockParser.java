package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;

import java.util.List;

/**
 * Parser para bloques de código delimitados por llaves.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * Block ::= '{' StatementList '}'
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Bloque simple
 * {
 *     x = 10
 *     imprimir(x)
 * }
 *
 * // Bloque en función
 * function saludar(nombre: texto) {
 *     mensaje = "Hola " + nombre
 *     imprimir(mensaje)
 *     return mensaje
 * }
 *
 * // Bloques anidados
 * {
 *     x = 5
 *     {
 *         y = x * 2
 *         imprimir(y)
 *     }
 *     imprimir(x)
 * }
 *
 * // Bloque en if
 * if x > 10 {
 *     imprimir("Mayor")
 *     y = x * 2
 * }
 *
 * // Bloque en try
 * try {
 *     resultado = dividir(a, b)
 *     imprimir(resultado)
 * } catch (e) {
 *     imprimir("Error: " + e.mensaje)
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * BlockParser parser = new BlockParser();
 * BlockNode block = parser.parse(context);
 * </pre>
 *
 * @see BlockNode
 * @see StatementNode
 */
public class BlockParser implements IParser<BlockNode> {

    @Override
    public BlockNode parse(ParserContext context) throws Parser.ParseException {
        StatementListParser statementListParser = new StatementListParser();
        Token open = context.getCurrentToken();
        if (open == null || open.getType() != TokenType.LEFT_BRACE) {
            throw new Parser.ParseException("se esperaba '{' al inicio de un bloque");
        }

        context.consume(); 
        BlockNode block = new BlockNode(open);

        if (context.check(TokenType.RIGHT_BRACE)) {
            context.consume();
            return block;
        }

        List<StatementNode> statements = statementListParser.parse(context);
        for (StatementNode s : statements) {
            block.addStatement(s);
        }

        if (statements.isEmpty()) {
            throw new Parser.ParseException("bloque vacio o las sentencias no consumieron ningbn token.");
        }

        Token close = context.getCurrentToken();
        if (close == null || close.getType() != TokenType.RIGHT_BRACE) {
            throw new Parser.ParseException("se esperaba '}' para cerrar el bloque");
        }
        context.consume();

        return block;
    }
}
