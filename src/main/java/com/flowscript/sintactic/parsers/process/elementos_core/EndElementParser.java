package com.flowscript.sintactic.parsers.process.elementos_core;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.EndElementNode;

public class EndElementParser implements IParser<EndElementNode> {

    @Override
    public EndElementNode parse(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();
        TokenType type = current.getType();


        if (type != TokenType.END && type != TokenType.FIN) {
            throw error("Se esperaba 'end' o 'fin'", current);
        }


        context.consume();


        Token nameToken = context.consume(TokenType.IDENTIFIER);


        return new EndElementNode(current, nameToken.getValue());
    }

    private Parser.ParseException error(String message, Token token) {
        return new Parser.ParseException(
                message + " en la línea " + token.getLine() + ", columna " + token.getColumn()
        );
    }
}
