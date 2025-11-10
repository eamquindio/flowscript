package com.flowscript.semantic;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.semantic.symbols.*;
import com.flowscript.semantic.types.Type;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.literales.IntegerLiteralNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SymbolTable.
 * Tests scope management, variable declaration, lookup, and unused detection.
 */
public class SymbolTableTest {

    private SymbolTable symbolTable;
    private ASTNode mockNode;

    @BeforeEach
    public void setUp() {
        symbolTable = new SymbolTable();
        // Create a mock token for the AST node
        Token mockToken = new Token(TokenType.INTEGER_LITERAL, "0", 1, 1, 0);
        mockNode = new IntegerLiteralNode(mockToken);
    }

    // ========== SCOPE MANAGEMENT TESTS ==========

    @Test
    public void testGlobalScopeExists() {
        Scope currentScope = symbolTable.getCurrentScope();
        assertNotNull(currentScope);
        assertEquals(ScopeType.GLOBAL, currentScope.getType());
    }

    @Test
    public void testEnterAndExitScope() {
        symbolTable.enterScope(ScopeType.FUNCTION);
        Scope functionScope = symbolTable.getCurrentScope();
        assertEquals(ScopeType.FUNCTION, functionScope.getType());

        symbolTable.exitScope();
        Scope globalScope = symbolTable.getCurrentScope();
        assertEquals(ScopeType.GLOBAL, globalScope.getType());
    }

    @Test
    public void testNestedScopes() {
        symbolTable.enterScope(ScopeType.FUNCTION);
        symbolTable.enterScope(ScopeType.BLOCK);

        Scope blockScope = symbolTable.getCurrentScope();
        assertEquals(ScopeType.BLOCK, blockScope.getType());

        symbolTable.exitScope();
        Scope functionScope = symbolTable.getCurrentScope();
        assertEquals(ScopeType.FUNCTION, functionScope.getType());

        symbolTable.exitScope();
        Scope globalScope = symbolTable.getCurrentScope();
        assertEquals(ScopeType.GLOBAL, globalScope.getType());
    }

    @Test
    public void testCannotExitGlobalScope() {
        assertThrows(IllegalStateException.class, () -> {
            symbolTable.exitScope();
        });
    }

    // ========== VARIABLE DECLARATION TESTS ==========

    @Test
    public void testDeclareVariable() {
        symbolTable.declareVariable("x", Type.INTEGER, mockNode);

        assertTrue(symbolTable.isVariableDeclaredInCurrentScope("x"));
    }

    @Test
    public void testDeclareMultipleVariables() {
        symbolTable.declareVariable("x", Type.INTEGER, mockNode);
        symbolTable.declareVariable("y", Type.TEXT, mockNode);
        symbolTable.declareVariable("z", Type.BOOLEAN, mockNode);

        assertTrue(symbolTable.isVariableDeclaredInCurrentScope("x"));
        assertTrue(symbolTable.isVariableDeclaredInCurrentScope("y"));
        assertTrue(symbolTable.isVariableDeclaredInCurrentScope("z"));
    }

    @Test
    public void testDeclareVariableInNestedScope() {
        symbolTable.declareVariable("global", Type.INTEGER, mockNode);

        symbolTable.enterScope(ScopeType.FUNCTION);
        symbolTable.declareVariable("local", Type.TEXT, mockNode);

        assertTrue(symbolTable.isVariableDeclaredInCurrentScope("local"));
        assertFalse(symbolTable.isVariableDeclaredInCurrentScope("global"));

        symbolTable.exitScope();
    }

    @Test
    public void testDeclareParameter() {
        symbolTable.enterScope(ScopeType.FUNCTION);
        symbolTable.declareVariable("param", Type.INTEGER, mockNode, false, true);

        Symbol symbol = symbolTable.lookupVariable("param");
        assertNotNull(symbol);
        assertTrue(symbol instanceof VariableSymbol);
        assertTrue(((VariableSymbol) symbol).isParameter());
    }

    @Test
    public void testDeclareConstant() {
        symbolTable.declareVariable("PI", Type.DECIMAL, mockNode, true, false);

        Symbol symbol = symbolTable.lookupVariable("PI");
        assertNotNull(symbol);
        assertTrue(symbol instanceof VariableSymbol);
        assertTrue(((VariableSymbol) symbol).isConstant());
    }

    // ========== VARIABLE LOOKUP TESTS ==========

    @Test
    public void testLookupDeclaredVariable() {
        symbolTable.declareVariable("x", Type.INTEGER, mockNode);

        Symbol symbol = symbolTable.lookupVariable("x");
        assertNotNull(symbol);
        assertEquals("x", symbol.getName());
        assertEquals(Type.INTEGER, ((VariableSymbol) symbol).getType());
    }

    @Test
    public void testLookupUndeclaredVariable() {
        Symbol symbol = symbolTable.lookupVariable("undeclared");
        assertNull(symbol);
    }

