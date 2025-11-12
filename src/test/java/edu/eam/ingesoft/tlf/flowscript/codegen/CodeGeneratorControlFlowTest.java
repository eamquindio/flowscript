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
 * Tests para generación de control de flujo (if, for).
 */
class CodeGeneratorControlFlowTest {
    private CodeGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new CodeGenerator();
    }

    @Test
    void testIfStatementSimple() {
        // FlowScript:
        // funcion test(x: entero) -> entero {
        //     si (x > 0) {
        //         retornar 1
        //     }
        //     retornar 0
        // }

        TypeNode intType = new TypeNode(TypeNode.TypeKind.ENTERO);
        ParameterNode paramX = new ParameterNode("x", intType);

        // Condition: x > 0
        BinaryExpressionNode condition = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.GREATER_THAN,
            new IdentifierNode("x"),
            new IntegerLiteralNode(0L)
        );

        // Then block: retornar 1
        ReturnStatementNode thenReturn = new ReturnStatementNode(new IntegerLiteralNode(1L));
        BlockNode thenBlock = new BlockNode(List.of(thenReturn));

        // If statement
        IfStatementNode.ConditionalBranch branch = new IfStatementNode.ConditionalBranch(condition, thenBlock);
        IfStatementNode ifStmt = new IfStatementNode(List.of(branch));

        // After if: retornar 0
        ReturnStatementNode finalReturn = new ReturnStatementNode(new IntegerLiteralNode(0L));

        BlockNode body = new BlockNode(List.of(ifStmt, finalReturn));

        FunctionDeclarationNode function = new FunctionDeclarationNode(
            "test", List.of(paramX), intType, body
        );

        ProgramNode program = new ProgramNode(List.of(function));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        System.out.println("=== GENERATED CODE ===");
        System.out.println(generatedCode);
        System.out.println("======================");

        assertTrue(generatedCode.contains("public static int test(int x)"));
        assertTrue(generatedCode.contains("if ((x > 0))"));
        assertTrue(generatedCode.contains("return 1;"));
        assertTrue(generatedCode.contains("return 0;"));
    }

    @Test
    void testIfStatementWithElse() {
        // FlowScript:
        // funcion signo(x: entero) -> entero {
        //     si (x >= 0) {
        //         retornar 1
        //     } sino {
        //         retornar -1
        //     }
        // }

        TypeNode intType = new TypeNode(TypeNode.TypeKind.ENTERO);
        ParameterNode paramX = new ParameterNode("x", intType);

        // Condition: x >= 0
        BinaryExpressionNode condition = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.GREATER_EQUAL,
            new IdentifierNode("x"),
            new IntegerLiteralNode(0L)
        );

        // Then block: retornar 1
        ReturnStatementNode thenReturn = new ReturnStatementNode(new IntegerLiteralNode(1L));
        BlockNode thenBlock = new BlockNode(List.of(thenReturn));

        // Else block: retornar -1
        ReturnStatementNode elseReturn = new ReturnStatementNode(new IntegerLiteralNode(-1L));
        BlockNode elseBlock = new BlockNode(List.of(elseReturn));

        // If statement with else
        IfStatementNode.ConditionalBranch ifBranch = new IfStatementNode.ConditionalBranch(condition, thenBlock);
        IfStatementNode.ConditionalBranch elseBranch = new IfStatementNode.ConditionalBranch(null, elseBlock);
        IfStatementNode ifStmt = new IfStatementNode(List.of(ifBranch, elseBranch));

        BlockNode body = new BlockNode(List.of(ifStmt));

        FunctionDeclarationNode function = new FunctionDeclarationNode(
            "signo", List.of(paramX), intType, body
        );

        ProgramNode program = new ProgramNode(List.of(function));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        System.out.println("=== GENERATED CODE ===");
        System.out.println(generatedCode);
        System.out.println("======================");

        assertTrue(generatedCode.contains("public static int signo(int x)"));
        assertTrue(generatedCode.contains("if ((x >= 0))"));
        assertTrue(generatedCode.contains("} else {"));
        assertTrue(generatedCode.contains("return 1;"));
        assertTrue(generatedCode.contains("return -1;"));
    }

    @Test
    void testIfStatementNested() {
        // FlowScript:
        // funcion categorizar(x: entero) -> entero {
        //     si (x > 0) {
        //         si (x > 10) {
        //             retornar 2
        //         }
        //         retornar 1
        //     }
        //     retornar 0
        // }

        TypeNode intType = new TypeNode(TypeNode.TypeKind.ENTERO);
        ParameterNode paramX = new ParameterNode("x", intType);

        // Inner condition: x > 10
        BinaryExpressionNode innerCondition = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.GREATER_THAN,
            new IdentifierNode("x"),
            new IntegerLiteralNode(10L)
        );
        ReturnStatementNode innerReturn = new ReturnStatementNode(new IntegerLiteralNode(2L));
        BlockNode innerThenBlock = new BlockNode(List.of(innerReturn));
        IfStatementNode.ConditionalBranch innerBranch = new IfStatementNode.ConditionalBranch(innerCondition, innerThenBlock);
        IfStatementNode innerIf = new IfStatementNode(List.of(innerBranch));

        // Outer then block: inner if + return 1
        ReturnStatementNode middleReturn = new ReturnStatementNode(new IntegerLiteralNode(1L));
        BlockNode outerThenBlock = new BlockNode(List.of(innerIf, middleReturn));

        // Outer condition: x > 0
        BinaryExpressionNode outerCondition = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.GREATER_THAN,
            new IdentifierNode("x"),
            new IntegerLiteralNode(0L)
        );
        IfStatementNode.ConditionalBranch outerBranch = new IfStatementNode.ConditionalBranch(outerCondition, outerThenBlock);
        IfStatementNode outerIf = new IfStatementNode(List.of(outerBranch));

        // Final return
        ReturnStatementNode finalReturn = new ReturnStatementNode(new IntegerLiteralNode(0L));

        BlockNode body = new BlockNode(List.of(outerIf, finalReturn));

        FunctionDeclarationNode function = new FunctionDeclarationNode(
            "categorizar", List.of(paramX), intType, body
        );

        ProgramNode program = new ProgramNode(List.of(function));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        assertTrue(generatedCode.contains("public static int categorizar(int x)"));
        assertTrue(generatedCode.contains("if ((x > 0))"));
        assertTrue(generatedCode.contains("if ((x > 10))"));
        assertTrue(generatedCode.contains("return 2;"));
        assertTrue(generatedCode.contains("return 1;"));
        assertTrue(generatedCode.contains("return 0;"));
    }

    @Test
    void testForStatementSimple() {
        // FlowScript:
        // funcion suma_rango(n: entero) -> entero {
        //     suma = 0
        //     para (i en rango(0, n)) {
        //         suma = suma + i
        //     }
        //     retornar suma
        // }

        TypeNode intType = new TypeNode(TypeNode.TypeKind.ENTERO);
        ParameterNode paramN = new ParameterNode("n", intType);

        // suma = 0
        AssignmentStatementNode initSuma = new AssignmentStatementNode(
            new IdentifierNode("suma"),
            new IntegerLiteralNode(0L)
        );

        // rango(0, n) - this is the iterable expression
        FunctionCallNode rangoCall = new FunctionCallNode(
            new IdentifierNode("rango"),
            List.of(new IntegerLiteralNode(0L), new IdentifierNode("n"))
        );

        // suma = suma + i
        BinaryExpressionNode sumExpr = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.ADD,
            new IdentifierNode("suma"),
            new IdentifierNode("i")
        );
        AssignmentStatementNode loopAssignment = new AssignmentStatementNode(
            new IdentifierNode("suma"),
            sumExpr
        );
        BlockNode loopBody = new BlockNode(List.of(loopAssignment));

        // para (i en rango(0, n))
        ForStatementNode forStmt = new ForStatementNode("i", rangoCall, loopBody);

        // retornar suma
        ReturnStatementNode returnStmt = new ReturnStatementNode(new IdentifierNode("suma"));

        BlockNode body = new BlockNode(List.of(initSuma, forStmt, returnStmt));

        FunctionDeclarationNode function = new FunctionDeclarationNode(
            "suma_rango", List.of(paramN), intType, body
        );

        ProgramNode program = new ProgramNode(List.of(function));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        System.out.println("=== GENERATED CODE ===");
        System.out.println(generatedCode);
        System.out.println("======================");

        assertTrue(generatedCode.contains("public static int suma_rango(int n)"));
        assertTrue(generatedCode.contains("Object suma = 0;"));
        assertTrue(generatedCode.contains("for (Object i :"));
        assertTrue(generatedCode.contains("suma = (suma + i);"));
        assertTrue(generatedCode.contains("return suma;"));
    }

    @Test
    void testForStatementWithList() {
        // FlowScript:
        // funcion procesar_lista(items: lista) -> vacio {
        //     para (item en items) {
        //         println(item)
        //     }
        // }

        TypeNode listType = new TypeNode(TypeNode.TypeKind.LISTA);
        TypeNode voidType = new TypeNode(TypeNode.TypeKind.VACIO);
        ParameterNode paramItems = new ParameterNode("items", listType);

        // println(item)
        FunctionCallNode printlnCall = new FunctionCallNode(
            new IdentifierNode("println"),
            List.of(new IdentifierNode("item"))
        );
        ExpressionStatementNode printStmt = new ExpressionStatementNode(printlnCall);
        BlockNode loopBody = new BlockNode(List.of(printStmt));

        // para (item en items)
        ForStatementNode forStmt = new ForStatementNode("item", new IdentifierNode("items"), loopBody);

        BlockNode body = new BlockNode(List.of(forStmt));

        FunctionDeclarationNode function = new FunctionDeclarationNode(
            "procesar_lista", List.of(paramItems), voidType, body
        );

        ProgramNode program = new ProgramNode(List.of(function));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        assertTrue(generatedCode.contains("public static void procesar_lista(List<Object> items)"));
        assertTrue(generatedCode.contains("for (Object item : (List<Object>) items)"));
        assertTrue(generatedCode.contains("println(item);"));
    }

    @Test
    void testForStatementNested() {
        // FlowScript:
        // funcion matriz_suma(n: entero) -> entero {
        //     total = 0
        //     para (i en rango(0, n)) {
        //         para (j en rango(0, n)) {
        //             total = total + 1
        //         }
        //     }
        //     retornar total
        // }

        TypeNode intType = new TypeNode(TypeNode.TypeKind.ENTERO);
        ParameterNode paramN = new ParameterNode("n", intType);

        // total = 0
        AssignmentStatementNode initTotal = new AssignmentStatementNode(
            new IdentifierNode("total"),
            new IntegerLiteralNode(0L)
        );

        // total = total + 1
        BinaryExpressionNode addOne = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.ADD,
            new IdentifierNode("total"),
            new IntegerLiteralNode(1L)
        );
        AssignmentStatementNode innerAssignment = new AssignmentStatementNode(
            new IdentifierNode("total"),
            addOne
        );
        BlockNode innerBody = new BlockNode(List.of(innerAssignment));

        // Inner for: para (j en rango(0, n))
        FunctionCallNode innerRango = new FunctionCallNode(
            new IdentifierNode("rango"),
            List.of(new IntegerLiteralNode(0L), new IdentifierNode("n"))
        );
        ForStatementNode innerFor = new ForStatementNode("j", innerRango, innerBody);

        // Outer body
        BlockNode outerBody = new BlockNode(List.of(innerFor));

        // Outer for: para (i en rango(0, n))
        FunctionCallNode outerRango = new FunctionCallNode(
            new IdentifierNode("rango"),
            List.of(new IntegerLiteralNode(0L), new IdentifierNode("n"))
        );
        ForStatementNode outerFor = new ForStatementNode("i", outerRango, outerBody);

        // retornar total
        ReturnStatementNode returnStmt = new ReturnStatementNode(new IdentifierNode("total"));

        BlockNode body = new BlockNode(List.of(initTotal, outerFor, returnStmt));

        FunctionDeclarationNode function = new FunctionDeclarationNode(
            "matriz_suma", List.of(paramN), intType, body
        );

        ProgramNode program = new ProgramNode(List.of(function));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        assertTrue(generatedCode.contains("public static int matriz_suma(int n)"));
        assertTrue(generatedCode.contains("Object total = 0;"));
        assertTrue(generatedCode.contains("for (Object i :"));
        assertTrue(generatedCode.contains("for (Object j :"));
        assertTrue(generatedCode.contains("total = (total + 1);"));
        assertTrue(generatedCode.contains("return total;"));
    }
}
