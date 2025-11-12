package edu.eam.ingesoft.tlf.flowscript.codegen;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para generación de procesos BPMN.
 */
class CodeGeneratorProcessTest {
    private CodeGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new CodeGenerator();
    }

    @Test
    void testSimpleLinearProcess() {
        // FlowScript:
        // proceso SimpleProcess {
        //     inicio iniciar
        //
        //     tarea task1 {
        //         mensaje = "Hello"
        //         println(mensaje)
        //     }
        //
        //     tarea task2 {
        //         resultado = "Done"
        //         println(resultado)
        //     }
        //
        //     fin terminar
        // }

        // Start element - targets the first task
        StartElementNode start = new StartElementNode("task1");

        // Task 1
        AssignmentStatementNode assign1 = new AssignmentStatementNode(
            new IdentifierNode("mensaje"),
            new StringLiteralNode("Hello")
        );
        FunctionCallNode print1 = new FunctionCallNode(
            new IdentifierNode("println"),
            List.of(new IdentifierNode("mensaje"))
        );
        ExpressionStatementNode printStmt1 = new ExpressionStatementNode(print1);
        TaskElementNode task1 = new TaskElementNode("task1", List.of(assign1, printStmt1));

        // Task 2
        AssignmentStatementNode assign2 = new AssignmentStatementNode(
            new IdentifierNode("resultado"),
            new StringLiteralNode("Done")
        );
        FunctionCallNode print2 = new FunctionCallNode(
            new IdentifierNode("println"),
            List.of(new IdentifierNode("resultado"))
        );
        ExpressionStatementNode printStmt2 = new ExpressionStatementNode(print2);
        TaskElementNode task2 = new TaskElementNode("task2", List.of(assign2, printStmt2));

        // End element
        EndElementNode end = new EndElementNode("terminar");

        // Process
        List<ProcessElementNode> elements = List.of(start, task1, task2, end);
        ProcessDeclarationNode process = new ProcessDeclarationNode("SimpleProcess", elements);

        ProgramNode program = new ProgramNode(List.of(process));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        System.out.println("=== GENERATED CODE ===");
        System.out.println(generatedCode);
        System.out.println("======================");

        // Verify process class structure
        assertTrue(generatedCode.contains("public static class SimpleProcess"), "Should have process class");
        assertTrue(generatedCode.contains("private static class Context"), "Should have Context class");

        // Verify task methods
        assertTrue(generatedCode.contains("private void task1()"), "Should have task1 method");
        assertTrue(generatedCode.contains("private void task2()"), "Should have task2 method");

        // Verify context variable access
        assertTrue(generatedCode.contains("context.variables.put(\"mensaje\", \"Hello\")"), "Should set mensaje in context");
        assertTrue(generatedCode.contains("context.variables.put(\"resultado\", \"Done\")"), "Should set resultado in context");

        // Verify println calls
        assertTrue(generatedCode.contains("println(context.variables.get(\"mensaje\"))"), "Should call println with context.variables.get(\"mensaje\")");
        assertTrue(generatedCode.contains("println(context.variables.get(\"resultado\"))"), "Should call println with context.variables.get(\"resultado\")");

        // Verify run method
        assertTrue(generatedCode.contains("public void run()"), "Should have run method");
        assertTrue(generatedCode.contains("task1();"), "Should call task1");
        assertTrue(generatedCode.contains("task2();"), "Should call task2");
    }

    @Test
    void testProcessWithExclusiveGateway() {
        // FlowScript:
        // proceso ConditionalProcess {
        //     inicio start
        //
        //     tarea check {
        //         valor = 10
        //     }
        //
        //     gateway exclusive decision {
        //         cuando (valor > 5) -> high
        //         cuando (valor <= 5) -> low
        //     }
        //
        //     tarea high {
        //         resultado = "High"
        //     }
        //
        //     tarea low {
        //         resultado = "Low"
        //     }
        //
        //     fin end
        // }

        StartElementNode start = new StartElementNode("check");

        // Task: check
        AssignmentStatementNode assignValor = new AssignmentStatementNode(
            new IdentifierNode("valor"),
            new IntegerLiteralNode(10L)
        );
        TaskElementNode checkTask = new TaskElementNode("check", List.of(assignValor));

        // Gateway branches
        BinaryExpressionNode condition1 = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.GREATER_THAN,
            new IdentifierNode("valor"),
            new IntegerLiteralNode(5L)
        );
        ExclusiveGatewayNode.ConditionalBranch branch1 = new ExclusiveGatewayNode.ConditionalBranch(condition1, "high");

        BinaryExpressionNode condition2 = new BinaryExpressionNode(
            BinaryExpressionNode.Operator.LESS_EQUAL,
            new IdentifierNode("valor"),
            new IntegerLiteralNode(5L)
        );
        ExclusiveGatewayNode.ConditionalBranch branch2 = new ExclusiveGatewayNode.ConditionalBranch(condition2, "low");

        ExclusiveGatewayNode gateway = new ExclusiveGatewayNode(
            "decision",
            List.of(branch1, branch2)
        );

        // Task: high
        AssignmentStatementNode assignHigh = new AssignmentStatementNode(
            new IdentifierNode("resultado"),
            new StringLiteralNode("High")
        );
        TaskElementNode highTask = new TaskElementNode("high", List.of(assignHigh));

        // Task: low
        AssignmentStatementNode assignLow = new AssignmentStatementNode(
            new IdentifierNode("resultado"),
            new StringLiteralNode("Low")
        );
        TaskElementNode lowTask = new TaskElementNode("low", List.of(assignLow));

        EndElementNode end = new EndElementNode("end");

        // Process
        List<ProcessElementNode> elements = List.of(start, checkTask, gateway, highTask, lowTask, end);
        ProcessDeclarationNode process = new ProcessDeclarationNode("ConditionalProcess", elements);

        ProgramNode program = new ProgramNode(List.of(process));

        // When
        String generatedCode = generator.generate(program, null, null, null);

        // Then
        System.out.println("=== GENERATED CODE ===");
        System.out.println(generatedCode);
        System.out.println("======================");

        assertTrue(generatedCode.contains("public static class ConditionalProcess"));
        assertTrue(generatedCode.contains("private void check()"));
        assertTrue(generatedCode.contains("private void decision()"), "Should have gateway method");
        assertTrue(generatedCode.contains("if ((context.variables.get(\"valor\") > 5))"), "Should have if condition");
        assertTrue(generatedCode.contains("high();"), "Should call high task");
        assertTrue(generatedCode.contains("low();"), "Should call low task");
    }
}
