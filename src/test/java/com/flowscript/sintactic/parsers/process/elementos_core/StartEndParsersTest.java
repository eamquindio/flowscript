package com.flowscript.sintactic.parsers.process.elementos_core;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.estructura_principal.EndElementNode;
import com.flowscript.sintactic.ast.process.estructura_principal.StartElementNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StartEndParsersTest {

    private static ParserContext ctx(String src) throws Lexer.LexicalException {
        Lexer lx = new Lexer(src);
        List<Token> tokens = lx.tokenize();
        return new ParserContext(tokens);
    }

    // ---------- START ----------

    @Test
    void parseStart_basic_ok() throws Exception {
        ParserContext context = ctx("start -> PrimeraTarea");
        StartElementParser parser = new StartElementParser();

        StartElementNode node = parser.parse(context);

        assertNotNull(node);
        assertEquals("PrimeraTarea", node.getTargetName());
        // Debe haber avanzado el contexto
        assertNotNull(context.getCurrentToken());
    }

    @Test
    void parseStart_with_spaces_and_newlines_ok() throws Exception {
        ParserContext context = ctx("  start   ->   Nodo_1  \n");
        StartElementParser parser = new StartElementParser();

        StartElementNode node = parser.parse(context);

        assertEquals("Nodo_1", node.getTargetName());
    }

    @Test
    void parseStart_missing_arrow_fails() throws Exception {
        ParserContext context = ctx("start  Nodo");
        StartElementParser parser = new StartElementParser();

        Parser.ParseException ex = assertThrows(Parser.ParseException.class, () -> parser.parse(context));
        assertTrue(ex.getMessage().toLowerCase().contains("se esperaba '->'"));
    }

    @Test
    void parseStart_missing_identifier_fails() throws Exception {
        ParserContext context = ctx("start -> {");
        StartElementParser parser = new StartElementParser();

        Parser.ParseException ex = assertThrows(Parser.ParseException.class, () -> parser.parse(context));
        assertTrue(ex.getMessage().toLowerCase().contains("se esperaba un identificador"));
    }

    // ---------- END ----------

    @Test
    void parseEnd_basic_ok() throws Exception {
        ParserContext context = ctx("end FinProceso");
        EndElementParser parser = new EndElementParser();

        EndElementNode node = parser.parse(context);

        assertNotNull(node);
        assertEquals("FinProceso", node.getName());
    }

    @Test
    void parseEnd_requires_identifier() throws Exception {
        ParserContext context = ctx("end 123");
        EndElementParser parser = new EndElementParser();

        Parser.ParseException ex = assertThrows(Parser.ParseException.class, () -> parser.parse(context));
        assertTrue(ex.getMessage().toLowerCase().contains("se esperaba un identificador"));
    }

    // ---------- Secuencia sobre el mismo contexto ----------

    @Test
    void parse_start_then_end_on_same_context_ok() throws Exception {
        String src = "start -> T1\nend TFin\n";
        ParserContext context = ctx(src);

        StartElementParser startParser = new StartElementParser();
        EndElementParser endParser = new EndElementParser();

        StartElementNode s = startParser.parse(context);
        assertEquals("T1", s.getTargetName());

        EndElementNode e = endParser.parse(context);
        assertEquals("TFin", e.getName());
    }
}
