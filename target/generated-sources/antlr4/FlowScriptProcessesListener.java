// Generated from FlowScriptProcesses.g4 by ANTLR 4.13.1

    package edu.eam.ingesoft.tlf;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FlowScriptProcessesParser}.
 */
public interface FlowScriptProcessesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(FlowScriptProcessesParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(FlowScriptProcessesParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code flowImport}
	 * labeled alternative in {@link FlowScriptProcessesParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void enterFlowImport(FlowScriptProcessesParser.FlowImportContext ctx);
	/**
	 * Exit a parse tree produced by the {@code flowImport}
	 * labeled alternative in {@link FlowScriptProcessesParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void exitFlowImport(FlowScriptProcessesParser.FlowImportContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jarImport}
	 * labeled alternative in {@link FlowScriptProcessesParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void enterJarImport(FlowScriptProcessesParser.JarImportContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jarImport}
	 * labeled alternative in {@link FlowScriptProcessesParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void exitJarImport(FlowScriptProcessesParser.JarImportContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#globalVarDecl}.
	 * @param ctx the parse tree
	 */
	void enterGlobalVarDecl(FlowScriptProcessesParser.GlobalVarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#globalVarDecl}.
	 * @param ctx the parse tree
	 */
	void exitGlobalVarDecl(FlowScriptProcessesParser.GlobalVarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(FlowScriptProcessesParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(FlowScriptProcessesParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(FlowScriptProcessesParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(FlowScriptProcessesParser.FunctionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(FlowScriptProcessesParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(FlowScriptProcessesParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(FlowScriptProcessesParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(FlowScriptProcessesParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(FlowScriptProcessesParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(FlowScriptProcessesParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(FlowScriptProcessesParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(FlowScriptProcessesParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processDecl}.
	 * @param ctx the parse tree
	 */
	void enterProcessDecl(FlowScriptProcessesParser.ProcessDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processDecl}.
	 * @param ctx the parse tree
	 */
	void exitProcessDecl(FlowScriptProcessesParser.ProcessDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processBody}.
	 * @param ctx the parse tree
	 */
	void enterProcessBody(FlowScriptProcessesParser.ProcessBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processBody}.
	 * @param ctx the parse tree
	 */
	void exitProcessBody(FlowScriptProcessesParser.ProcessBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processElement}.
	 * @param ctx the parse tree
	 */
	void enterProcessElement(FlowScriptProcessesParser.ProcessElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processElement}.
	 * @param ctx the parse tree
	 */
	void exitProcessElement(FlowScriptProcessesParser.ProcessElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#startNode}.
	 * @param ctx the parse tree
	 */
	void enterStartNode(FlowScriptProcessesParser.StartNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#startNode}.
	 * @param ctx the parse tree
	 */
	void exitStartNode(FlowScriptProcessesParser.StartNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskNode}.
	 * @param ctx the parse tree
	 */
	void enterTaskNode(FlowScriptProcessesParser.TaskNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskNode}.
	 * @param ctx the parse tree
	 */
	void exitTaskNode(FlowScriptProcessesParser.TaskNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#endNode}.
	 * @param ctx the parse tree
	 */
	void enterEndNode(FlowScriptProcessesParser.EndNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#endNode}.
	 * @param ctx the parse tree
	 */
	void exitEndNode(FlowScriptProcessesParser.EndNodeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exclusiveGateway}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayNode}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveGateway(FlowScriptProcessesParser.ExclusiveGatewayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exclusiveGateway}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayNode}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveGateway(FlowScriptProcessesParser.ExclusiveGatewayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parallelGateway}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayNode}.
	 * @param ctx the parse tree
	 */
	void enterParallelGateway(FlowScriptProcessesParser.ParallelGatewayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parallelGateway}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayNode}.
	 * @param ctx the parse tree
	 */
	void exitParallelGateway(FlowScriptProcessesParser.ParallelGatewayContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void enterWhenClause(FlowScriptProcessesParser.WhenClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void exitWhenClause(FlowScriptProcessesParser.WhenClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#branchClauses}.
	 * @param ctx the parse tree
	 */
	void enterBranchClauses(FlowScriptProcessesParser.BranchClausesContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#branchClauses}.
	 * @param ctx the parse tree
	 */
	void exitBranchClauses(FlowScriptProcessesParser.BranchClausesContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void enterJoinClause(FlowScriptProcessesParser.JoinClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void exitJoinClause(FlowScriptProcessesParser.JoinClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#statementList}.
	 * @param ctx the parse tree
	 */
	void enterStatementList(FlowScriptProcessesParser.StatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#statementList}.
	 * @param ctx the parse tree
	 */
	void exitStatementList(FlowScriptProcessesParser.StatementListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(FlowScriptProcessesParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(FlowScriptProcessesParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(FlowScriptProcessesParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(FlowScriptProcessesParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(FlowScriptProcessesParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(FlowScriptProcessesParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void enterElseIfClause(FlowScriptProcessesParser.ElseIfClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void exitElseIfClause(FlowScriptProcessesParser.ElseIfClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(FlowScriptProcessesParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(FlowScriptProcessesParser.ElseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(FlowScriptProcessesParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(FlowScriptProcessesParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forEachStmt}.
	 * @param ctx the parse tree
	 */
	void enterForEachStmt(FlowScriptProcessesParser.ForEachStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forEachStmt}.
	 * @param ctx the parse tree
	 */
	void exitForEachStmt(FlowScriptProcessesParser.ForEachStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forRangeStmt}.
	 * @param ctx the parse tree
	 */
	void enterForRangeStmt(FlowScriptProcessesParser.ForRangeStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forRangeStmt}.
	 * @param ctx the parse tree
	 */
	void exitForRangeStmt(FlowScriptProcessesParser.ForRangeStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tryCatchStmt}.
	 * @param ctx the parse tree
	 */
	void enterTryCatchStmt(FlowScriptProcessesParser.TryCatchStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tryCatchStmt}.
	 * @param ctx the parse tree
	 */
	void exitTryCatchStmt(FlowScriptProcessesParser.TryCatchStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#throwStmt}.
	 * @param ctx the parse tree
	 */
	void enterThrowStmt(FlowScriptProcessesParser.ThrowStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#throwStmt}.
	 * @param ctx the parse tree
	 */
	void exitThrowStmt(FlowScriptProcessesParser.ThrowStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gotoStmt}.
	 * @param ctx the parse tree
	 */
	void enterGotoStmt(FlowScriptProcessesParser.GotoStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gotoStmt}.
	 * @param ctx the parse tree
	 */
	void exitGotoStmt(FlowScriptProcessesParser.GotoStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(FlowScriptProcessesParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(FlowScriptProcessesParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#expressionStmt}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStmt(FlowScriptProcessesParser.ExpressionStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#expressionStmt}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStmt(FlowScriptProcessesParser.ExpressionStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(FlowScriptProcessesParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(FlowScriptProcessesParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(FlowScriptProcessesParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(FlowScriptProcessesParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(FlowScriptProcessesParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(FlowScriptProcessesParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(FlowScriptProcessesParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(FlowScriptProcessesParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(FlowScriptProcessesParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(FlowScriptProcessesParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(FlowScriptProcessesParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(FlowScriptProcessesParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#mulExpr}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(FlowScriptProcessesParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#mulExpr}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(FlowScriptProcessesParser.MulExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(FlowScriptProcessesParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(FlowScriptProcessesParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(FlowScriptProcessesParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(FlowScriptProcessesParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(FlowScriptProcessesParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(FlowScriptProcessesParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(FlowScriptProcessesParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(FlowScriptProcessesParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(FlowScriptProcessesParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(FlowScriptProcessesParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#memberAccess}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccess(FlowScriptProcessesParser.MemberAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#memberAccess}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccess(FlowScriptProcessesParser.MemberAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#indexAccess}.
	 * @param ctx the parse tree
	 */
	void enterIndexAccess(FlowScriptProcessesParser.IndexAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#indexAccess}.
	 * @param ctx the parse tree
	 */
	void exitIndexAccess(FlowScriptProcessesParser.IndexAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(FlowScriptProcessesParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(FlowScriptProcessesParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(FlowScriptProcessesParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(FlowScriptProcessesParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#decimalLiteral}.
	 * @param ctx the parse tree
	 */
	void enterDecimalLiteral(FlowScriptProcessesParser.DecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#decimalLiteral}.
	 * @param ctx the parse tree
	 */
	void exitDecimalLiteral(FlowScriptProcessesParser.DecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(FlowScriptProcessesParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(FlowScriptProcessesParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(FlowScriptProcessesParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(FlowScriptProcessesParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(FlowScriptProcessesParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(FlowScriptProcessesParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void enterObjectLiteral(FlowScriptProcessesParser.ObjectLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void exitObjectLiteral(FlowScriptProcessesParser.ObjectLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectField}.
	 * @param ctx the parse tree
	 */
	void enterObjectField(FlowScriptProcessesParser.ObjectFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectField}.
	 * @param ctx the parse tree
	 */
	void exitObjectField(FlowScriptProcessesParser.ObjectFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(FlowScriptProcessesParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(FlowScriptProcessesParser.IdentifierContext ctx);
}