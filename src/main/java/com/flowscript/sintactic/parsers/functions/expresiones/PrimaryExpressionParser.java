package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;

import com.flowscript.sintactic.ast.functions.expresiones.*;

import java.util.ArrayList;
import java.util.List;

/**
 * primary :=
 *      literal
 *    | IDENT (('.' IDENT | '(' argList? ')' ) )*
 *    | '(' expression ')'
 */
public class PrimaryExpressionParser implements IParser<ExpressionNode> {

    private final IParser<ExpressionNode> expressionParser; // root
    private final com.flowscript.sintactic.parsers.functions.listas_argumentos.ArgumentListParser argListParser =
            new com.flowscript.sintactic.parsers.functions.listas_argumentos.ArgumentListParser();

    public PrimaryExpressionParser(IParser<ExpressionNode> expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        Token t = context.getCurrentToken();
        if (t == null) throw new Parser.ParseException("Se esperaba una expresión primaria, no hay más tokens.");

        // ( expr )
        if (isLP(t)) {
            context.consume(); // '('
            ExpressionNode inside = expressionParser.parse(context);
            Token rp = context.getCurrentToken();
            if (!isRP(rp)) throw error(rp, "Se esperaba ')'");
            context.consume(); // ')'
            return inside;
        }

        // Literales
        if (isLiteral(t)) {
            context.consume();
            return new LiteralNode(t);
        }

        // Identificador (y sufijos)
        if (t.getType() == TokenType.IDENTIFIER) {
            context.consume();
            ExpressionNode base = new IdentifierNode(t, t.getValue());

            while (true) {
                Token next = context.getCurrentToken();
                if (next == null) break;

                // Acceso por punto
                if (isDot(next)) {
                    context.consume(); // '.'
                    Token prop = context.getCurrentToken();
                    if (prop == null || prop.getType() != TokenType.IDENTIFIER) {
                        throw error(prop, "Se esperaba un identificador después de '.'");
                    }
                    context.consume();
                    base = new PropertyAccessNode(base, prop);
                    continue;
                }

                // Llamada base(...)
                if (isLP(next)) {
                    Token lp = next;
                    context.consume(); // '('

                    List<ArgumentListNode> argNodes = null;
                    Token maybeRP = context.getCurrentToken();
                    if (!isRP(maybeRP)) {
                        argNodes = argListParser.parse(context);
                        maybeRP = context.getCurrentToken();
                    }
                    if (!isRP(maybeRP)) throw error(maybeRP, "Se esperaba ')'");
                    context.consume(); // ')'

                    base = new FunctionCallNode(base, lp, flattenArgs(argNodes));
                    continue;
                }

                break;
            }

            return base;
        }

        throw error(t, "Expresión primaria inválida");
    }

    // ---- helpers ----
    private static boolean isLP(Token t) { return t != null && (t.getType() == TokenType.LEFT_PAREN || "(".equals(t.getValue())); }
    private static boolean isRP(Token t) { return t != null && (t.getType() == TokenType.RIGHT_PAREN || ")".equals(t.getValue())); }
    private static boolean isDot(Token t){ return t != null && (t.getType() == TokenType.DOT || ".".equals(t.getValue())); }

    private static boolean isLiteral(Token t) {
        if (t == null) return false;
        TokenType ty = t.getType();
        return ty == TokenType.INTEGER_LITERAL
                || ty == TokenType.DECIMAL_LITERAL
                || ty == TokenType.STRING_LITERAL
                || ty == TokenType.TRUE
                || ty == TokenType.FALSE
                || ty == TokenType.NULL;
    }

    private static Parser.ParseException error(Token t, String msg) {
        if (t == null) return new Parser.ParseException(msg + ". Fin de entrada.");
        return new Parser.ParseException(msg + " pero se encontró '" + t.getValue() +
                "' en línea " + t.getLine() + ", columna " + t.getColumn());
    }

    private static List<ExpressionNode> flattenArgs(List<ArgumentListNode> nodes) {
        List<ExpressionNode> out = new ArrayList<>();
        if (nodes != null) {
            for (ArgumentListNode n : nodes) {
                out.addAll(n.getArguments()); // ajusta si tu API usa otro nombre
            }
        }
        return out;
    }
}
