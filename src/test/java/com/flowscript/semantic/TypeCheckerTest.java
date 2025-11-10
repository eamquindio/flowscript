package com.flowscript.semantic;

import com.flowscript.semantic.checker.Operator;
import com.flowscript.semantic.checker.TypeChecker;
import com.flowscript.semantic.functions.FunctionTable;
import com.flowscript.semantic.symbols.SymbolTable;
import com.flowscript.semantic.types.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TypeChecker.
 * Tests type compatibility, operator result types, numeric promotion, and error cases.
 */
public class TypeCheckerTest {

    private TypeChecker typeChecker;
    private SymbolTable symbolTable;
    private FunctionTable functionTable;

    @BeforeEach
    public void setUp() {
        symbolTable = new SymbolTable();
        functionTable = new FunctionTable();
        typeChecker = new TypeChecker(symbolTable, functionTable);
    }

    // ========== TYPE COMPATIBILITY TESTS ==========

    @Test
    public void testExactTypeMatch() {
        assertTrue(typeChecker.isCompatible(Type.INTEGER, Type.INTEGER));
        assertTrue(typeChecker.isCompatible(Type.TEXT, Type.TEXT));
        assertTrue(typeChecker.isCompatible(Type.BOOLEAN, Type.BOOLEAN));
    }

    @Test
    public void testIncompatibleTypes() {
        assertFalse(typeChecker.isCompatible(Type.INTEGER, Type.TEXT));
        assertFalse(typeChecker.isCompatible(Type.BOOLEAN, Type.INTEGER));
        assertFalse(typeChecker.isCompatible(Type.LIST, Type.OBJECT));
    }

    @Test
    public void testAnyTypeCompatibility() {
        assertTrue(typeChecker.isCompatible(Type.ANY, Type.INTEGER));
        assertTrue(typeChecker.isCompatible(Type.INTEGER, Type.ANY));
        assertTrue(typeChecker.isCompatible(Type.ANY, Type.TEXT));
        assertTrue(typeChecker.isCompatible(Type.ANY, Type.ANY));
    }

    @Test
    public void testNumericPromotion() {
        // entero -> decimal
        assertTrue(typeChecker.isCompatible(Type.DECIMAL, Type.INTEGER));

        // But not decimal -> entero
        assertFalse(typeChecker.isCompatible(Type.INTEGER, Type.DECIMAL));
    }

    @Test
    public void testTextCompatibility() {
        // Any type converts to texto in concatenation context
        assertTrue(typeChecker.isCompatible(Type.TEXT, Type.INTEGER));
        assertTrue(typeChecker.isCompatible(Type.TEXT, Type.BOOLEAN));
        assertTrue(typeChecker.isCompatible(Type.TEXT, Type.DECIMAL));
    }

    @Test
    public void testNullCompatibility() {
        // null is compatible with object and list types (as per design)
        assertTrue(typeChecker.isCompatible(Type.OBJECT, Type.NULL));
        assertTrue(typeChecker.isCompatible(Type.LIST, Type.NULL));

        // Note: Current implementation doesn't restrict null from primitives
        // In a stricter type system, these would be false
        // For now, null compatibility is only checked for object/list
    }

    // ========== ARITHMETIC OPERATORS TESTS ==========

    @Test
    public void testPlusWithIntegers() {
        Type result = typeChecker.getOperatorResultType(Operator.PLUS, Type.INTEGER, Type.INTEGER);
        assertEquals(Type.INTEGER, result);
    }

    @Test
    public void testPlusWithDecimals() {
        Type result = typeChecker.getOperatorResultType(Operator.PLUS, Type.DECIMAL, Type.DECIMAL);
        assertEquals(Type.DECIMAL, result);
    }

    @Test
    public void testPlusWithMixedNumeric() {
        // entero + decimal -> decimal
        Type result = typeChecker.getOperatorResultType(Operator.PLUS, Type.INTEGER, Type.DECIMAL);
        assertEquals(Type.DECIMAL, result);

        result = typeChecker.getOperatorResultType(Operator.PLUS, Type.DECIMAL, Type.INTEGER);
        assertEquals(Type.DECIMAL, result);
    }

    @Test
    public void testPlusWithText() {
        // texto + any -> texto
        Type result = typeChecker.getOperatorResultType(Operator.PLUS, Type.TEXT, Type.INTEGER);
        assertEquals(Type.TEXT, result);

        result = typeChecker.getOperatorResultType(Operator.PLUS, Type.INTEGER, Type.TEXT);
        assertEquals(Type.TEXT, result);
    }

