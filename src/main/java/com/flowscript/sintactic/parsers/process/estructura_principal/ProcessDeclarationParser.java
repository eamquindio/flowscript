package com.flowscript.sintactic.parsers.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para declaraciones de proceso.
 *
 * BNF:
 *   ProcessDeclaration ::= 'process' IDENTIFIER '{' ProcessBody '}'
 */
public class ProcessDeclarationParser implements IParser<ProcessDeclarationNode> {

    private final ProcessBodyParser bodyParser;

    public ProcessDeclarationParser() {
        this.bodyParser = new ProcessBodyParser();
    }

    @Override
    public ProcessDeclarationNode parse(ParserContext context) throws Parser.ParseException {
        // 1) 'process'
        Token processTk = consumeProcessKeyword(context);

        // 2) Nombre (IDENTIFIER)
        Token nameTk = context.consume(TokenType.IDENTIFIER);
        String processName = nameTk.getValue();

        // 3) '{'
        consumeLBrace(context);

        // 4) Intentar parsear el cuerpo con ProcessBodyParser.
        int before = context.getCurrentIndex();
        List<ASTNode> elements = new ArrayList<>();
        boolean bodyAdvanced = false;

        try {
            List<ASTNode> parsed = bodyParser.parse(context);
            int after = context.getCurrentIndex();
            bodyAdvanced = after > before;
            if (parsed != null) {
                elements.addAll(parsed);
            }
        } catch (Parser.ParseException ignore) {
            // Si falla, pasamos al modo permisivo y hacemos skip del cuerpo.
            bodyAdvanced = false;
        }

        // Si el body parser NO avanzó (por ejemplo, primer token es 'start'),
        // aplicamos un fallback permisivo: avanzar hasta quedar justo antes de la '}' de cierre,
        // respetando anidación de llaves.
        if (!bodyAdvanced) {
            skipProcessBodyPermissive(context);
        }

        // 5) Consumir la '}' de cierre
        consumeRBrace(context);

        // 6) Devolver nodo AST
        return new ProcessDeclarationNode(processTk, processName, elements);
    }

    // ---------------------------------------------------------------------
    // Utilidades
    // ---------------------------------------------------------------------

    private static Token consumeProcessKeyword(ParserContext ctx) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba 'process' pero no hay más tokens.");
        }
        if (t.getType() == TokenType.PROCESS || "process".equals(t.getValue())) {
            ctx.advance();
            return t;
        }
        throw new Parser.ParseException(
                "Se esperaba 'process' pero se encontró '" + t.getValue() + "' en línea " +
                        t.getLine() + ", columna " + t.getColumn()
        );
    }

    private static void consumeLBrace(ParserContext ctx) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba '{' pero no hay más tokens.");
        }
        if (isLBrace(t)) {
            ctx.advance();
            return;
        }
        throw new Parser.ParseException(
                "Se esperaba '{' pero se encontró '" + t.getValue() + "' en línea " +
                        t.getLine() + ", columna " + t.getColumn()
        );
    }

    private static void consumeRBrace(ParserContext ctx) throws Parser.ParseException {
        Token t = ctx.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Expected '}' but reached end of input");
        }
        if (isRBrace(t)) {
            ctx.advance();
            return;
        }
        throw new Parser.ParseException(
                "Expected '}' but found '" + t.getValue() + "' at line " +
                        t.getLine() + ", column " + t.getColumn()
        );
    }

    private static boolean isLBrace(Token t) {
        return t != null &&
                (t.getType() == TokenType.LEFT_BRACE || "{".equals(t.getValue()));
    }

    private static boolean isRBrace(Token t) {
        return t != null &&
                (t.getType() == TokenType.RIGHT_BRACE || "}".equals(t.getValue()));
    }

    /**
     * Avanza por el cuerpo del proceso de forma permisiva hasta quedar
     * justo antes de la '}' que cierra el bloque actual. Respeta anidación.
     */
    private static void skipProcessBodyPermissive(ParserContext ctx) throws Parser.ParseException {
        int balance = 1; // ya hemos consumido la '{' de apertura
        while (ctx.hasMoreTokens()) {
            Token t = ctx.getCurrentToken();
            if (isLBrace(t)) {
                balance++;
                ctx.advance();
                continue;
            }
            if (isRBrace(t)) {
                if (balance == 1) {
                    // Estamos justo antes de la '}' que cierra el bloque del proceso.
                    return;
                } else {
                    balance--;
                    ctx.advance();
                    continue;
                }
            }
            // Cualquier otro token dentro del cuerpo
            ctx.advance();
        }
        throw new Parser.ParseException("Se alcanzó el fin de entrada buscando la '}' de cierre del proceso.");
    }
}