    @Test
    public void testLookupInParentScope() {
        symbolTable.declareVariable("global", Type.INTEGER, mockNode);

        symbolTable.enterScope(ScopeType.FUNCTION);

        Symbol symbol = symbolTable.lookupVariable("global");
        assertNotNull(symbol);
        assertEquals("global", symbol.getName());

        symbolTable.exitScope();
    }

    @Test
    public void testShadowing() {
        // Declare in global scope
        symbolTable.declareVariable("x", Type.INTEGER, mockNode);

        // Declare same name in function scope
        symbolTable.enterScope(ScopeType.FUNCTION);
        symbolTable.declareVariable("x", Type.TEXT, mockNode);

        // Lookup should find the innermost (function scope) variable
        Symbol symbol = symbolTable.lookupVariable("x");
        assertNotNull(symbol);
        assertEquals(Type.TEXT, ((VariableSymbol) symbol).getType());

        symbolTable.exitScope();

        // After exiting, should find global variable
        symbol = symbolTable.lookupVariable("x");
        assertEquals(Type.INTEGER, ((VariableSymbol) symbol).getType());
    }

    @Test
    public void testIsVariableDeclaredInCurrentScope() {
        symbolTable.declareVariable("global", Type.INTEGER, mockNode);
        assertTrue(symbolTable.isVariableDeclaredInCurrentScope("global"));

        symbolTable.enterScope(ScopeType.FUNCTION);
        assertFalse(symbolTable.isVariableDeclaredInCurrentScope("global"));

        symbolTable.declareVariable("local", Type.TEXT, mockNode);
        assertTrue(symbolTable.isVariableDeclaredInCurrentScope("local"));

        symbolTable.exitScope();
    }

    // ========== UNUSED VARIABLE DETECTION TESTS ==========

    @Test
    public void testUnusedVariableDetection() {
        symbolTable.declareVariable("unused", Type.INTEGER, mockNode);

        List<Symbol> unused = symbolTable.getUnusedSymbols();
        assertEquals(1, unused.size());
        assertEquals("unused", unused.get(0).getName());
    }

    @Test
    public void testUsedVariableNotInUnusedList() {
        symbolTable.declareVariable("used", Type.INTEGER, mockNode);

        // Lookup marks variable as used
        symbolTable.lookupVariable("used");

        List<Symbol> unused = symbolTable.getUnusedSymbols();
        assertEquals(0, unused.size());
    }

    @Test
    public void testMultipleUnusedVariables() {
        symbolTable.declareVariable("unused1", Type.INTEGER, mockNode);
        symbolTable.declareVariable("unused2", Type.TEXT, mockNode);
        symbolTable.declareVariable("used", Type.BOOLEAN, mockNode);

        symbolTable.lookupVariable("used");

        List<Symbol> unused = symbolTable.getUnusedSymbols();
        assertEquals(2, unused.size());
    }

    @Test
    public void testUnusedParameterDetection() {
        symbolTable.enterScope(ScopeType.FUNCTION);
        symbolTable.declareVariable("param", Type.INTEGER, mockNode, false, true);

        List<Symbol> unused = symbolTable.getUnusedSymbols();
        assertEquals(1, unused.size());

        VariableSymbol varSymbol = (VariableSymbol) unused.get(0);
        assertTrue(varSymbol.isParameter());

        symbolTable.exitScope();
    }

    @Test
    public void testUnusedVariableAcrossScopes() {
        symbolTable.declareVariable("globalUnused", Type.INTEGER, mockNode);

        symbolTable.enterScope(ScopeType.FUNCTION);
        symbolTable.declareVariable("functionUnused", Type.TEXT, mockNode);

        List<Symbol> unused = symbolTable.getUnusedSymbols();
        assertEquals(2, unused.size());

        symbolTable.exitScope();
    }

    // ========== EDGE CASES ==========

    @Test
    public void testVariableWithSameNameInDifferentScopes() {
        symbolTable.declareVariable("x", Type.INTEGER, mockNode);

        symbolTable.enterScope(ScopeType.FUNCTION);
        symbolTable.declareVariable("x", Type.TEXT, mockNode);

        symbolTable.enterScope(ScopeType.BLOCK);
        symbolTable.declareVariable("x", Type.BOOLEAN, mockNode);

        Symbol symbol = symbolTable.lookupVariable("x");
        assertEquals(Type.BOOLEAN, ((VariableSymbol) symbol).getType());

        symbolTable.exitScope();
        symbol = symbolTable.lookupVariable("x");
        assertEquals(Type.TEXT, ((VariableSymbol) symbol).getType());

        symbolTable.exitScope();
        symbol = symbolTable.lookupVariable("x");
        assertEquals(Type.INTEGER, ((VariableSymbol) symbol).getType());
    }

    @Test
    public void testLookupMarksSymbolAsUsed() {
        symbolTable.declareVariable("var", Type.INTEGER, mockNode);

        Symbol symbol = symbolTable.lookupVariable("var");
        assertTrue(symbol.isUsed());
    }
}
