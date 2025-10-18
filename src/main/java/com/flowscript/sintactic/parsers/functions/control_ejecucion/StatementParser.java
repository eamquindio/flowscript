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

public class StatementParser implements IParser<StatementNode> {

    // Lazy-initialized parsers
    private IfStatementParser ifParser;
    private TryStatementParser tryParser;
    private ThrowStatementParser throwParser;
    private ReturnStatementParser returnParser;
    private GotoStatementParser gotoParser;
    private ForStatementParser forParser;
    private VariableDeclarationStatementParser varParser;
    private BlockParser blockParser;
    private ExpressionStatementParser exprParser;

    public StatementParser() {
        // Empty constructor - lazy initialization
    }

    private IfStatementParser getIfParser() {
        if (ifParser == null) {
            ifParser = new IfStatementParser();
        }
        return ifParser;
    }

    private TryStatementParser getTryParser() {
        if (tryParser == null) {
            tryParser = new TryStatementParser();
        }
        return tryParser;
    }

    private ThrowStatementParser getThrowParser() {
        if (throwParser == null) {
            throwParser = new ThrowStatementParser();
        }
        return throwParser;
    }

    private ReturnStatementParser getReturnParser() {
        if (returnParser == null) {
            returnParser = new ReturnStatementParser();
        }
        return returnParser;
    }

    private GotoStatementParser getGotoParser() {
        if (gotoParser == null) {
            gotoParser = new GotoStatementParser();
        }
        return gotoParser;
    }

    private ForStatementParser getForParser() {
        if (forParser == null) {
            forParser = new ForStatementParser();
        }
        return forParser;
    }

    private VariableDeclarationStatementParser getVarParser() {
        if (varParser == null) {
            varParser = new VariableDeclarationStatementParser();
        }
        return varParser;
    }

    private BlockParser getBlockParser() {
        if (blockParser == null) {
            blockParser = new BlockParser();
        }
        return blockParser;
    }

    private ExpressionStatementParser getExprParser() {
        if (exprParser == null) {
            exprParser = new ExpressionStatementParser();
        }
        return exprParser;
    }

    @Override
    public StatementNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current.getType() == TokenType.IF) {
            return getIfParser().parse(context);
        }
        
        if (current.getType() == TokenType.TRY) {
            return getTryParser().parse(context);
        }
        
        if (current.getType() == TokenType.THROW) {
            return getThrowParser().parse(context);
        }
        
        if (current.getType() == TokenType.RETURN) {
            return getReturnParser().parse(context);
        }
        
        if (current.getType() == TokenType.GOTO) {
            return getGotoParser().parse(context);
        }
        
        if (current.getType() == TokenType.FOR) {
            return getForParser().parse(context);
        }
        
        if (current.getType() == TokenType.LEFT_BRACE) {
            return getBlockParser().parse(context);
        }
        
        if (current.getType() == TokenType.IDENTIFIER) {
            Token next = context.peek(1);
            if (next != null && next.getType() == TokenType.ASSIGN) {
                return getVarParser().parse(context);
            }
        }
        
        return getExprParser().parse(context);
    }
}