    @Test
    public void testMinusWithNumeric() {
        assertEquals(Type.INTEGER, typeChecker.getOperatorResultType(Operator.MINUS, Type.INTEGER, Type.INTEGER));
        assertEquals(Type.DECIMAL, typeChecker.getOperatorResultType(Operator.MINUS, Type.DECIMAL, Type.DECIMAL));
        assertEquals(Type.DECIMAL, typeChecker.getOperatorResultType(Operator.MINUS, Type.INTEGER, Type.DECIMAL));
    }

    @Test
    public void testMultiplyWithNumeric() {
        assertEquals(Type.INTEGER, typeChecker.getOperatorResultType(Operator.MULTIPLY, Type.INTEGER, Type.INTEGER));
        assertEquals(Type.DECIMAL, typeChecker.getOperatorResultType(Operator.MULTIPLY, Type.DECIMAL, Type.DECIMAL));
        assertEquals(Type.DECIMAL, typeChecker.getOperatorResultType(Operator.MULTIPLY, Type.INTEGER, Type.DECIMAL));
    }

    @Test
    public void testDivideWithNumeric() {
        assertEquals(Type.INTEGER, typeChecker.getOperatorResultType(Operator.DIVIDE, Type.INTEGER, Type.INTEGER));
        assertEquals(Type.DECIMAL, typeChecker.getOperatorResultType(Operator.DIVIDE, Type.DECIMAL, Type.DECIMAL));
        assertEquals(Type.DECIMAL, typeChecker.getOperatorResultType(Operator.DIVIDE, Type.INTEGER, Type.DECIMAL));
    }

    @Test
    public void testModuloWithIntegers() {
        Type result = typeChecker.getOperatorResultType(Operator.MODULO, Type.INTEGER, Type.INTEGER);
        assertEquals(Type.INTEGER, result);
    }

