package com.flowscript.semantic.visitor;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.*;
import com.flowscript.sintactic.ast.functions.control_flujo.*;
import com.flowscript.sintactic.ast.functions.expresiones.*;
import com.flowscript.sintactic.ast.functions.listas_argumentos.*;
import com.flowscript.sintactic.ast.functions.literales.*;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.*;
import com.flowscript.sintactic.ast.functions.statements_basicos.*;
import com.flowscript.sintactic.ast.functions.tipos_parametros.*;
import com.flowscript.sintactic.ast.process.clausulas_control.*;
import com.flowscript.sintactic.ast.process.estructura_principal.*;
import com.flowscript.sintactic.ast.process.navegacion.*;

/**
 * Visitor interface for traversing the FlowScript AST.
 * Implements the Visitor design pattern for semantic analysis.
 */
public interface ASTVisitor<T> {
    // Program declarations
    T visit(ProgramNode node);
    T visit(FunctionDeclarationNode node);
    T visit(ImportDeclarationNode node);
    T visit(VariableDeclarationNode node);
    T visit(ProcessDeclarationNode node);

    // Control execution
    T visit(BlockNode node);

    // Control flow
    T visit(IfStatementNode node);
    T visit(WhileStatementNode node);
    T visit(ForStatementNode node);
    T visit(ForRangeStatementNode node);
    T visit(BreakStatementNode node);
    T visit(ContinueStatementNode node);
    T visit(ReturnStatementNode node);
    T visit(ThrowStatementNode node);
    T visit(TryStatementNode node);

    // Expressions
    T visit(AdditiveExpressionNode node);
    T visit(ArgumentListNode node);
    T visit(EqualityExpressionNode node);
    T visit(FunctionCallNode node);
    T visit(IdentifierNode node);
    T visit(IndexAccessNode node);
    T visit(LogicalAndExpressionNode node);
    T visit(LogicalOrExpressionNode node);
    T visit(MultiplicativeExpressionNode node);
    T visit(ObjectMemberNode node);
    T visit(PostfixExpressionNode node);
    T visit(PostfixOperatorNode node);
    T visit(PrimaryExpressionNode node);
    T visit(PropertyAccessNode node);
    T visit(RelationalExpressionNode node);
    T visit(TernaryExpressionNode node);
    T visit(UnaryExpressionNode node);

    // Special operations
    T visit(DbExecuteNode node);
    T visit(DbQueryNode node);
    T visit(HttpGetNode node);
    T visit(HttpPostNode node);
    T visit(HttpDeleteNode node);

    // List arguments
    T visit(ExpressionListNode node);
    T visit(ObjectMemberListNode node);

    // Literals
    T visit(BooleanLiteralNode node);
    T visit(DecimalLiteralNode node);
    T visit(IntegerLiteralNode node);
    T visit(ListLiteralNode node);
    T visit(NullLiteralNode node);
    T visit(ObjectLiteralNode node);
    T visit(StringLiteralNode node);

    // Statements
    T visit(ElseIfClauseNode node);
    T visit(ExpressionStatementNode node);
    T visit(VariableDeclarationStatementNode node);

    // Types and parameters
    T visit(ParameterNode node);
    T visit(TypeNode node);

    // Process clauses
    T visit(ElseClauseNode node);
    T visit(JoinClauseNode node);
    T visit(ParallelBranchNode node);
    T visit(WhenClauseNode node);

    // Process elements
    T visit(EndElementNode node);
    T visit(ExclusiveGatewayNode node);
    T visit(ParallelGatewayNode node);
    T visit(StartElementNode node);
    T visit(TaskElementNode node);

    // Process navigation
    T visit(GotoStatementNode node);
}
