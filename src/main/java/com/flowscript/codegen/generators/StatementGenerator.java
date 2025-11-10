package com.flowscript.codegen.generators;

import com.flowscript.codegen.JavaEmitter;
import com.flowscript.codegen.TypeMapper;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.*;
import com.flowscript.sintactic.ast.functions.statements_basicos.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Generates Java code for FlowScript statements.
 * Handles control flow, variable declarations, and expression statements.
 */
public class StatementGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final TypeMapper typeMapper;
    private final JavaEmitter emitter;

    // Track variables declared in current scope to avoid duplicate var declarations
    private final Set<String> declaredVariables;

    public StatementGenerator(ExpressionGenerator expressionGenerator,
                              TypeMapper typeMapper,
                              JavaEmitter emitter) {
        this.expressionGenerator = expressionGenerator;
        this.typeMapper = typeMapper;
        this.emitter = emitter;
        this.declaredVariables = new HashSet<>();
    }

    /**
     * Clears the set of declared variables.
     * Should be called when entering a new function scope.
     */
    public void clearScope() {
        declaredVariables.clear();
    }

    /**
     * Generates Java code for a statement node.
     */
    public void generate(ASTNode node) {
        if (node == null) {
            return;
        }

        // Variable declaration statement
        if (node instanceof VariableDeclarationStatementNode) {
            generateVariableDeclarationStatement((VariableDeclarationStatementNode) node);
            return;
        }

        // Expression statement
        if (node instanceof ExpressionStatementNode) {
            generateExpressionStatement((ExpressionStatementNode) node);
            return;
        }

        // Control flow
        if (node instanceof IfStatementNode) {
            generateIfStatement((IfStatementNode) node);
            return;
        }

        if (node instanceof ForStatementNode) {
            generateForStatement((ForStatementNode) node);
            return;
        }

        if (node instanceof ForRangeStatementNode) {
            generateForRangeStatement((ForRangeStatementNode) node);
            return;
        }

        if (node instanceof WhileStatementNode) {
            generateWhileStatement((WhileStatementNode) node);
            return;
        }

        if (node instanceof ReturnStatementNode) {
            generateReturnStatement((ReturnStatementNode) node);
            return;
        }

        if (node instanceof BreakStatementNode) {
            emitter.emit("break;");
            return;
        }

        if (node instanceof ContinueStatementNode) {
            emitter.emit("continue;");
            return;
        }

        if (node instanceof com.flowscript.sintactic.ast.process.navegacion.GotoStatementNode) {
            generateGotoStatement((com.flowscript.sintactic.ast.process.navegacion.GotoStatementNode) node);
            return;
        }

        if (node instanceof ThrowStatementNode) {
            generateThrowStatement((ThrowStatementNode) node);
            return;
        }

        if (node instanceof TryStatementNode) {
            generateTryStatement((TryStatementNode) node);
            return;
        }

        // Block
        if (node instanceof BlockNode) {
            generateBlock((BlockNode) node);
            return;
        }

        throw new IllegalArgumentException("Unsupported statement type: " +
                                          node.getClass().getSimpleName());
    }

    // ========== Variable Declarations ==========

    private void generateVariableDeclarationStatement(VariableDeclarationStatementNode node) {
        String varName = node.getVariableName();

        // Check if variable has already been declared in this scope
        boolean alreadyDeclared = declaredVariables.contains(varName);

        // Generate declaration with initializer
        if (node.getInitializer() != null) {
            String initializer = expressionGenerator.generate(node.getInitializer());

            if (alreadyDeclared) {
                // Variable was declared in a previous block but may not be visible here
                // In Java, variables declared with 'var' inside if blocks are not visible outside
                // So we need to determine the type and redeclare it

                // Infer type from initializer
                String type = inferTypeFromExpression(node.getInitializer());

                if (type != null) {
                    // Redeclare with explicit type (will cause shadowing warning but compiles)
                    emitter.emit(type + " " + varName + " = " + initializer + ";");
                } else {
                    // Fallback to simple reassignment (may fail if not in scope)
                    emitter.emit(varName + " = " + initializer + ";");
                }
            } else {
                // First declaration - use 'var' keyword
                emitter.emit("var " + varName + " = " + initializer + ";");
                declaredVariables.add(varName);
            }
        } else {
            if (!alreadyDeclared) {
                // No initializer
                emitter.emit("var " + varName + ";");
                declaredVariables.add(varName);
            }
        }
    }

    /**
     * Infers Java type from a FlowScript expression node.
     * Returns null if type cannot be inferred.
     */
    private String inferTypeFromExpression(com.flowscript.sintactic.ast.ASTNode node) {
        // Check if it's an object literal (Map)
        if (node instanceof com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode) {
            return "Map<String, Object>";
        }
        // Check if it's a list literal
        if (node instanceof com.flowscript.sintactic.ast.functions.literales.ListLiteralNode) {
            return "List<Object>";
        }
        // Check if it's a string literal
        if (node instanceof com.flowscript.sintactic.ast.functions.literales.StringLiteralNode) {
            return "String";
        }
        // Check if it's an integer literal
        if (node instanceof com.flowscript.sintactic.ast.functions.literales.IntegerLiteralNode) {
            return "int";
        }
        // Check if it's a decimal literal
        if (node instanceof com.flowscript.sintactic.ast.functions.literales.DecimalLiteralNode) {
            return "double";
        }
        // Check if it's a boolean literal
        if (node instanceof com.flowscript.sintactic.ast.functions.literales.BooleanLiteralNode) {
            return "boolean";
        }

        // For complex expressions, return null (can't easily infer)
        return null;
    }

    // ========== Expression Statement ==========

    private void generateExpressionStatement(ExpressionStatementNode node) {
        String expr = expressionGenerator.generate(node.getExpression());
        emitter.emit(expr + ";");
    }

    // ========== If Statement ==========

    private void generateIfStatement(IfStatementNode node) {
        // Main if
        String condition = expressionGenerator.generate(node.getCondition());
        emitter.emit("if (" + condition + ")");
        generateStatementOrBlock(node.getThenStatement());

        // Else-if clauses
        List<ElseIfClauseNode> elseIfs = node.getElseIfClauses();
        if (elseIfs != null && !elseIfs.isEmpty()) {
            for (ElseIfClauseNode elseIf : elseIfs) {
                String elseIfCondition = expressionGenerator.generate(elseIf.getCondition());
                emitter.emit("else if (" + elseIfCondition + ")");
                generateStatementOrBlock(elseIf.getStatement());
            }
        }

        // Else clause
        if (node.getElseStatement() != null) {
            emitter.emit("else");
            generateStatementOrBlock(node.getElseStatement());
        }
    }

    // ========== For Statement (collection-based) ==========

    private void generateForStatement(ForStatementNode node) {
        String variable = node.getIteratorVariable();
        String collection = expressionGenerator.generate(node.getIterable());

        // Generate: for (var variable : collection)
        emitter.emit("for (var " + variable + " : " + collection + ")");
        generateStatementOrBlock(node.getBody());
    }

    // ========== For Range Statement ==========

    private void generateForRangeStatement(ForRangeStatementNode node) {
        String variable = node.getLoopVariable();
        String start = expressionGenerator.generate(node.getFromExpression());
        String end = expressionGenerator.generate(node.getToExpression());

        // Generate: for (int variable = start; variable <= end; variable++)
        // Note: Assumes step is 1 if not specified
        if (node.getStepExpression() != null) {
            String step = expressionGenerator.generate(node.getStepExpression());
            emitter.emit("for (int " + variable + " = " + start + "; " +
                        variable + " <= " + end + "; " + variable + " += " + step + ")");
        } else {
            emitter.emit("for (int " + variable + " = " + start + "; " +
                        variable + " <= " + end + "; " + variable + "++)");
        }

        generateStatementOrBlock(node.getBody());
    }

    // ========== While Statement ==========

    private void generateWhileStatement(WhileStatementNode node) {
        String condition = expressionGenerator.generate(node.getCondition());
        emitter.emit("while (" + condition + ")");
        generateStatementOrBlock(node.getBody());
    }

    // ========== Return Statement ==========

    private void generateReturnStatement(ReturnStatementNode node) {
        if (node.getExpression() != null) {
            String expr = expressionGenerator.generate(node.getExpression());
            emitter.emit("return " + expr + ";");
        } else {
            emitter.emit("return;");
        }
    }

    // ========== Throw Statement ==========

    private void generateThrowStatement(ThrowStatementNode node) {
        String expr = expressionGenerator.generate(node.getExpression());
        emitter.emit("throw new RuntimeException(" + expr + ");");
    }

    // ========== Try Statement ==========

    private void generateTryStatement(TryStatementNode node) {
        // Try block
        emitter.emit("try");
        generateBlock(node.getTryBlock());

        // Catch clause
        if (node.getCatchBlock() != null) {
            String exceptionVar = node.getCatchVariable();
            emitter.emit("catch (Exception " + exceptionVar + ")");
            generateBlock(node.getCatchBlock());
        }
    }

    // ========== Goto Statement ==========

    private void generateGotoStatement(com.flowscript.sintactic.ast.process.navegacion.GotoStatementNode node) {
        // In Java, we can't have arbitrary goto statements
        // Generate a comment indicating the target for processes
        emitter.emit("// GOTO: " + node.getTargetLabel());
    }

    // ========== Block ==========

    private void generateBlock(BlockNode node) {
        emitter.openBlock();

        List<StatementNode> statements = node.getStatements();
        if (statements != null) {
            for (StatementNode statement : statements) {
                generate(statement);
            }
        }

        emitter.closeBlock();
    }

    // ========== Helper Methods ==========

    /**
     * Generates a statement or block.
     * If the statement is a block, generates it with braces.
     * Otherwise, wraps single statement in braces for consistency.
     */
    private void generateStatementOrBlock(ASTNode statement) {
        if (statement instanceof BlockNode) {
            generateBlock((BlockNode) statement);
        } else {
            emitter.openBlock();
            generate(statement);
            emitter.closeBlock();
        }
    }

    /**
     * Generates multiple statements.
     */
    public void generateStatements(List<? extends ASTNode> statements) {
        if (statements != null) {
            for (ASTNode statement : statements) {
                generate(statement);
            }
        }
    }
}
