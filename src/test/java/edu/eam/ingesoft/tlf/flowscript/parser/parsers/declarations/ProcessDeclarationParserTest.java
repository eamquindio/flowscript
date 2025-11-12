package edu.eam.ingesoft.tlf.flowscript.parser.parsers.declarations;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserContext;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserException;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.ProcessDeclarationNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ProcessDeclarationParser.
 * Tests the parser in isolation using manually constructed token lists.
 */
class ProcessDeclarationParserTest {

    @Test
    void testBasicProcessWithStartAndEnd() {
        // proceso SimpleProcess { inicio -> FirstTask fin Completed }
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.IDENTIFIER, "SimpleProcess"),
            token(TokenType.LEFT_BRACE, "{"),
            token(TokenType.INICIO, "inicio"),
            token(TokenType.ARROW, "->"),
            token(TokenType.IDENTIFIER, "FirstTask"),
            token(TokenType.FIN, "fin"),
            token(TokenType.IDENTIFIER, "Completed"),
            token(TokenType.RIGHT_BRACE, "}")
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        ProcessDeclarationNode process = parser.parse(context);

        assertNotNull(process);
        assertEquals("SimpleProcess", process.getName());
        assertEquals(2, process.getElements().size());

        // Verify start element
        assertTrue(process.getElements().get(0) instanceof StartElementNode);
        StartElementNode start = (StartElementNode) process.getElements().get(0);
        assertEquals("FirstTask", start.getTargetTaskName());

