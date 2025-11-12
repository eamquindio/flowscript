package edu.eam.ingesoft.tlf.flowscript.codegen;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.semantic.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para generación de literales simples.
 * Enfoque TDD: un test por cada tipo de literal.
 */
class CodeGeneratorLiteralsTest {
    private CodeGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new CodeGenerator();
    }

    @Test
    void testIntegerLiteral() {
        // Given
        IntegerLiteralNode node = new IntegerLiteralNode(42L);

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("42", result, "Should generate integer value 42");
    }

    @Test
    void testIntegerLiteralNegative() {
        // Given
        IntegerLiteralNode node = new IntegerLiteralNode(-10L);

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("-10", result, "Should generate negative integer -10");
    }

    @Test
    void testIntegerLiteralZero() {
        // Given
        IntegerLiteralNode node = new IntegerLiteralNode(0L);

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("0", result, "Should generate zero");
    }

    @Test
    void testDecimalLiteral() {
        // Given
        DecimalLiteralNode node = new DecimalLiteralNode(3.14);

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("3.14", result, "Should generate decimal value 3.14");
    }

    @Test
    void testDecimalLiteralNegative() {
        // Given
        DecimalLiteralNode node = new DecimalLiteralNode(-2.5);

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("-2.5", result, "Should generate negative decimal -2.5");
    }

    @Test
    void testBooleanLiteralTrue() {
        // Given
        BooleanLiteralNode node = new BooleanLiteralNode(true);

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("true", result, "Should generate 'true' for boolean true");
    }

    @Test
    void testBooleanLiteralFalse() {
        // Given
        BooleanLiteralNode node = new BooleanLiteralNode(false);

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("false", result, "Should generate 'false' for boolean false");
    }

    @Test
    void testStringLiteralSimple() {
        // Given
        StringLiteralNode node = new StringLiteralNode("Hello World");

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("\"Hello World\"", result, "Should generate quoted string");
    }

    @Test
    void testStringLiteralWithEscapes() {
        // Given
        StringLiteralNode node = new StringLiteralNode("Line 1\nLine 2\tTabbed");

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("\"Line 1\\nLine 2\\tTabbed\"", result, "Should escape newline and tab");
    }

    @Test
    void testStringLiteralWithQuotes() {
        // Given
        StringLiteralNode node = new StringLiteralNode("He said \"Hello\"");

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("\"He said \\\"Hello\\\"\"", result, "Should escape quotes");
    }

    @Test
    void testStringLiteralWithBackslash() {
        // Given
        StringLiteralNode node = new StringLiteralNode("Path\\to\\file");

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("\"Path\\\\to\\\\file\"", result, "Should escape backslashes");
    }

    @Test
    void testStringLiteralEmpty() {
        // Given
        StringLiteralNode node = new StringLiteralNode("");

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("\"\"", result, "Should generate empty quotes");
    }

    @Test
    void testNullLiteral() {
        // Given
        NullLiteralNode node = new NullLiteralNode();

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("null", result, "Should generate 'null'");
    }
}
