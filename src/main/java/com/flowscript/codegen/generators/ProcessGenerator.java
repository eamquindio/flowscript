package com.flowscript.codegen.generators;

import com.flowscript.codegen.JavaEmitter;
import com.flowscript.codegen.TypeMapper;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.process.clausulas_control.*;
import com.flowscript.sintactic.ast.process.estructura_principal.*;
import com.flowscript.sintactic.ast.process.navegacion.GotoStatementNode;

import java.util.*;

/**
 * Generates Java code for FlowScript process declarations.
 * Implements the simplified design: one process = one method.
 */
public class ProcessGenerator {

    private final StatementGenerator statementGenerator;
    private final ExpressionGenerator expressionGenerator;
    private final TypeMapper typeMapper;
    private final JavaEmitter emitter;

    public ProcessGenerator(StatementGenerator statementGenerator,
                           ExpressionGenerator expressionGenerator,
                           TypeMapper typeMapper,
                           JavaEmitter emitter) {
        this.statementGenerator = statementGenerator;
        this.expressionGenerator = expressionGenerator;
        this.typeMapper = typeMapper;
        this.emitter = emitter;
    }

    /**
     * Generates a complete process as a Java static method.
     */
    public void generate(ProcessDeclarationNode process) {
        // Clear variable scope for new process
        statementGenerator.clearScope();

        // Check if process has goto statements (requires state machine)
        boolean hasGoto = hasGotoStatements(process);

        if (hasGoto) {
            generateStateMachineProcess(process);
        } else {
            generateSequentialProcess(process);
        }

        emitter.newLine(); // Add blank line after process
    }

    // ========== Sequential Process (Simple) ==========

    private void generateSequentialProcess(ProcessDeclarationNode process) {
        // Method signature
        emitter.emitMethodSignature("public", true, "void", process.getName(), "");

        // Open method body
        emitter.openBlock();

        // Log entrada
        String entryLog = String.format("System.out.println(\"[ENTRADA] Proceso: %s\");",
                                       process.getName());
        emitter.emit(entryLog);
        emitter.newLine();

        // Generate comment
        emitter.emitComment("=== INICIO ===");
        emitter.newLine();

        // Process elements in sequence
        List<ASTNode> elements = process.getProcessElements();
        if (elements != null) {
            for (ASTNode element : elements) {
                generateProcessElement(element);
            }
        }

        // End comment
        emitter.emitComment("=== FIN ===");
        emitter.newLine();

        // Log salida
        String exitLog = String.format("System.out.println(\"[SALIDA] Proceso: %s\");",
                                      process.getName());
        emitter.emit(exitLog);

        // Close method body
        emitter.closeBlock();
    }

    // ========== Process Elements ==========

    private void generateProcessElement(ASTNode element) {
        if (element instanceof StartElementNode) {
            // Start element - just a marker, no code generated
            return;
        }

        if (element instanceof EndElementNode) {
            // End element - just a marker
            return;
        }

        if (element instanceof TaskElementNode) {
            generateTask((TaskElementNode) element);
            return;
        }

        if (element instanceof ExclusiveGatewayNode) {
            generateExclusiveGateway((ExclusiveGatewayNode) element);
            return;
        }

        if (element instanceof ParallelGatewayNode) {
            generateParallelGateway((ParallelGatewayNode) element);
            return;
        }

        // Other elements - skip or handle
    }

    // ========== Task Element ==========

    private void generateTask(TaskElementNode task) {
        emitter.emitComment("=== TAREA: " + task.getTaskName() + " ===");

        List<StatementNode> statements = task.getActionStatements();
        if (statements != null) {
            for (StatementNode statement : statements) {
                statementGenerator.generate(statement);
            }
        }

        emitter.newLine();
    }

    // ========== Exclusive Gateway ==========

    private void generateExclusiveGateway(ExclusiveGatewayNode gateway) {
        emitter.emitComment("=== GATEWAY: " + gateway.getGatewayName() + " ===");
        emitter.emit("System.out.println(\"[GATEWAY ENTRADA] " + gateway.getGatewayName() + "\");");

        List<WhenClauseNode> whenClauses = gateway.getWhenClauses();

        if (whenClauses == null || whenClauses.isEmpty()) {
            return;
        }

        // First when clause
        WhenClauseNode firstWhen = whenClauses.get(0);
        String condition = expressionGenerator.generate(firstWhen.getCondition());
        emitter.emit("if (" + condition + ")");
        emitter.openBlock();
        emitter.emitComment("Go to: " + firstWhen.getTargetTask());
        // Note: In sequential mode, we'd need to inline the target task here
        // For now, we just add a comment
        emitter.closeBlock();

        // Subsequent when clauses
        for (int i = 1; i < whenClauses.size(); i++) {
            WhenClauseNode when = whenClauses.get(i);
            String cond = expressionGenerator.generate(when.getCondition());
            emitter.emit("else if (" + cond + ")");
            emitter.openBlock();
            emitter.emitComment("Go to: " + when.getTargetTask());
            emitter.closeBlock();
        }

        // Else clause
        if (gateway.getElseClause() != null) {
            emitter.emit("else");
            emitter.openBlock();
            emitter.emitComment("Go to: " + gateway.getElseClause().getTargetTask());
            emitter.closeBlock();
        }

        emitter.emit("System.out.println(\"[GATEWAY SALIDA] " + gateway.getGatewayName() + "\");");
        emitter.newLine();
    }

