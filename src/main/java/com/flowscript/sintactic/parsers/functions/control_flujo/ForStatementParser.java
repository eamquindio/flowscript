package com.flowscript.sintactic.parsers.functions.control_flujo;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.StatementParser;

public class ForStatementParser implements IParser<ForStatementNode> {
    private ExpressionParser expressionParser;
    private StatementParser statementParser;

    public ForStatementParser() {
    }

    private ExpressionParser getExpressionParser() {
        if (expressionParser == null) {
            expressionParser = new ExpressionParser();
        }
        return expressionParser; 
    }

    private StatementParser getStatementParser() {
        if (statementParser == null) {
            statementParser = new StatementParser();
        }
         return statementParser;
    }

    @Override
    public ForStatementNode parse(ParserContext context) throws Parser.ParseException {
         throw new UnsupportedOperationException("ForStatementParser no implementado - Tarea del estudiante");
    } 
}

