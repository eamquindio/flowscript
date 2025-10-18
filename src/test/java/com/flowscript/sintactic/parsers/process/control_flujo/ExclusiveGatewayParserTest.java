package com.flowscript.sintactic.parsers.process.control_flujo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.ExclusiveGatewayNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para ExclusiveGatewayParser.
 *
 * Gramática BNF:
 * ExclusiveGateway ::= 'gateway' IDENTIFIER '{' WhenClause* ElseClause? '}'
 * WhenClause ::= 'when' Expression '->' IDENTIFIER
 * ElseClause ::= 'else' '->' IDENTIFIER
 */
public class ExclusiveGatewayParserTest {

    @Test
    public void testSimpleGateway() throws Exception {
        String input = "gateway CheckAmount {\n" +
                      "    when amount > 10000 -> HighValue\n" +
                      "    when amount > 1000 -> MediumValue\n" +
                      "    else -> LowValue\n" +
                      "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // Note: You'll need to create the actual parser
        // ExclusiveGatewayParser parser = new ExclusiveGatewayParser();
        // ExclusiveGatewayNode result = parser.parse(context);

        // assertNotNull(result);
        // assertEquals("CheckAmount", result.getName());
        // assertEquals(2, result.getWhenClauses().size());
    }

    @Test
    public void testGatewayWithoutElse() throws Exception {
        String input = "gateway Validate {\n" +
                      "    when isValid -> Continue\n" +
                      "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // ExclusiveGatewayParser parser = new ExclusiveGatewayParser();
        // ExclusiveGatewayNode result = parser.parse(context);

        // assertNotNull(result);
    }
}