    @Test
    public void testModuloWithNonIntegers() {
        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getOperatorResultType(Operator.MODULO, Type.DECIMAL, Type.INTEGER);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getOperatorResultType(Operator.MODULO, Type.TEXT, Type.INTEGER);
        });
    }

    @Test
    public void testArithmeticWithInvalidTypes() {
        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getOperatorResultType(Operator.MINUS, Type.TEXT, Type.TEXT);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getOperatorResultType(Operator.MULTIPLY, Type.BOOLEAN, Type.INTEGER);
        });
    }

    // ========== LOGICAL OPERATORS TESTS ==========

    @Test
    public void testLogicalAndWithBooleans() {
        Type result = typeChecker.getOperatorResultType(Operator.AND, Type.BOOLEAN, Type.BOOLEAN);
        assertEquals(Type.BOOLEAN, result);
    }

    @Test
    public void testLogicalOrWithBooleans() {
        Type result = typeChecker.getOperatorResultType(Operator.OR, Type.BOOLEAN, Type.BOOLEAN);
        assertEquals(Type.BOOLEAN, result);
    }

    @Test
    public void testLogicalOperatorsWithNonBooleans() {
        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getOperatorResultType(Operator.AND, Type.INTEGER, Type.BOOLEAN);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getOperatorResultType(Operator.OR, Type.BOOLEAN, Type.TEXT);
        });
    }

    @Test
    public void testLogicalNotWithBoolean() {
        Type result = typeChecker.getUnaryOperatorResultType(Operator.NOT, Type.BOOLEAN);
        assertEquals(Type.BOOLEAN, result);
    }

    @Test
    public void testLogicalNotWithNonBoolean() {
        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getUnaryOperatorResultType(Operator.NOT, Type.INTEGER);
        });
    }

    // ========== RELATIONAL OPERATORS TESTS ==========

    @Test
    public void testRelationalWithNumeric() {
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.LESS_THAN, Type.INTEGER, Type.INTEGER));
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.GREATER_THAN, Type.DECIMAL, Type.DECIMAL));
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.LESS_EQUAL, Type.INTEGER, Type.DECIMAL));
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.GREATER_EQUAL, Type.DECIMAL, Type.INTEGER));
    }

    @Test
    public void testRelationalWithText() {
        Type result = typeChecker.getOperatorResultType(Operator.LESS_THAN, Type.TEXT, Type.TEXT);
        assertEquals(Type.BOOLEAN, result);
    }

    @Test
    public void testRelationalWithIncomparableTypes() {
        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getOperatorResultType(Operator.LESS_THAN, Type.INTEGER, Type.TEXT);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getOperatorResultType(Operator.GREATER_THAN, Type.BOOLEAN, Type.INTEGER);
        });
    }

    // ========== EQUALITY OPERATORS TESTS ==========

    @Test
    public void testEqualityWithAnyTypes() {
        // Can compare any types
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.EQUAL, Type.INTEGER, Type.INTEGER));
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.EQUAL, Type.TEXT, Type.TEXT));
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.EQUAL, Type.INTEGER, Type.TEXT));
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.NOT_EQUAL, Type.BOOLEAN, Type.OBJECT));
    }

    // ========== UNARY OPERATORS TESTS ==========

    @Test
    public void testUnaryMinusWithNumeric() {
        assertEquals(Type.INTEGER, typeChecker.getUnaryOperatorResultType(Operator.MINUS, Type.INTEGER));
        assertEquals(Type.DECIMAL, typeChecker.getUnaryOperatorResultType(Operator.MINUS, Type.DECIMAL));
    }

    @Test
    public void testUnaryMinusWithNonNumeric() {
        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getUnaryOperatorResultType(Operator.MINUS, Type.TEXT);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            typeChecker.getUnaryOperatorResultType(Operator.MINUS, Type.BOOLEAN);
        });
    }

    // ========== HELPER METHOD TESTS ==========

    @Test
    public void testIsNumeric() {
        assertTrue(typeChecker.isNumeric(Type.INTEGER));
        assertTrue(typeChecker.isNumeric(Type.DECIMAL));
        assertTrue(typeChecker.isNumeric(Type.ANY));
        assertFalse(typeChecker.isNumeric(Type.TEXT));
        assertFalse(typeChecker.isNumeric(Type.BOOLEAN));
    }

    @Test
    public void testIsComparable() {
        // Numbers are comparable
        assertTrue(typeChecker.isComparable(Type.INTEGER, Type.INTEGER));
        assertTrue(typeChecker.isComparable(Type.INTEGER, Type.DECIMAL));
        assertTrue(typeChecker.isComparable(Type.DECIMAL, Type.DECIMAL));

        // Texts are comparable
        assertTrue(typeChecker.isComparable(Type.TEXT, Type.TEXT));

        // ANY is always comparable
        assertTrue(typeChecker.isComparable(Type.ANY, Type.INTEGER));
        assertTrue(typeChecker.isComparable(Type.TEXT, Type.ANY));

        // Different non-numeric types are not comparable
        assertFalse(typeChecker.isComparable(Type.INTEGER, Type.TEXT));
        assertFalse(typeChecker.isComparable(Type.BOOLEAN, Type.INTEGER));
        assertFalse(typeChecker.isComparable(Type.LIST, Type.OBJECT));
    }

    // ========== ANY TYPE TESTS ==========

    @Test
    public void testOperatorsWithAnyType() {
        // When both operands are ANY, result is ANY
        assertEquals(Type.ANY, typeChecker.getOperatorResultType(Operator.PLUS, Type.ANY, Type.ANY));

        // When one operand is ANY but the other is typed, TypeChecker infers
        // the result type based on the known operand. This is by design - ANY acts as
        // a wildcard that allows the operation to proceed with type inference.

        // MINUS: ANY - INTEGER = INTEGER (ANY is treated as numeric)
        assertEquals(Type.INTEGER, typeChecker.getOperatorResultType(Operator.MINUS, Type.ANY, Type.INTEGER));

        // MULTIPLY: DECIMAL * ANY = DECIMAL (ANY is treated as numeric, promotes to decimal)
        assertEquals(Type.DECIMAL, typeChecker.getOperatorResultType(Operator.MULTIPLY, Type.DECIMAL, Type.ANY));

        // MODULO: ANY % ANY = ANY (both unknown)
        assertEquals(Type.ANY, typeChecker.getOperatorResultType(Operator.MODULO, Type.ANY, Type.ANY));

        // Logical operators with ANY return ANY (logical operations need both to be boolean or any)
        assertEquals(Type.ANY, typeChecker.getOperatorResultType(Operator.AND, Type.ANY, Type.ANY));

        // Relational operators always return BOOLEAN (even with ANY operands)
        assertEquals(Type.BOOLEAN, typeChecker.getOperatorResultType(Operator.LESS_THAN, Type.ANY, Type.INTEGER));
    }

    @Test
    public void testUnaryOperatorsWithAnyType() {
        assertEquals(Type.BOOLEAN, typeChecker.getUnaryOperatorResultType(Operator.NOT, Type.ANY));
        assertEquals(Type.ANY, typeChecker.getUnaryOperatorResultType(Operator.MINUS, Type.ANY));
    }
}