        // Verify end element
        assertTrue(process.getElements().get(1) instanceof EndElementNode);
        EndElementNode end = (EndElementNode) process.getElements().get(1);
        assertEquals("Completed", end.getName());
    }

    @Test
    void testProcessWithTask() {
        // proceso TestProcess {
        //   inicio -> Task1
        //   tarea Task1 { accion: ejecutar() }
        //   fin End
        // }
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.IDENTIFIER, "TestProcess"),
            token(TokenType.LEFT_BRACE, "{"),
            // Start element
            token(TokenType.INICIO, "inicio"),
            token(TokenType.ARROW, "->"),
            token(TokenType.IDENTIFIER, "Task1"),
            // Task element
            token(TokenType.TAREA, "tarea"),
            token(TokenType.IDENTIFIER, "Task1"),
            token(TokenType.LEFT_BRACE, "{"),
            token(TokenType.ACCION, "accion"),
            token(TokenType.COLON, ":"),
            token(TokenType.IDENTIFIER, "ejecutar"),
            token(TokenType.LEFT_PAREN, "("),
            token(TokenType.RIGHT_PAREN, ")"),
            token(TokenType.RIGHT_BRACE, "}"),
            // End element
            token(TokenType.FIN, "fin"),
            token(TokenType.IDENTIFIER, "End"),
            token(TokenType.RIGHT_BRACE, "}")
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        ProcessDeclarationNode process = parser.parse(context);

        assertNotNull(process);
        assertEquals("TestProcess", process.getName());
        assertEquals(3, process.getElements().size());

        // Verify task element
        assertTrue(process.getElements().get(1) instanceof TaskElementNode);
        TaskElementNode task = (TaskElementNode) process.getElements().get(1);
        assertEquals("Task1", task.getName());
        assertNotNull(task.getActions());
        assertEquals(1, task.getActions().size());
    }

    @Test
    void testProcessWithMultipleTasks() {
        // proceso MultiTask {
        //   inicio -> Task1
        //   tarea Task1 { accion: step1() }
        //   tarea Task2 { accion: step2() }
        //   fin End
        // }
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.IDENTIFIER, "MultiTask"),
            token(TokenType.LEFT_BRACE, "{"),
            // Start
            token(TokenType.INICIO, "inicio"),
            token(TokenType.ARROW, "->"),
            token(TokenType.IDENTIFIER, "Task1"),
            // Task 1
            token(TokenType.TAREA, "tarea"),
            token(TokenType.IDENTIFIER, "Task1"),
            token(TokenType.LEFT_BRACE, "{"),
            token(TokenType.ACCION, "accion"),
            token(TokenType.COLON, ":"),
            token(TokenType.IDENTIFIER, "step1"),
            token(TokenType.LEFT_PAREN, "("),
            token(TokenType.RIGHT_PAREN, ")"),
            token(TokenType.RIGHT_BRACE, "}"),
            // Task 2
            token(TokenType.TAREA, "tarea"),
            token(TokenType.IDENTIFIER, "Task2"),
            token(TokenType.LEFT_BRACE, "{"),
            token(TokenType.ACCION, "accion"),
            token(TokenType.COLON, ":"),
            token(TokenType.IDENTIFIER, "step2"),
            token(TokenType.LEFT_PAREN, "("),
            token(TokenType.RIGHT_PAREN, ")"),
            token(TokenType.RIGHT_BRACE, "}"),
            // End
            token(TokenType.FIN, "fin"),
            token(TokenType.IDENTIFIER, "End"),
            token(TokenType.RIGHT_BRACE, "}")
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        ProcessDeclarationNode process = parser.parse(context);

        assertEquals("MultiTask", process.getName());
        assertEquals(4, process.getElements().size());
        assertTrue(process.getElements().get(1) instanceof TaskElementNode);
        assertTrue(process.getElements().get(2) instanceof TaskElementNode);
    }

    @Test
    void testProcessWithParallelGateway() {
        // proceso ParallelProc {
        //   inicio -> Start
        //   gateway Parallel paralelo { rama -> Task1 rama -> Task2 unir -> End }
        //   fin End
        // }
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.IDENTIFIER, "ParallelProc"),
            token(TokenType.LEFT_BRACE, "{"),
            // Start
            token(TokenType.INICIO, "inicio"),
            token(TokenType.ARROW, "->"),
            token(TokenType.IDENTIFIER, "Start"),
            // Parallel Gateway
            token(TokenType.GATEWAY, "gateway"),
            token(TokenType.IDENTIFIER, "Parallel"),
            token(TokenType.PARALELO, "paralelo"),
            token(TokenType.LEFT_BRACE, "{"),
            token(TokenType.RAMA, "rama"),
            token(TokenType.ARROW, "->"),
            token(TokenType.IDENTIFIER, "Task1"),
            token(TokenType.RAMA, "rama"),
            token(TokenType.ARROW, "->"),
            token(TokenType.IDENTIFIER, "Task2"),
            token(TokenType.UNIR, "unir"),
            token(TokenType.ARROW, "->"),
            token(TokenType.IDENTIFIER, "End"),
            token(TokenType.RIGHT_BRACE, "}"),
            // End
            token(TokenType.FIN, "fin"),
            token(TokenType.IDENTIFIER, "End"),
            token(TokenType.RIGHT_BRACE, "}")
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        ProcessDeclarationNode process = parser.parse(context);

        assertEquals("ParallelProc", process.getName());
        assertEquals(3, process.getElements().size());

        // Verify parallel gateway
        assertTrue(process.getElements().get(1) instanceof ParallelGatewayNode);
        ParallelGatewayNode gateway = (ParallelGatewayNode) process.getElements().get(1);
        assertEquals("Parallel", gateway.getName());
        assertEquals(2, gateway.getBranches().size());
        assertEquals("End", gateway.getMergeTargetName());
    }

    @Test
    void testEmptyProcessThrowsException() {
        // proceso EmptyProcess { }
        // Empty processes are invalid - must have at least one element
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.IDENTIFIER, "EmptyProcess"),
            token(TokenType.LEFT_BRACE, "{"),
            token(TokenType.RIGHT_BRACE, "}")
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        // Should throw because processes must have at least one element
        assertThrows(ParserException.class, () -> parser.parse(context));
    }

    @Test
    void testProcessMissingName() {
        // proceso { }
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.LEFT_BRACE, "{"),  // Missing IDENTIFIER
            token(TokenType.RIGHT_BRACE, "}")
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        assertThrows(ParserException.class, () -> parser.parse(context));
    }

    @Test
    void testProcessMissingOpenBrace() {
        // proceso TestProc }
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.IDENTIFIER, "TestProc"),
            token(TokenType.RIGHT_BRACE, "}")  // Missing LEFT_BRACE
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        assertThrows(ParserException.class, () -> parser.parse(context));
    }

    @Test
    void testProcessMissingCloseBrace() {
        // proceso TestProc { inicio -> Task
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.IDENTIFIER, "TestProc"),
            token(TokenType.LEFT_BRACE, "{"),
            token(TokenType.INICIO, "inicio"),
            token(TokenType.ARROW, "->"),
            token(TokenType.IDENTIFIER, "Task")
            // Missing RIGHT_BRACE
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        assertThrows(ParserException.class, () -> parser.parse(context));
    }

    @Test
    void testProcessWithInvalidElement() {
        // proceso TestProc { invalido }
        List<Token> tokens = List.of(
            token(TokenType.PROCESO, "proceso"),
            token(TokenType.IDENTIFIER, "TestProc"),
            token(TokenType.LEFT_BRACE, "{"),
            token(TokenType.FUNCION, "funcion"),  // Invalid element type
            token(TokenType.RIGHT_BRACE, "}")
        );

        ParserContext context = new ParserContext(tokens);
        ProcessDeclarationParser parser = new ProcessDeclarationParser();

        assertThrows(ParserException.class, () -> parser.parse(context));
    }

    /**
     * Helper method to create a token with position info.
     */
    private Token token(TokenType type, String lexeme) {
        return new Token(type, lexeme, 1, 1);
    }
}
