package com.flowscript.sintactic.parsers.functions.control_flujo;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_flujo.TryStatementNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.BlockParser;

public class TryStatementParser implements IParser<TryStatementNode> {
    private BlockParser blockParser;
 
    public TryStatementParser() {
    }

    private BlockParser getBlockParser() {
        if (blockParser == null) {
            blockParser = new BlockParser();

        }
        return blockParser;
    }     

    @Override 
    public TryStatementNode parse(ParserContext context) throws Parser.ParseException {
        throw new UnsupportedOperationException("TryStatementParser no implementado - Tarea del estudiante");
    } 
}



