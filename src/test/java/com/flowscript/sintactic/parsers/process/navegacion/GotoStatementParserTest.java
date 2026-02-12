package com.flowscript.sintactic.parsers.process.navegacion;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.navegacion.GotoStatementNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para GotoStatementParser.
 *
 * Gramática (BNF):
 *   GotoStatement ::= 'go_to' IDENTIFIER
 * Aceptamos también la variante 'goto' porque el lexer puede emitir ambas.
 */
public class GotoStatementParserTest {

    private GotoStatementParser parser;

    @BeforeEach
    void setUp() {
        parser = new GotoStatementParser();
    }

    @Test
    @DisplayName("goto básico con destino simple")
    public void testSimpleGoto() throws Exception {
        GotoStatementNode result = parseOne("go_to NextTask");
        assertNotNull(result);
        assertEquals("NextTask", result.getTargetLabel());
    }

    @Test
    @DisplayName("goto con guion bajo en el identificador")
    public void testGotoWithUnderscoreInName() throws Exception {
        GotoStatementNode result = parseOne("go_to Validate_Input");
        assertNotNull(result);
        assertEquals("Validate_Input", result.getTargetLabel());
    }

    @Test
    @DisplayName("goto hacia un end")
    public void testGotoToEnd() throws Exception {
        GotoStatementNode result = parseOne("go_to FinOK");
        assertNotNull(result);
        assertEquals("FinOK", result.getTargetLabel());
    }

    @Test
    @DisplayName("error: falta el destino")
    public void testInvalidGoto_MissingTarget() throws Exception {
        assertThrows(Parser.ParseException.class, () -> parseOne("go_to"));
    }

    // --- Casos adicionales recomendados ---

    @Test
    @DisplayName("acepta variante 'goto' sin guion bajo")
    public void testGotoKeywordVariantWithoutUnderscore() throws Exception {
        GotoStatementNode result = parseOne("goto Siguiente");
        assertNotNull(result);
        assertEquals("Siguiente", result.getTargetLabel());
    }

    @Test
    @DisplayName("error: destino no es identificador")
    public void testInvalidGoto_NonIdentifierTarget() throws Exception {
        assertThrows(Parser.ParseException.class, () -> parseOne("go_to 123"));
    }

    // -------------------------
    // Helper de parseo local
    // -------------------------
    private GotoStatementNode parseOne(String source) throws Exception {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();
        ParserContext context = new ParserContext(tokens);
        return parser.parse(context);
    }
}
