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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void enterImportStmt(FlowScriptProcessesParser.ImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void exitImportStmt(FlowScriptProcessesParser.ImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importJarStmt}.
	 * @param ctx the parse tree
	 */
	void enterImportJarStmt(FlowScriptProcessesParser.ImportJarStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importJarStmt}.
	 * @param ctx the parse tree
	 */
	void exitImportJarStmt(FlowScriptProcessesParser.ImportJarStmtContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(FlowScriptProcessesParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(FlowScriptProcessesParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(FlowScriptProcessesParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(FlowScriptProcessesParser.ParameterContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(FlowScriptProcessesParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(FlowScriptProcessesParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#functionStatement}.
	 * @param ctx the parse tree
	 */
	void enterFunctionStatement(FlowScriptProcessesParser.FunctionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#functionStatement}.
	 * @param ctx the parse tree
	 */
	void exitFunctionStatement(FlowScriptProcessesParser.FunctionStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(FlowScriptProcessesParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(FlowScriptProcessesParser.ExprStmtContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#startDecl}.
	 * @param ctx the parse tree
	 */
	void enterStartDecl(FlowScriptProcessesParser.StartDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#startDecl}.
	 * @param ctx the parse tree
	 */
	void exitStartDecl(FlowScriptProcessesParser.StartDeclContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#endDecl}.
	 * @param ctx the parse tree
	 */
	void enterEndDecl(FlowScriptProcessesParser.EndDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#endDecl}.
	 * @param ctx the parse tree
	 */
	void exitEndDecl(FlowScriptProcessesParser.EndDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskDecl}.
	 * @param ctx the parse tree
	 */
	void enterTaskDecl(FlowScriptProcessesParser.TaskDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskDecl}.
	 * @param ctx the parse tree
	 */
	void exitTaskDecl(FlowScriptProcessesParser.TaskDeclContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(FlowScriptProcessesParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(FlowScriptProcessesParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(FlowScriptProcessesParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(FlowScriptProcessesParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(FlowScriptProcessesParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(FlowScriptProcessesParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tryCatch}.
	 * @param ctx the parse tree
	 */
	void enterTryCatch(FlowScriptProcessesParser.TryCatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tryCatch}.
	 * @param ctx the parse tree
	 */
	void exitTryCatch(FlowScriptProcessesParser.TryCatchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forEach}.
	 * @param ctx the parse tree
	 */
	void enterForEach(FlowScriptProcessesParser.ForEachContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forEach}.
	 * @param ctx the parse tree
	 */
	void exitForEach(FlowScriptProcessesParser.ForEachContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#goToStmt}.
	 * @param ctx the parse tree
	 */
	void enterGoToStmt(FlowScriptProcessesParser.GoToStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#goToStmt}.
	 * @param ctx the parse tree
	 */
	void exitGoToStmt(FlowScriptProcessesParser.GoToStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exclusiveGatewayStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayStmt}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveGatewayStmt(FlowScriptProcessesParser.ExclusiveGatewayStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exclusiveGatewayStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayStmt}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveGatewayStmt(FlowScriptProcessesParser.ExclusiveGatewayStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parallelGatewayStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayStmt}.
	 * @param ctx the parse tree
	 */
	void enterParallelGatewayStmt(FlowScriptProcessesParser.ParallelGatewayStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parallelGatewayStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayStmt}.
	 * @param ctx the parse tree
	 */
	void exitParallelGatewayStmt(FlowScriptProcessesParser.ParallelGatewayStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exclusiveGatewayDecl}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayDecl}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveGatewayDecl(FlowScriptProcessesParser.ExclusiveGatewayDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exclusiveGatewayDecl}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayDecl}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveGatewayDecl(FlowScriptProcessesParser.ExclusiveGatewayDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parallelGatewayDecl}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayDecl}.
	 * @param ctx the parse tree
	 */
	void enterParallelGatewayDecl(FlowScriptProcessesParser.ParallelGatewayDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parallelGatewayDecl}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayDecl}.
	 * @param ctx the parse tree
	 */
	void exitParallelGatewayDecl(FlowScriptProcessesParser.ParallelGatewayDeclContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parallelBranch}.
	 * @param ctx the parse tree
	 */
	void enterParallelBranch(FlowScriptProcessesParser.ParallelBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parallelBranch}.
	 * @param ctx the parse tree
	 */
	void exitParallelBranch(FlowScriptProcessesParser.ParallelBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parallelJoin}.
	 * @param ctx the parse tree
	 */
	void enterParallelJoin(FlowScriptProcessesParser.ParallelJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parallelJoin}.
	 * @param ctx the parse tree
	 */
	void exitParallelJoin(FlowScriptProcessesParser.ParallelJoinContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(FlowScriptProcessesParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(FlowScriptProcessesParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(FlowScriptProcessesParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(FlowScriptProcessesParser.MultiplicativeExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#postfix}.
	 * @param ctx the parse tree
	 */
	void enterPostfix(FlowScriptProcessesParser.PostfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#postfix}.
	 * @param ctx the parse tree
	 */
	void exitPostfix(FlowScriptProcessesParser.PostfixContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(FlowScriptProcessesParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(FlowScriptProcessesParser.ArgumentListContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#indexing}.
	 * @param ctx the parse tree
	 */
	void enterIndexing(FlowScriptProcessesParser.IndexingContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#indexing}.
	 * @param ctx the parse tree
	 */
	void exitIndexing(FlowScriptProcessesParser.IndexingContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(FlowScriptProcessesParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(FlowScriptProcessesParser.PrimaryContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void enterObjectPair(FlowScriptProcessesParser.ObjectPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void exitObjectPair(FlowScriptProcessesParser.ObjectPairContext ctx);
}