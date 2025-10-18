package com.flowscript.sintactic.parsers.functions.control_ejecucion;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import java.util.ArrayList;
import java.util.List;

public class StatementListParser {
    private StatementParser statementParser;

    public StatementListParser() {
    }

    private  StatementParser getStatementParser() {
        if (statementParser == null) {
            statementParser = new StatementParser();
        }

        return statementParser;
    }

    public List<StatementNode> parse(ParserContext context) throws Parser.ParseException {
        return null;
    } 
}

