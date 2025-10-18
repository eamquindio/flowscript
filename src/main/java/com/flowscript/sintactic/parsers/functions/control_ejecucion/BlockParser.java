package com.flowscript.sintactic.parsers.functions.control_ejecucion;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import java.util.List;

public class BlockParser implements IParser<BlockNode> {
    private StatementListParser statementListParser;

    public BlockParser() {
    }

    private StatementListParser getStatementListParser() {
        if (statementListParser == null) { 
            statementListParser = new StatementListParser();
        }
        return statementListParser;
    }
    @Override
    public BlockNode parse(ParserContext context)  throws Parser.ParseException {
        return null;
    } 

}

