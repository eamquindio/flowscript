package com.flowscript.sintactic.parsers.process.elementos_trabajo;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.TaskElementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para TaskElementParser.
 *
 * Gramática BNF:
 * TaskElement ::= 'task' IDENTIFIER '{' TaskBody '}'
 * TaskBody ::= 'action:' StatementList
 */
public class TaskElementParserTest {

    @Test
    public void testSimpleTask() throws Exception {
        String input = "task ValidateInput { action: x = 1\n go_to Next }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // Note: You'll need to create the actual parser
        // TaskElementParser parser = new TaskElementParser();
        // TaskElementNode result = parser.parse(context);

        // assertNotNull(result);
        // assertEquals("ValidateInput", result.getName());
    }

    @Test
    public void testTaskWithMultipleStatements() throws Exception {
        String input = "task ProcessData { action:\n" +
                      "    data = load_data()\n" +
                      "    result = process(data)\n" +
                      "    save(result)\n" +
                      "    go_to Complete\n" +
                      "}";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);

        // TaskElementParser parser = new TaskElementParser();
        // TaskElementNode result = parser.parse(context);

        // assertNotNull(result);
    }
}
