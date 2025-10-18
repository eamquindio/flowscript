package com.flowscript.sintactic.parsers.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.parsers.functions.control_flujo.*;
import com.flowscript.sintactic.parsers.functions.statements_basicos.*;
import com.flowscript.sintactic.parsers.process.navegacion.GotoStatementParser;

/**
 * Parser principal que se encarga de decidir qué tipo de sentencia (statement)
 * se está leyendo y a qué parser delegarla.
 *
 * Ejemplo general:
 *   Statement ::= ExpressionStatement | If | Try | Throw | Return | Goto | For | VariableDeclaration | Block
 *
 * En pocas palabras: este parser es el "router" de todos los statements.
 */
public class StatementParser implements IParser<StatementNode> {

    private IfStatementParser ifParser;
    private TryStatementParser tryParser;
    private ThrowStatementParser throwParser;
    private ReturnStatementParser returnParser;
    private GotoStatementParser gotoParser;
    private ForStatementParser forParser;
    private VariableDeclarationStatementParser varParser;
    private BlockParser blockParser;
    private ExpressionStatementParser exprParser;

    // Constructor vacío (usa wire() antes de parsear)
    public StatementParser() { }

    // Constructor completo, útil si quieres inyectar todo desde el inicio
    public StatementParser(
            IfStatementParser ifParser,
            TryStatementParser tryParser,
            ThrowStatementParser throwParser,
            ReturnStatementParser returnParser,
            GotoStatementParser gotoParser,
            ForStatementParser forParser,
            VariableDeclarationStatementParser varParser,
            BlockParser blockParser,
            ExpressionStatementParser exprParser
    ) {
        this.ifParser = ifParser;
        this.tryParser = tryParser;
        this.throwParser = throwParser;
        this.returnParser = returnParser;
        this.gotoParser = gotoParser;
        this.forParser = forParser;
        this.varParser = varParser;
        this.blockParser = blockParser;
        this.exprParser = exprParser;
    }

    /**
     * Inyecta los parsers necesarios para evitar referencias circulares.
     */
    public void wire(
            IfStatementParser ifParser,
            TryStatementParser tryParser,
            ThrowStatementParser throwParser,
            ReturnStatementParser returnParser,
            GotoStatementParser gotoParser,
            ForStatementParser forParser,
            VariableDeclarationStatementParser varParser,
            BlockParser blockParser,
            ExpressionStatementParser exprParser
    ) {
        this.ifParser = ifParser;
        this.tryParser = tryParser;
        this.throwParser = throwParser;
        this.returnParser = returnParser;
        this.gotoParser = gotoParser;
        this.forParser = forParser;
        this.varParser = varParser;
        this.blockParser = blockParser;
        this.exprParser = exprParser;
    }

    @Override
    public StatementNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();
        if (current == null) {
            throw new Parser.ParseException("Se esperaba un statement, pero no hay más tokens.");
        }

        // Si encontramos '}', no lo tocamos aquí (lo maneja el BlockParser)
        if (isRBrace(current)) return null;

        // Si empieza con '{', lo tomamos como un bloque
        if (isLBrace(current)) {
            require(blockParser, "BlockParser");
            return blockParser.parse(context);
        }

        // Despachamos según palabra clave o tipo de token
        if (isIf(current))    { require(ifParser, "IfStatementParser"); return ifParser.parse(context); }
        if (isTry(current))   { require(tryParser, "TryStatementParser"); return tryParser.parse(context); }
        if (isThrow(current)) { require(throwParser, "ThrowStatementParser"); return throwParser.parse(context); }
        if (isReturn(current)){ require(returnParser, "ReturnStatementParser"); return returnParser.parse(context); }
        if (isGoto(current))  { require(gotoParser, "GotoStatementParser"); return gotoParser.parse(context); }
        if (isFor(current))   { require(forParser, "ForStatementParser"); return forParser.parse(context); }

        // Detectar asignaciones/declaraciones rápidas (ID = algo)
        if (isAssignmentLookahead(context)) {
            require(varParser, "VariableDeclarationStatementParser");
            return varParser.parse(context);
        }

        // Si nada de lo anterior, tratamos la línea como una expresión simple
        require(exprParser, "ExpressionStatementParser");
        return exprParser.parse(context);
    }

    // -------------------------------------
    // Utilidades privadas de detección
    // -------------------------------------

    private static boolean isIf(Token t)    { return isType(t, TokenType.IF) || eqLex(t, "if"); }
    private static boolean isTry(Token t)   { return isType(t, TokenType.TRY) || eqLex(t, "try"); }
    private static boolean isThrow(Token t) { return isType(t, TokenType.THROW) || eqLex(t, "throw"); }
    private static boolean isReturn(Token t){ return isType(t, TokenType.RETURN) || eqLex(t, "return"); }
    private static boolean isGoto(Token t)  { return isType(t, TokenType.GOTO) || eqLex(t, "goto") || eqLex(t, "go_to"); }
    private static boolean isFor(Token t)   { return isType(t, TokenType.FOR) || eqLex(t, "for"); }
    private static boolean isLBrace(Token t){ return isType(t, TokenType.LEFT_BRACE) || eqLex(t, "{"); }
    private static boolean isRBrace(Token t){ return isType(t, TokenType.RIGHT_BRACE) || eqLex(t, "}"); }

    private static boolean isType(Token t, TokenType type) { return t != null && t.getType() == type; }
    private static boolean eqLex(Token t, String s) { return t != null && s.equals(t.getValue()); }

    // Detecta si tenemos algo como "ID = ..."
    private static boolean isAssignmentLookahead(ParserContext ctx) {
        Token t0 = ctx.getCurrentToken();
        if (t0 == null || t0.getType() != TokenType.IDENTIFIER) return false;

        Token t1 = ctx.peek(1);
        return t1 != null && (t1.getType() == TokenType.ASSIGN || "=".equals(t1.getValue()));
    }

    private static void require(Object dep, String name) throws Parser.ParseException {
        if (dep == null) {
            throw new Parser.ParseException("Falta inyectar: " + name +
                    ". Usa StatementParser.wire(...) o el constructor completo.");
        }
    }
}
