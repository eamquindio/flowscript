package edu.eam.ingesoft.tlf.flowscript.codegen;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para generación de literales complejos (List y Object).
 */
class CodeGeneratorComplexLiteralsTest {
    private CodeGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new CodeGenerator();
    }

    @Test
    void testListLiteralEmpty() {
        // Given
        ListLiteralNode node = new ListLiteralNode(List.of());

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("List.of()", result, "Should generate empty list");
    }

    @Test
    void testListLiteralWithIntegers() {
        // Given
        ListLiteralNode node = new ListLiteralNode(List.of(
            new IntegerLiteralNode(1L),
            new IntegerLiteralNode(2L),
            new IntegerLiteralNode(3L)
        ));

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("List.of(1, 2, 3)", result, "Should generate list with integers");
    }

    @Test
    void testListLiteralWithStrings() {
        // Given
        ListLiteralNode node = new ListLiteralNode(List.of(
            new StringLiteralNode("hello"),
            new StringLiteralNode("world")
        ));

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("List.of(\"hello\", \"world\")", result, "Should generate list with strings");
    }

    @Test
    void testListLiteralWithMixedTypes() {
        // Given
        ListLiteralNode node = new ListLiteralNode(List.of(
            new IntegerLiteralNode(42L),
            new StringLiteralNode("answer"),
            new BooleanLiteralNode(true)
        ));

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("List.of(42, \"answer\", true)", result, "Should generate list with mixed types");
    }

    @Test
    void testListLiteralNested() {
        // Given - lista dentro de lista: [[1, 2], [3, 4]]
        ListLiteralNode innerList1 = new ListLiteralNode(List.of(
            new IntegerLiteralNode(1L),
            new IntegerLiteralNode(2L)
        ));
        ListLiteralNode innerList2 = new ListLiteralNode(List.of(
            new IntegerLiteralNode(3L),
            new IntegerLiteralNode(4L)
        ));
        ListLiteralNode outerList = new ListLiteralNode(List.of(innerList1, innerList2));

        // When
        outerList.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("List.of(List.of(1, 2), List.of(3, 4))", result, "Should generate nested lists");
    }

    @Test
    void testObjectLiteralEmpty() {
        // Given
        ObjectLiteralNode node = new ObjectLiteralNode(List.of());

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("Map.of()", result, "Should generate empty object");
    }

    @Test
    void testObjectLiteralSingleProperty() {
        // Given - {name: "John"}
        ObjectLiteralNode node = new ObjectLiteralNode(List.of(
            new ObjectLiteralNode.ObjectMember("name", new StringLiteralNode("John"))
        ));

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("Map.of(\"name\", \"John\")", result, "Should generate object with single property");
    }

    @Test
    void testObjectLiteralMultipleProperties() {
        // Given - {name: "John", age: 30, active: true}
        ObjectLiteralNode node = new ObjectLiteralNode(List.of(
            new ObjectLiteralNode.ObjectMember("name", new StringLiteralNode("John")),
            new ObjectLiteralNode.ObjectMember("age", new IntegerLiteralNode(30L)),
            new ObjectLiteralNode.ObjectMember("active", new BooleanLiteralNode(true))
        ));

        // When
        node.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("Map.of(\"name\", \"John\", \"age\", 30, \"active\", true)",
            result, "Should generate object with multiple properties");
    }

    @Test
    void testObjectLiteralNested() {
        // Given - {user: {name: "John", age: 30}}
        ObjectLiteralNode innerObj = new ObjectLiteralNode(List.of(
            new ObjectLiteralNode.ObjectMember("name", new StringLiteralNode("John")),
            new ObjectLiteralNode.ObjectMember("age", new IntegerLiteralNode(30L))
        ));
        ObjectLiteralNode outerObj = new ObjectLiteralNode(List.of(
            new ObjectLiteralNode.ObjectMember("user", innerObj)
        ));

        // When
        outerObj.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("Map.of(\"user\", Map.of(\"name\", \"John\", \"age\", 30))",
            result, "Should generate nested objects");
    }

    @Test
    void testObjectLiteralWithListValue() {
        // Given - {items: [1, 2, 3]}
        ListLiteralNode list = new ListLiteralNode(List.of(
            new IntegerLiteralNode(1L),
            new IntegerLiteralNode(2L),
            new IntegerLiteralNode(3L)
        ));
        ObjectLiteralNode obj = new ObjectLiteralNode(List.of(
            new ObjectLiteralNode.ObjectMember("items", list)
        ));

        // When
        obj.accept(generator);
        String result = generator.getGeneratedCode();

        // Then
        assertEquals("Map.of(\"items\", List.of(1, 2, 3))",
            result, "Should generate object with list value");
    }
}
