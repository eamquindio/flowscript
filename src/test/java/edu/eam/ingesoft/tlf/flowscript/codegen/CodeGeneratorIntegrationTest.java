package edu.eam.ingesoft.tlf.flowscript.codegen;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de integración E2E que genera una función completa y verifica el código generado.
 */
class CodeGeneratorIntegrationTest {
    private CodeGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new CodeGenerator();
    }

    @Test
    void testSimpleFunctionGeneration() {
        // FlowScript:
        // funcion suma(a: entero, b: entero) -> entero {
        //     resultado = a + b
        //     retornar resultado
        // }

        // Construir AST manualmente
        TypeNode intType = new TypeNode(TypeNode.TypeKind.ENTERO);

        // Parámetros
        ParameterNode paramA = new ParameterNode("a", intType);
        ParameterNode paramB = new ParameterNode("b", intType);

        // Cuerpo de la función
        // resultado = a + b
        IdentifierNode idA = new IdentifierNode("a");
        IdentifierNode idB = new IdentifierNode("b");
        BinaryExpressionNode sum = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.ADD,
            idA,
            idB
        );
        IdentifierNode targetResultado = new IdentifierNode("resultado");
        AssignmentStatementNode assignment = new AssignmentStatementNode(targetResultado, sum);

        // retornar resultado
        IdentifierNode idResultado = new IdentifierNode("resultado");
        ReturnStatementNode returnStmt = new ReturnStatementNode(idResultado);

        // Block
        BlockNode body = new BlockNode(List.of(assignment, returnStmt));

        // Function Declaration
        FunctionDeclarationNode function = new FunctionDeclarationNode(
            "suma",
            List.of(paramA, paramB),
            intType,
            body
        );

        // Program
        ProgramNode program = new ProgramNode(List.of(function));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        System.out.println("=== GENERATED CODE ===");
        System.out.println(generatedCode);
        System.out.println("======================");

        // Verify imports
        assertTrue(generatedCode.contains("import java.util.*;"), "Should have java.util import");
        assertTrue(generatedCode.contains("import java.net.http.*;"), "Should have java.net.http import");

        // Verify class declaration
        assertTrue(generatedCode.contains("public class GeneratedProgram"), "Should have GeneratedProgram class");

        // Verify helper methods
        assertTrue(generatedCode.contains("private static Object getMember"), "Should have getMember helper");
        assertTrue(generatedCode.contains("private static Object getIndex"), "Should have getIndex helper");
        assertTrue(generatedCode.contains("private static void print"), "Should have print helper");
        assertTrue(generatedCode.contains("private static void println"), "Should have println helper");

        // Verify function signature
        assertTrue(generatedCode.contains("public static int suma(int a, int b)"), "Should have correct function signature");

        // Verify function body
        assertTrue(generatedCode.contains("Object resultado = (a + b);"), "Should have assignment with binary expression");
        assertTrue(generatedCode.contains("return resultado;"), "Should have return statement");
    }

    @Test
    void testFunctionWithMultipleOperations() {
        // FlowScript:
        // funcion calcular(x: entero) -> entero {
        //     doble = x * 2
        //     resultado = doble + 10
        //     retornar resultado
        // }

        TypeNode intType = new TypeNode(TypeNode.TypeKind.ENTERO);
        ParameterNode paramX = new ParameterNode("x", intType);

        // doble = x * 2
        BinaryExpressionNode mult = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.MULTIPLY,
            new IdentifierNode("x"),
            new IntegerLiteralNode(2L)
        );
        AssignmentStatementNode assign1 = new AssignmentStatementNode(
            new IdentifierNode("doble"), mult
        );

        // resultado = doble + 10
        BinaryExpressionNode add = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.ADD,
            new IdentifierNode("doble"),
            new IntegerLiteralNode(10L)
        );
        AssignmentStatementNode assign2 = new AssignmentStatementNode(
            new IdentifierNode("resultado"), add
        );

        // retornar resultado
        ReturnStatementNode returnStmt = new ReturnStatementNode(new IdentifierNode("resultado"));

        BlockNode body = new BlockNode(List.of(assign1, assign2, returnStmt));

        FunctionDeclarationNode function = new FunctionDeclarationNode(
            "calcular",
            List.of(paramX),
            intType,
            body
        );

        ProgramNode program = new ProgramNode(List.of(function));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        assertTrue(generatedCode.contains("public static int calcular(int x)"));
        assertTrue(generatedCode.contains("Object doble = (x * 2);"));
        assertTrue(generatedCode.contains("Object resultado = (doble + 10);"));
        assertTrue(generatedCode.contains("return resultado;"));
    }
}