    // ========== Parallel Gateway ==========

    private void generateParallelGateway(ParallelGatewayNode gateway) {
        emitter.emitComment("=== PARALLEL GATEWAY: " + gateway.getGatewayName() + " ===");
        emitter.emit("System.out.println(\"[PARALLEL GATEWAY ENTRADA] " + gateway.getGatewayName() + " - Iniciando ramas paralelas\");");

        List<ParallelBranchNode> branches = gateway.getBranches();

        if (branches == null || branches.isEmpty()) {
            return;
        }

        // Use gateway name as prefix to ensure unique variable names
        String gatewayPrefix = gateway.getGatewayName();

        // Generate CompletableFutures for each branch
        for (int i = 0; i < branches.size(); i++) {
            ParallelBranchNode branch = branches.get(i);
            emitter.emit("CompletableFuture<Void> " + gatewayPrefix + "_rama" + i +
                        " = CompletableFuture.runAsync(() ->");
            emitter.openBlock();
            emitter.emit("try");
            emitter.openBlock();
            emitter.emitComment("Branch to: " + branch.getTargetTask());
            // Note: In a full implementation, we'd generate the task code here
            emitter.closeBlock();
            emitter.emit("catch (Exception e)");
            emitter.openBlock();
            emitter.emit("throw new RuntimeException(\"Error in parallel branch " + i + "\", e);");
            emitter.closeBlock();
            emitter.closeBlock();
            emitter.emit(");");
            emitter.newLine();
        }

        // Generate join
        emitter.emitComment("=== JOIN: Esperar todas las ramas ===");

        StringBuilder allOf = new StringBuilder("CompletableFuture.allOf(");
        for (int i = 0; i < branches.size(); i++) {
            if (i > 0) allOf.append(", ");
            allOf.append(gatewayPrefix).append("_rama").append(i);
        }
        allOf.append(").join();");

        emitter.emit(allOf.toString());
        emitter.emit("System.out.println(\"[PARALLEL GATEWAY SALIDA] " + gateway.getGatewayName() + " - Todas las ramas completadas\");");
        emitter.newLine();

        // Continue to join target
        if (gateway.getJoinClause() != null) {
            emitter.emitComment("Continue to: " + gateway.getJoinClause().getTargetTask());
            emitter.newLine();
        }
    }

    // ========== State Machine Process (with goto) ==========

    private void generateStateMachineProcess(ProcessDeclarationNode process) {
        // Method signature
        emitter.emitMethodSignature("public", true, "void", process.getName(), "");

        // Open method body
        emitter.openBlock();

        // Log entrada
        String entryLog = String.format("System.out.println(\"[ENTRADA] Proceso: %s\");",
                                       process.getName());
        emitter.emit(entryLog);
        emitter.newLine();

        // Context map for variables
        emitter.emit("Map<String, Object> ctx = new HashMap<>();");
        emitter.emit("String estado = \"inicio\";");
        emitter.newLine();

        // State machine loop
        emitter.emitComment("=== State Machine ===");
        emitter.emit("while (!estado.equals(\"fin\"))");
        emitter.openBlock();

        emitter.emit("switch (estado)");
        emitter.openBlock();

        // Generate case for each task/element
        emitter.emit("case \"inicio\":");
        emitter.indent();
        emitter.emitComment("=== INICIO ===");
        emitter.emit("estado = \"fin\"; // TODO: Set next state");
        emitter.emit("break;");
        emitter.dedent();
        emitter.newLine();

        // TODO: Generate cases for each task

        emitter.emit("default:");
        emitter.indent();
        emitter.emit("estado = \"fin\";");
        emitter.emit("break;");
        emitter.dedent();

        emitter.closeBlock(); // switch
        emitter.closeBlock(); // while

        emitter.emitComment("=== FIN ===");
        emitter.newLine();

        // Log salida
        String exitLog = String.format("System.out.println(\"[SALIDA] Proceso: %s\");",
                                      process.getName());
        emitter.emit(exitLog);

        // Close method body
        emitter.closeBlock();
    }

    // ========== Helpers ==========

    /**
     * Checks if a process contains goto statements.
     */
    private boolean hasGotoStatements(ProcessDeclarationNode process) {
        List<ASTNode> elements = process.getProcessElements();
        if (elements == null) {
            return false;
        }

        for (ASTNode element : elements) {
            if (element instanceof GotoStatementNode) {
                return true;
            }
            // TODO: Check inside tasks for goto statements
        }

        return false;
    }
}
